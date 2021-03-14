//package hcloud.platform.modules.es;
//
//import lombok.Data;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
////@Document(indexName = "account", type = "person", shards = 1, replicas = 0)
//@Data
//public class Person {
////    @Id
////    private Long id;
//
//    @Field(type = FieldType.Text)
//    private String code;
//
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
//    private String name;
//
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
//    private String post;
//
//    public Person(String code, String name, String post) {
//        this.code = code;
//        this.name = name;
//        this.post = post;
//    }
//}
