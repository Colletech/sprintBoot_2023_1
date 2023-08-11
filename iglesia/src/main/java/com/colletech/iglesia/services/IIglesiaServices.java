package com.colletech.iglesia.services;

import java.util.List;
import com.colletech.iglesia.dao.Church;

public interface IIglesiaServices {

	List<Church> getListCreyentes();

	Church registrarCreyente(Church iglesia);
}
