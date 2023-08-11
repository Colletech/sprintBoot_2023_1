package com.colletech.iglesia.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.colletech.iglesia.dao.Church;
import com.colletech.iglesia.services.IIglesiaServices;

@RestController
@RequestMapping(value = "/api")
public class IglesiaController {

	@Autowired
	private IIglesiaServices iglesiaServices;

	@GetMapping(value = "/creyentes")
	public ResponseEntity<?> getListadoCreyentes() {
		return ResponseEntity.ok(iglesiaServices.getListCreyentes());
	}

	@PostMapping(value = "/iglesia")
	public ResponseEntity<?> saveCreyente(@Valid @RequestBody(required = true) Church iglesia) {
		return ResponseEntity.ok(iglesiaServices.registrarCreyente(iglesia));
	}
}
