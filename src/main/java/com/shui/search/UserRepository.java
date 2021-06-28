package com.shui.search;

import com.shui.domain.UserElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends ElasticsearchRepository<UserElastic, Long> {

}
