package com.shui.domain.mongodb;

import com.shui.utils.mongoHelper.bean.BaseMongoModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "user")
public class MongoUser extends BaseMongoModel implements Serializable {

    private String name;
    private Integer age;

}
