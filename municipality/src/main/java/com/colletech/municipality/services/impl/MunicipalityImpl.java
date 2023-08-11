package com.colletech.municipality.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.colletech.municipality.dao.Municipality;
import com.colletech.municipality.exceptions.NotFoundException;
import com.colletech.municipality.repository.MunicipalityRepository;
import com.colletech.municipality.services.IMunicipalityService;
import com.mongodb.client.result.UpdateResult;

@Service
public class MunicipalityImpl implements IMunicipalityService {
	
	Logger log = LoggerFactory.getLogger(MunicipalityImpl.class);
	
	@Autowired
	private MunicipalityRepository repository;
	
	@Autowired
	private MongoOperations operations;

	@Override
	@Transactional(readOnly = true)
	public List<Municipality> getAll() {
		log.info("Se está llamando a todos los registros");
		return repository.findAll();
	}

	@Override
	public Optional<Municipality> getMunicipalityByPersonDni(Long dni) {
		log.info("DNI: {}", dni);
		Query query = new Query(Criteria.where("person.dni").is(dni));
		return Optional.ofNullable(operations.findOne(query, Municipality.class));
	}

	@Override
	@Transactional
	public Optional<Municipality> create(Municipality municipality) {
		log.info("Municipality: {}", municipality.toString());
		repository.save(municipality);
		return getMunicipality(municipality.getId());
	}

	@Override
	@Transactional
	public Municipality update(Municipality municipality) throws NotFoundException {
		Query query = new Query(Criteria.where("_id").is(municipality.getId()));
		Update update = new Update();
		update.set("person", municipality.getPerson());
		UpdateResult result = operations.updateFirst(query, update, Municipality.class);
		if (result.getMatchedCount() == 0) {
			log.error("No se han encontrado registros con el ID {}", municipality.getId());
			throw new NotFoundException(municipality.getId());
		}
		return getMunicipality(municipality.getId()).get();
	}
	
	private Optional<Municipality> getMunicipality(String id){
		Query query = new Query(Criteria.where("_id").is(id));
		return Optional.ofNullable(operations.findOne(query, Municipality.class));
	}

}
