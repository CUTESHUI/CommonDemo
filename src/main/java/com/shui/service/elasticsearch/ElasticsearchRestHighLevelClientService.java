package com.shui.service.elasticsearch;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 准备检所请求
 * 模糊匹配，过滤（按照属性，分类，品牌，价格区间，库存），排序，分页，高亮 ,聚合分析【分析所有可选的规格、分类、品牌】
 * <p>
 * 1、创建查询请求
 * SearchRequest searchRequest = new SearchRequest("newbank")
 * 2、创建构建DSL检索条件对象
 * SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
 * 3、创建各种条件
 * QueryBuilder boolQuery = QueryBuilders.boolQuery()
 * QueryBuilder matchQuery = QueryBuilders.matchAllQuery()
 * <p>
 * 4、组合检索条件
 * sourceBuilder.sort();
 * sourceBuilder.from();
 * sourceBuilder.size();
 * sourceBuilder.aggregation();
 * sourceBuilder.query(QueryBuilder);
 * sourceBuilder.query(boolQuery);
 * 5、请求绑定条件
 * searchRequest.source(sourceBuilder);
 */
@Service
public class ElasticsearchRestHighLevelClientService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ElasticsearchRestHighLevelClientUtils elasticsearchRestHighLevelClientUtils;

    public Object bankSearch() throws Exception {

//        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("subject").subAggregation(AggregationBuilders.count("class"));

//        AggregationBuilders.nested("", "").subAggregation()


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder

                .from(0).size(10)
        ;
        SearchRequest searchRequest = new SearchRequest(new String[]{"bank"}, searchSourceBuilder);
//        SearchResponse searchResponses = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
//
//        SearchHits hits = searchResponses.getHits();
//        SearchHit[] searchHits = hits.getHits();
//
//        JSONArray res = new JSONArray();
//        for (SearchHit searchHit : searchHits) {
//            JSONObject jsonObject = JSON.parseObject(searchHit.getSourceAsString());
//            res.add(jsonObject);
//        }
//
//        Aggregations aggregations = searchResponses.getAggregations();
//        return res;
//        JSONArray res = elasticsearchRestHighLevelClientUtils.listByParam(searchRequest);


        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> messageJSON = new HashMap<>();
        Map<String, Object> messageJSON1 = new HashMap<>();
        messageJSON1.put("type", "text");

        Map<String, Object> account_number = new HashMap<>();
        account_number.put("type", "text");

        Map<String, Object> messageJSON2 = new HashMap<>();
        messageJSON2.put("type", "long");
        messageJSON.put("f1", messageJSON1);
        messageJSON.put("f2", messageJSON2);
        message.put("properties", messageJSON);

        Map<String, Object> properties = new HashMap<>();
        properties.put("message", message);
        properties.put("messageJSON1", messageJSON1);
        properties.put("account_number", account_number);


        mapping.put("properties", properties);

//        boolean b = elasticsearchRestHighLevelClientUtils.updateIndex("bank11505383619076579329", mapping);

//        ReindexRequest request = new ReindexRequest();
//        request.setSourceIndices("bank");
//        request.setDestIndex("bank1");
//        request.setSourceBatchSize(5000);
//        BulkByScrollResponse reindex = restHighLevelClient.reindex(request, ElasticSearchConfig.COMMON_OPTIONS);
        boolean b = elasticsearchRestHighLevelClientUtils.updateIndex("bank1", mapping);

        // 智能提示
        CompletionSuggestionBuilder completionSuggestionBuilder = SuggestBuilders.completionSuggestion("");
        completionSuggestionBuilder.size(1); // 智能提示的数量
        completionSuggestionBuilder.skipDuplicates(true); // 跳过重复
        completionSuggestionBuilder.analyzer("ik_max_word"); // 分词器
        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("customName", completionSuggestionBuilder));

//
//
//        List<ElasticSearchBaseDTO> dataList = new ArrayList<>();
//        for (int i = 0; i < 100000; i++) {
//            ElasticSearchBaseDTO data = new ElasticSearchBaseDTO();
//            data.setId(IdWorker.getId()+"");
//            Map<String, Object> filed = new HashMap<>();
//            filed.put("account_number", i);
//            data.setFiledJson(JSON.parseObject(JSON.toJSONString(filed)));
//            dataList.add(data);
//        }


//        elasticsearchRestHighLevelClientUtils.saveBatch("bank", dataList);

//        Optional<ElasticSearchBaseDTO> byQuery = elasticsearchRestHighLevelClientUtils.getByQuery(searchRequest);
//        return byQuery.orElseGet(() -> byQuery.orElse(null));
        return null;
    }
}
