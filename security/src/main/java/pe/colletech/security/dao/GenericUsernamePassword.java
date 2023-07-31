package pe.colletech.security.dao;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GenericUsernamePassword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private String username;
	
	@NotNull
	private String password;
}
