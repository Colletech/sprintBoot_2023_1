package com.colletech.iglesia.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "iglesia")
public class Iglesia {

	@Id
	private Long id;
	@Column(name = "name")
	private String nombre;
	private String ubicacion;
	private String religion;
}
