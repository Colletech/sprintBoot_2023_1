package pe.colletech.security.dao;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
}
