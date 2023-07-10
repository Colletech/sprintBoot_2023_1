package com.colletech.municipality.services;

import java.util.List;
import java.util.Optional;

import com.colletech.municipality.dao.Municipality;
import com.colletech.municipality.exceptions.NotFoundException;

public interface IMunicipalityService {

	List<Municipality> getAll();
	
	Optional<Municipality> getMunicipalityByPersonDni(Long dni);
	
	Optional<Municipality> create(Municipality municipality);
	
	Municipality update(Municipality municipality) throws NotFoundException;
}
