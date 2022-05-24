//package com.shui.domain.elasticsearch;
//
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import java.util.Date;
//
//@Data
//@Document(indexName = "user", replicas = 0)
//public class UserElastic {
//
//    @Id
//    private Long id;
//
//    private String name;
//
//    @Field(type = FieldType.Keyword)
//    private String password;
//
//    @Field(analyzer = "ik_max_word", type = FieldType.Text)
//    private String address;
//
//    @Field(type = FieldType.Keyword)
//    private String phone;
//
//    private Long creator;
//
//    private Long updator;
//
//    private Date create_date;
//
//    private Date update_date;
//
//    private Integer version;
//
//}
