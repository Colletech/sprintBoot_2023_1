package com.colletech.municipality.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.colletech.municipality.dao.Municipality;

@Repository
public interface MunicipalityRepository extends MongoRepository<Municipality, Long>{

}
