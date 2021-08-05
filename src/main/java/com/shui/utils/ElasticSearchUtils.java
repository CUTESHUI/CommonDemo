package com.shui.utils;

import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

public class ElasticSearchUtils {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    //                                  索引                                      //

    /**
     * 判断索引是否存在
     *
     * @param clazz 索引类
     * @return boolean
     */
    public boolean indexExists(Class<?> clazz) {
        return elasticsearchRestTemplate.indexOps(clazz).exists();
    }

    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexExists(String indexName) {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).exists();
    }

    /**
     * 创建索引
     *
     * @param clazz 索引类
     * @return boolean
     */
    public boolean indexCreate(Class<?> clazz) {
        return elasticsearchRestTemplate.indexOps(clazz).create();
    }

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexCreate(String indexName) {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).create();
    }

    /**
     * 索引删除
     *
     * @param clazz 索引类
     * @return boolean
     */
    public boolean indexDelete(Class<?> clazz) {
        return elasticsearchRestTemplate.indexOps(clazz).delete();
    }

    /**
     * 索引删除
     *
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexDelete(String indexName) {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).delete();
    }


    //                                  查询                                      //
    /**
     * 关键字匹配查询
     *
     * @param name  字段的名称
     * @param value 查询值
     */
    public static TermQueryBuilder termQuery(String name, String value) {
        return new TermQueryBuilder(name, value);
    }

    /**
     * 关键字查询，同时匹配多个关键字
     *
     * @param name   字段名称
     * @param values 查询值
     */
    public static TermsQueryBuilder termsQuery(String name, String... values) {
        return new TermsQueryBuilder(name, values);
    }

    /**
     * 创建一个匹配多个关键字的查询，返回boolean
     *
     * @param fieldNames 字段名称
     * @param text       查询值
     */
    public static MultiMatchQueryBuilder multiMatchQuery(Object text, String... fieldNames) {
        return new MultiMatchQueryBuilder(text, fieldNames);
    }

    /**
     * 关键字，精确匹配
     *
     * @param name 字段名称
     * @param text 查询值
     */
    public static MatchQueryBuilder matchQuery(String name, Object text) {
        return new MatchQueryBuilder(name, text);
    }

    /**
     * 关键字范围查询（后面跟范围条件）
     *
     * @param name 字段名称
     */
    public static RangeQueryBuilder rangeQuery(String name) {
        return new RangeQueryBuilder(name);
    }

    /**
     * 判断字段是否有值
     *
     * @param name 字段名称
     */
    public static ExistsQueryBuilder existsQuery(String name) {
        return new ExistsQueryBuilder(name);
    }

    /**
     * 模糊查询
     *
     * @param name  字段名称
     * @param value 查询值
     */
    public static FuzzyQueryBuilder fuzzyQuery(String name, String value) {
        return new FuzzyQueryBuilder(name, value);
    }

    /**
     * 组合查询对象，可以同时引用上面的所有查询对象
     */
    public static BoolQueryBuilder boolQuery() {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        return new BoolQueryBuilder();
    }

    /**
     * 根据字段聚合，统计该字段的每个值的数量
     */
    public static TermsAggregationBuilder terms(String name) {
        TermsAggregationBuilder termsAggregationBuilder = new TermsAggregationBuilder(name);
        return new TermsAggregationBuilder(name);
    }

    /**
     * 统计操作的，过滤条件
     */
    public static FilterAggregationBuilder filter(String name, QueryBuilder filter) {
        return new FilterAggregationBuilder(name, filter);
    }

    /**
     * 设置多个过滤条件
     */
    public static FiltersAggregationBuilder filters(String name, FiltersAggregator.KeyedFilter... filters) {
        return new FiltersAggregationBuilder(name, filters);
    }

    /**
     * 统计该字段的数据总数
     */
    public static ValueCountAggregationBuilder count(String name) {
        return new ValueCountAggregationBuilder(name);
    }

    /**
     * 计算平均值
     */
    public static AvgAggregationBuilder avg(String name) {
        return new AvgAggregationBuilder(name);
    }

    /**
     * 计算最大值
     */
    public static MaxAggregationBuilder max(String name) {
        return new MaxAggregationBuilder(name);
    }

    /**
     * 计算最小值
     */
    public static MinAggregationBuilder min(String name) {
        return new MinAggregationBuilder(name);
    }

    /**
     * 计算总数
     */
    public static SumAggregationBuilder sum(String name) {
        return new SumAggregationBuilder(name);
    }

}
