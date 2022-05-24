package com.shui.config.mongo;

import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * 动态mongo模板
 */
public class DynamicMongoTemplate extends MongoTemplate {

    public DynamicMongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        super(mongoDatabaseFactory);
    }

    @Override
    protected MongoDatabase doGetDatabase() {
        MongoDatabaseFactory mongoDbFactory = MongoContext.getMongoDbFactory();
        return mongoDbFactory == null ? super.doGetDatabase() : mongoDbFactory.getMongoDatabase();
    }
}
