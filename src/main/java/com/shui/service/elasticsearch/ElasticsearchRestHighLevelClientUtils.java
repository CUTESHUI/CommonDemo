package com.shui.service.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.shui.config.ElasticSearchConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
@Slf4j
public class ElasticsearchRestHighLevelClientUtils {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 判断索引是否存在
     */
    public boolean existIndex(String indexName) {
        try {
            GetIndexRequest request = new GetIndexRequest(indexName);
            return restHighLevelClient.indices().exists(request, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES判断索引是否存在出错：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 创建索引
     */
    public boolean createIndex(String indexName, Map<String, Object> mapping) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            request.mapping(mapping);
            restHighLevelClient.indices().create(request, ElasticSearchConfig.COMMON_OPTIONS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES新增索引出错：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String indexName) {
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            restHighLevelClient.indices().delete(request, ElasticSearchConfig.COMMON_OPTIONS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES删除索引出错：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 更新索引
     */
    public boolean updateIndex(String indexName, Map<String, Object> mapping) {
        try {
            String realIndexName = indexName;
            GetAliasesResponse getAliasesResponse = restHighLevelClient.indices().getAlias(new GetAliasesRequest(indexName), ElasticSearchConfig.COMMON_OPTIONS);
            Map<String, Set<AliasMetadata>> aliases = getAliasesResponse.getAliases();
            if (!aliases.isEmpty()) {
                realIndexName = aliases.keySet().toArray(new String[0])[0];
            }


            String destIndexName;
            String[] indexSplit = realIndexName.split("&");
            if (indexSplit.length > 1) {
                destIndexName = indexSplit[0] + "&" + IdWorker.getId();
            } else {
                destIndexName = realIndexName + "&" + IdWorker.getId();
            }

            if (!createIndex(destIndexName, mapping)) {
                return false;
            }

            ReindexRequest request = new ReindexRequest();
            request.setSourceIndices(realIndexName);
            request.setDestIndex(destIndexName);
            request.setSourceBatchSize(10000);
            BulkByScrollResponse reindex = restHighLevelClient.reindex(request, ElasticSearchConfig.COMMON_OPTIONS);

            deleteIndex(realIndexName);

            IndicesAliasesRequest aliasesRequest = new IndicesAliasesRequest();
            IndicesAliasesRequest.AliasActions aliasAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD);
            aliasAction.index(destIndexName).alias(indexName);
            aliasesRequest.addAliasAction(aliasAction);
            restHighLevelClient.indices().updateAliases(aliasesRequest, ElasticSearchConfig.COMMON_OPTIONS);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES更新索引出错：{}", e.getMessage());
            return false;
        }
    }


    public void save(String indexName, ElasticSearchBaseDTO data) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.add(new IndexRequest(indexName).id(data.getId()).source(data.getFiledJson()));
            BulkResponse response = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
            if (response.hasFailures()) {
                exceptionRetry(bulkRequest, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES新增出错：{}", e.getMessage());
        }
    }

    public void saveBatch(String indexName, List<ElasticSearchBaseDTO> dataList) {
        BulkRequest bulkRequest = new BulkRequest();
        for (ElasticSearchBaseDTO data : dataList) {
            bulkRequest.add(new IndexRequest(indexName).id(data.getId()).source(data.getFiledJson()));
        }
        try {
            BulkResponse response = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
            if (response.hasFailures()) {
                exceptionRetry(bulkRequest, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES批量新增出错：{}", e.getMessage());
        }
    }

    /**
     * 异常捕获并重试
     */
    private void exceptionRetry(BulkRequest bulkRequest, BulkResponse response) {
        List<DocWriteRequest<?>> list = bulkRequest.requests();
        BulkRequest requestRetry = new BulkRequest();
        for (BulkItemResponse itemResponse : response) {
            if (itemResponse.isFailed()) {
                int docIndex = itemResponse.getItemId();
                IndexRequest ir = (IndexRequest) list.get(docIndex);
                requestRetry.add(ir);
            }
        }
        try {
            // 遇到错误，休眠1s后重试
            Thread.sleep(1000);
            restHighLevelClient.bulk(requestRetry, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("ES新增重试出错：{}", e.getMessage());
        }
    }

    /**
     * 根据Id获取
     */
    public Optional<ElasticSearchBaseDTO> getById(String indexName, String id) {
        GetResponse response = null;
        try {
            GetRequest request = new GetRequest(indexName, id);
            response = restHighLevelClient.get(request, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES查询出错: {}", e.getMessage(), e);
        }
        if (response == null || !response.isExists()) {
            return convertSearchHit(null);
        }
        return convertSearchHit(response.getId(), response.getSourceAsString());
    }

    /**
     * 根据查询获取
     */
    public Optional<ElasticSearchBaseDTO> getByQuery(SearchRequest searchRequest) {
        if (!checkSearchRequestReady(searchRequest)) {
            return convertSearchHit(null);
        }
        SearchHit[] hits = new SearchHit[0];
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            SearchHits searchHits = searchResponse.getHits();
            hits = searchHits.getHits();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES查询出错: {}", e.getMessage(), e);
        }
        if (hits.length == 0) {
            return convertSearchHit(null);
        }
        return convertSearchHit(hits[0]);
    }


    /**
     * 通过查询获取列表
     */
    public List<Optional<ElasticSearchBaseDTO>> listByQuery(SearchRequest searchRequest) {
        if (!checkSearchRequestReady(searchRequest)) {
            return new ArrayList<>();
        }
        SearchHit[] hits = new SearchHit[0];
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            SearchHits searchHits = searchResponse.getHits();
            hits = searchHits.getHits();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES查询出错: {}", e.getMessage(), e);
        }
        if (hits.length == 0) {
            return new ArrayList<>();
        }
        List<Optional<ElasticSearchBaseDTO>> res = new ArrayList<>();
        for (SearchHit hit : hits) {
            res.add(convertSearchHit(hit));
        }
        return res;
    }

    /**
     * 根据Id更新
     */
    public void updateById(String indexName, ElasticSearchBaseDTO data) {
        try {
            UpdateRequest request = new UpdateRequest(indexName, data.getId()).doc(data.getFiledJson());
            restHighLevelClient.update(request, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES更新异常:{}", e.getMessage());
        }
    }

    /**
     * 根据查询更新
     */
    public void updateByQuery(UpdateByQueryRequest queryRequest) {
        try {
            restHighLevelClient.updateByQuery(queryRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES更新异常:{}", e.getMessage());
        }
    }

    /**
     * 根据Id删除
     */
    public void deleteById(String indexName, String id) {
        try {
            DeleteRequest request = new DeleteRequest(indexName, id);
            restHighLevelClient.delete(request, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES删除异常: {}", e.getMessage());
        }
    }

    /**
     * 根据查询删除
     */
    public void deleteByQuery(DeleteByQueryRequest queryRequest) {
        try {
            restHighLevelClient.deleteByQuery(queryRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ES删除异常: {}", e.getMessage());
        }
    }


    /**
     * 检查搜索请求
     */
    private boolean checkSearchRequestReady(SearchRequest searchRequest) {
        if (searchRequest == null) {
            return false;
        }
        try {
            Field indicesField = SearchRequest.class.getDeclaredField("indices");
            indicesField.setAccessible(true);
            Object indices = indicesField.get(searchRequest);
            if (indices == null || indices.toString().isEmpty()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 转换结果
     */
    private Optional<ElasticSearchBaseDTO> convertSearchHit(SearchHit searchHit) {
        if (searchHit == null || searchHit.getId().isEmpty() || searchHit.getSourceAsString().isEmpty()) {
            return Optional.empty();
        }
        return convertSearchHit(searchHit.getId(), searchHit.getSourceAsString());
    }

    private Optional<ElasticSearchBaseDTO> convertSearchHit(String id, String sourceString) {
        if (id.isEmpty() || sourceString.isEmpty()) {
            return Optional.empty();
        }
        ElasticSearchBaseDTO res = new ElasticSearchBaseDTO();
        res.setId(id);
        res.setFiledJson(JSON.parseObject(sourceString));
        return Optional.of(res);
    }

}
