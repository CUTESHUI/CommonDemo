//package com.shui.service.elasticsearch;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.shui.domain.elasticsearch.BankElastic;
//import com.shui.domain.elasticsearch.UserElastic;
//import com.shui.repository.BankEsRepository;
//import com.shui.repository.UserEsRepository;
//import org.elasticsearch.common.unit.Fuzziness;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.aggregations.Aggregation;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.sort.SortBuilders;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class ElasticsearchService {
//
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;
//    @Autowired
//    private UserEsRepository userEsRepository;
//    @Autowired
//    private BankEsRepository bankEsRepository;
//
//
//    public void test() {
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                //查询条件
//                .withQuery(QueryBuilders.queryStringQuery("浦东开发开放").defaultField("title"))
//                //分页
//                .withPageable(PageRequest.of(0, 5))
//                //排序
//                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
//                //高亮字段显示
//                .withHighlightFields(new HighlightBuilder.Field("浦东").preTags("<em style='color:red;'>").postTags("</em>").fragmentSize(100))
//                .build();
//        SearchHits<UserElastic> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, UserElastic.class);
//        // 转换成map集合
//        Map<String, Aggregation> aggregationMap = searchHits.getAggregations().getAsMap();
//        for (SearchHit<UserElastic> searchHit : searchHits) {
//            UserElastic content = searchHit.getContent();
//        }
//
//        /**
//         * 精确查询
//         */
//        // 指定字符串作为关键词查询，关键词支持分词
//        // 查询title字段中，包含 ”开发”、“开放" 这个字符串的document；相当于把"浦东开发开放"分词了，再查询；
//        QueryBuilders.queryStringQuery("开发开放").defaultField("title");
//        // 不指定field，查询范围为所有field
//        QueryBuilders.queryStringQuery("青春");
//        // 指定多个field
//        QueryBuilders.queryStringQuery("青春").field("title").field("content");
//
//        // 关键字不支持分词，keyword
//        QueryBuilders.termQuery("title", "开发开放");
//        QueryBuilders.termsQuery("fieldName", "fieldlValue1", "fieldlValue2...");
//
//        // 关键字支持分词
//        QueryBuilders.matchQuery("title", "开发开放");
//        QueryBuilders.multiMatchQuery("fieldlValue", "fieldName1", "fieldName2", "fieldName3");
//
//
//        /**
//         * 模糊查询
//         */
//        // 常用于推荐相似内容的查询，//如果不指定filedName，则默认全部
//        QueryBuilders.moreLikeThisQuery(new String[]{"fieldName"});
//        // 左右模糊查询，其中fuzziness的参数作用是在查询时，es动态的将查询关键词前后增加或者删除一个词，然后进行匹配
//        QueryBuilders.fuzzyQuery("title", "开发开放").fuzziness(Fuzziness.ONE);
//        // 前缀查询，查询title中以“开发开放”为前缀的document；
//        QueryBuilders.prefixQuery("title", "开发开放");
//        // 通配符查询，支持*和？，？表示单个字符；注意不建议将通配符作为前缀，否则导致查询很慢
//        QueryBuilders.wildcardQuery("title", "开*放");
//        QueryBuilders.wildcardQuery("title", "开？放");
//
//
//        /**
//         * 范围查询
//         */
//        // 闭区间查询
//        QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2");
//        // 开区间查询，默认是true，也就是包含
//        QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2").includeUpper(false).includeLower(false);
//        // 大于
//        QueryBuilders.rangeQuery("fieldName").gt("fieldValue");
//        // 大于等于
//        QueryBuilders.rangeQuery("fieldName").gte("fieldValue");
//        // 小于
//        QueryBuilders.rangeQuery("fieldName").lt("fieldValue");
//        // 小于等于
//        QueryBuilders.rangeQuery("fieldName").lte("fieldValue");
//
//        /**
//         * 多个关键字组合查询boolQuery()
//         */
//        // 文档必须完全匹配条件，相当于and
//        QueryBuilders.boolQuery().must();
//        // 文档必须不匹配条件，相当于not
//        QueryBuilders.boolQuery().mustNot();
//        // 至少满足一个条件，这个文档就符合should，相当于or
//        QueryBuilders.boolQuery().should();
//        // demo
//        QueryBuilders.boolQuery()
//                .should(QueryBuilders.termQuery("title", "开发"))
//                .should(QueryBuilders.termQuery("title", "青春"))
//                .mustNot(QueryBuilders.termQuery("title", "潮头"));
//
//
//        /**
//         * 排序
//         */
//        // 按照id字段降序
//        new NativeSearchQueryBuilder().withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));
//        new NativeSearchQueryBuilder().withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
//
//        /**
//         * 聚合
//         */
//        //  统计某个字段的数量
//        AggregationBuilders.count("count_uid").field("uid");
//        // 去重统计某个字段的数量（有少量误差）
//        AggregationBuilders.cardinality("distinct_count_uid").field("uid");
//        // 聚合过滤
//        AggregationBuilders.filter("uid_filter", QueryBuilders.queryStringQuery("uid:001"));
//        // 按某个字段分组
//        AggregationBuilders.terms("group_name").field("name");
//        // 求和
//        AggregationBuilders.sum("sum_price").field("price");
//        // 求平均
//        AggregationBuilders.avg("avg_price").field("price");
//        // 求最大值
//        AggregationBuilders.max("max_price").field("price");
//        // 求最小值
//        AggregationBuilders.min("min_price").field("price");
//        // 按日期间隔分组
//        AggregationBuilders.dateHistogram("dh").field("date");
//        // 按某间隔分组
//        AggregationBuilders.histogram("balance").field("balance");
//        // 嵌套的聚合，字段为一个json，取json的字段
//        AggregationBuilders.nested("allAttrValues", "attrValueList")
//                .subAggregation(AggregationBuilders.filter("productAttrs", QueryBuilders.termQuery("attrValueList.type", 1))
//                        .subAggregation(AggregationBuilders.terms("attrIds").field("attrValueList.productAttributeId")
//                                .subAggregation(AggregationBuilders.terms("attrValues").field("attrValueList.value"))
//                                .subAggregation(AggregationBuilders.terms("attrNames").field("attrValueList.name"))));
//        // 反转嵌套
//        AggregationBuilders.reverseNested("res_negsted").path("kps ");
//
//
//        elasticsearchRestTemplate.get("1", BankElastic.class);
//        elasticsearchRestTemplate.count(nativeSearchQuery, BankElastic.class);
//        elasticsearchRestTemplate.delete("1", BankElastic.class);
//        elasticsearchRestTemplate.searchOne(nativeSearchQuery, BankElastic.class);
//    }
//
//    public Object bankSearch() {
//        Iterable<BankElastic> all = bankEsRepository.findAll();
//        Optional<BankElastic> byId = bankEsRepository.findById(2L);
//
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                //查询条件
////                .withQuery(QueryBuilders.matchQuery("address", "mill Lane"))
////                .withQuery(QueryBuilders.matchQuery("address.keyword", "198 Mill Lane"))
////                .withQuery(QueryBuilders.matchPhraseQuery("address", "mill lane"))
////                .withQuery(QueryBuilders.multiMatchQuery("mill", "address", "city"))
////                .withQuery(QueryBuilders.rangeQuery("age").gte(18).lte(20))
////                .withQuery(QueryBuilders.termQuery("_id", 2))
////                .withQuery(QueryBuilders.boolQuery()
////                        .must(QueryBuilders.matchQuery("gender", "F"))
////                        .must(QueryBuilders.matchQuery("address", "mill"))
////                )
////                .withQuery(QueryBuilders.boolQuery()
////                        .filter(QueryBuilders.matchQuery("gender", "F"))
////                )
//                //分页
//                .withPageable(PageRequest.of(0, 10))
//                //排序
////                .withSort(SortBuilders.fieldSort("account_number").order(SortOrder.ASC))
////                .withSort(SortBuilders.fieldSort("balance").order(SortOrder.ASC))
//                //高亮字段显示
////                .withHighlightFields(new HighlightBuilder.Field("浦东").preTags("<em style='color:red;'>").postTags("</em>").fragmentSize(100))
//                //显示的字段
////                .withFields("address", "email")
//                //聚合
////                .addAggregation(AggregationBuilders.terms("年龄分布(分组)统计").field("age").order(BucketOrder.count(false)))
////                .addAggregation(
////                        AggregationBuilders.terms("年龄分布(分组)统计").field("age").order(BucketOrder.count(false))
////                                .subAggregation(AggregationBuilders.avg("平均工资").field("balance"))
////                )
////                .addAggregation(
////                        AggregationBuilders.avg("平均工资").field("balance")
////                )
////                .withIds(Arrays.asList("1", "2"))
//                .build();
//        SearchHits<BankElastic> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, BankElastic.class);
//        SearchHits<JSONObject> search = elasticsearchRestTemplate.search(nativeSearchQuery, JSONObject.class);
//        List<BankElastic> bankElastics = new ArrayList<>();
//        for (SearchHit<BankElastic> searchHit : searchHits) {
//            bankElastics.add(searchHit.getContent());
//        }
//        Aggregations aggregations = searchHits.getAggregations();
//        Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();
//        return aggregationsMap;
//    }
//}
