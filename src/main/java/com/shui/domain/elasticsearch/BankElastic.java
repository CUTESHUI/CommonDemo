//package com.shui.domain.elasticsearch;
//
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//@Data
//@Document(indexName = "bank")
//public class BankElastic {
//
//    @Id
//    private Long id;
//
//    private Long account_number;
//
//    private Long balance;
//
//    private String firstname;
//
//    private String lastname;
//
//    private Long age;
//
//    @Field(type = FieldType.Keyword)
//    private String gender;
//
//    @Field(analyzer = "ik_max_word", type = FieldType.Text)
//    private String address;
//
//    @Field(type = FieldType.Keyword)
//    private String employer;
//
//    private String email;
//
//    private String city;
//
//    @Field(type = FieldType.Keyword)
//    private String state;
//
//    @Field(type = FieldType.Nested)
//    private ObjElastic obj;
//
//
//    private class ObjElastic {
//
//        private Long f1;
//
//        private Long f2;
//    }
//}
