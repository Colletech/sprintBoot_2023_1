package com.colletech.iglesia.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.colletech.iglesia.dao.Church;
import com.colletech.iglesia.repository.IIglesiaRepository;
import com.colletech.iglesia.services.IIglesiaServices;

@Service("iIglesiaServices")
public class IglesiaImpl implements IIglesiaServices {

	@Autowired
	IIglesiaRepository iglesiaRepository;

	@Override
	public List<Church> getListCreyentes() {
		return iglesiaRepository.findAll();
	}

	@Override
	public Church registrarCreyente(Church iglesia) {
		return iglesiaRepository.save(iglesia);
	}
}
