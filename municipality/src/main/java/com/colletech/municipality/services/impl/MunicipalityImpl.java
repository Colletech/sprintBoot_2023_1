package com.colletech.municipality.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.colletech.municipality.dao.Municipality;
import com.colletech.municipality.repository.MunicipalityRepository;
import com.colletech.municipality.services.IMunicipalityService;

@Service
public class MunicipalityImpl implements IMunicipalityService {
	
	@Autowired
	private MunicipalityRepository repository;
	
	@Autowired
	private MongoOperations operations;

	@Override
	@Transactional(readOnly = true)
	public List<Municipality> getAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Municipality> getMunicipalityByPersonDni(Long dni) {
		return Optional.empty();
	}

	@Override
	public Optional<Municipality> create(Municipality municipality) {
		repository.save(municipality);
		return getMunicipality(municipality.getId());
	}

	@Override
	public Municipality update(Municipality municipality) {
		return null;
	}
	
	private Optional<Municipality> getMunicipality(String id){
		Query query = new Query(Criteria.where("_id").is(id));
		return Optional.ofNullable(operations.findOne(query, Municipality.class));
	}

}
