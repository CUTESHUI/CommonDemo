package com.shui.config.mongo;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Mongo数据源上下文
 */
@Component
public class MongoContext {

    private static final Map<String, MongoDatabaseFactory> MONGO_CLIENT_DB_FACTORY_MAP = new HashMap<>();
    private static final ThreadLocal<MongoDatabaseFactory> MONGO_DB_FACTORY_THREAD_LOCAL = new ThreadLocal<>();

    @Autowired
    private DynamicMongoListProperties dynamicMongoListProperties;

    @PostConstruct
    public void afterPropertiesSet() {
        List<MongoList> list = dynamicMongoListProperties.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(info -> MONGO_CLIENT_DB_FACTORY_MAP.put(info.getDatabase(), new SimpleMongoClientDatabaseFactory(info.getUri())));
        }
    }

    @Bean(name = "mongoTemplate")
    public DynamicMongoTemplate dynamicMongoTemplate() {
        Iterator<MongoDatabaseFactory> iterator = MONGO_CLIENT_DB_FACTORY_MAP.values().iterator();
        return new DynamicMongoTemplate(iterator.next());
    }

    @Bean(name = "mongoDatabaseFactory")
    public MongoDatabaseFactory mongoDatabaseFactory() {
        Iterator<MongoDatabaseFactory> iterator = MONGO_CLIENT_DB_FACTORY_MAP.values().iterator();
        return iterator.next();
    }

    public static void setMongoDbFactory(String name) {
        MONGO_DB_FACTORY_THREAD_LOCAL.set(MONGO_CLIENT_DB_FACTORY_MAP.get(name));
    }

    public static MongoDatabaseFactory getMongoDbFactory() {
        return MONGO_DB_FACTORY_THREAD_LOCAL.get();
    }


    public static void removeMongoDbFactory() {
        MONGO_DB_FACTORY_THREAD_LOCAL.remove();
    }

}
