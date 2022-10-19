package com.hali.spring;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonnelRepository extends MongoRepository<Personnel, String> {

}
