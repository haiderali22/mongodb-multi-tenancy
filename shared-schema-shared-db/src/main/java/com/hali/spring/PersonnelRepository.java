package com.hali.spring;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonnelRepository extends MongoRepository<Personnel, String> {
	Optional<Personnel> findByName(String name);
}
