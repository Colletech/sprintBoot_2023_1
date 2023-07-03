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
	private Long id;
	
	@Column(name = "idMunicipality")
	private Long idMunicipality;
	
	@Column(name = "baptized")
	private Boolean Baptized;
	
	@Column(name = "married")
	private Boolean Married;
}
