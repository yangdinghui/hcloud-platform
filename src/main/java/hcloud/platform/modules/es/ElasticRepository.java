//package hcloud.platform.modules.es;
//
//import com.github.pagehelper.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.annotations.Query;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//public interface ElasticRepository extends ElasticsearchRepository<Person, String> {
//
//    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"firstCode.keyword\" : \"?\"}}}}")
//    Page<Person> findByFirstCode(String firstCode, Pageable pageable);
//
//
//}
