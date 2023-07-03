package com.colletech.municipality.dao;

import java.util.Date;

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
@Table(name = "person")
public class Person {
	
	@Id
	private Long id;
	private Long dni;
	private String name;
	private String lastName;
	private String address;
	private Long numberCell;
	private Date brithdate;
}
