package com.colletech.municipality.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colletech.municipality.dao.Municipality;
import com.colletech.municipality.exceptions.NotFoundException;
import com.colletech.municipality.services.IMunicipalityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1/municipality")
@Api(tags = {
		"Controlador de Municipalidad"
},description = "Esta API tiene el CRUD de Municipalidad")
public class MunicipalityController {
	
	Logger log = LoggerFactory.getLogger(MunicipalityController.class);
	
	@Autowired
	IMunicipalityService service;
	
	@ApiOperation(value = "Listado de personas", response = ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
	})
	@GetMapping(value = "/persons")
	public ResponseEntity<?> getListPersons(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@ApiOperation(value = "Obtener de persona por DNI", response = ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
		@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "NO ENCONTRADO")
	})
	@GetMapping(value = "/person/{dni}")
	public ResponseEntity<?> getPerson(@PathVariable(required = true) Long dni){
		Optional<Municipality> optional = service.getMunicipalityByPersonDni(dni);
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Registro de personas", response = ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code = HttpServletResponse.SC_CREATED, message = "ELEMENTO CREADO"),
		@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "CAMPOS FALTANTES")
	})
	@PostMapping
	public ResponseEntity<?> createPerson(@ApiParam(value = "Persona a registrar", required = true) 
													@Valid @RequestBody(required = true) Municipality municipality, BindingResult result){
		log.info("municipality {}", municipality);
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			log.error("Errores de validacion: ", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
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
	
	@ApiOperation(value = "Actualizacion de registro de personas", response = ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code = HttpServletResponse.SC_OK, message = "ELEMENTO ACTUALIZADO"),
		@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "ELEMENTO NO ENCONTRADO")
	})
	@PutMapping
	public ResponseEntity<?> updatePerson(@RequestBody(required = true) Municipality municipality){
		Map<String, Object> response = new HashMap<>();
		try {
			response.put("mensaje", "Actualización satisfactoria");
			response.put("municipality", service.update(municipality));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NotFoundException e) {
			response.put("mensaje", "No se ha podido modificar");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch(DataAccessException e) {
			response.put("mensaje", "No se ha podido modificar");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
