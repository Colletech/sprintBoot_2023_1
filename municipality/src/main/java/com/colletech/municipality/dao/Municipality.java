package com.colletech.municipality.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "municipality")
@ApiModel(value = "Municipalidad", description = "Proyecto de municipalidad")
public class Municipality {
	
	@Id
	@ApiModelProperty(value = "Identificador de la Municipalidad", required = false)
	private String id;
	
	@ApiModelProperty(value = "Relación con el objecto Persona", required = true)
	@NotNull(message = "no puede ser nulo")
	private Person person;
}
