package com.colletech.municipality.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colletech.municipality.dao.Municipality;
import com.colletech.municipality.services.IMunicipalityService;

@RestController
@RequestMapping(value = "/api/v1/municipality")
public class MunicipalityController {
	
	@Autowired
	IMunicipalityService service;
	
	@GetMapping(value = "/persons")
	public ResponseEntity<?> getListPersons(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping(value = "/persons/{id}")
	public ResponseEntity<?> getPerson(){
		return null;
	}
	
	@PostMapping
	public ResponseEntity<?> createPerson(@RequestBody(required = true) Municipality municipality){
		Map<String, Object> response = new HashMap<>();
		try {
			Optional<Municipality> optional = service.create(municipality);
			response.put("mensaje", "Registro satisfactorio");
			response.put("municipality", optional.get());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "No se ha podido registrar");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updatePerson(){
		return null;
	}

}
