package com.colletech.municipality.dao;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Document(collection = "person")
@ApiModel(value = "Persona", description = "Persona de la municipalidad")
public class Person {

	@Id
	@ApiModelProperty(value = "Identificador de base de datos", required = false)
	@JsonIgnore
	private Long id;

	@ApiModelProperty(value = "Documento Nacional de Identidad de la persona", required = true)
	@Min(message = "El valor mínimo es 1", value = 1)
	@Max(message = "El valor mínimo es 8", value = 8)
	private Long dni;

	@NotNull(message = "Campo name obligatorio")
	@ApiModelProperty(value = "Nombre de la persona", required = true)
	private String name;

	@NotNull(message = "Campo lastName obligatorio")
	@ApiModelProperty(value = "Apellido de la persona", required = true)
	private String lastName;

	@NotEmpty
	@ApiModelProperty(value = "Dirección física de la persona", required = true)
	private String address;

	@ApiModelProperty(value = "Número de celular de la persona", required = true)
	private Long numberCell;

	@ApiModelProperty(value = "Fecha de nacimiento de la persona", required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date brithdate;
}
