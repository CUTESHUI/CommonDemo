package com.shui.repository;

import com.shui.domain.mongodb.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<MongoUser, String> {

}
