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
@Table(name = "church")
public class Church {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "municipality_id")
	private String idMunicipality;

	@Column(name = "baptized")
	private Boolean baptized;
}
