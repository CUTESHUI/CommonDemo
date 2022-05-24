package com.shui.config.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "dynamic.mongo")
public class DynamicMongoListProperties {

    private List<MongoList> list;
}