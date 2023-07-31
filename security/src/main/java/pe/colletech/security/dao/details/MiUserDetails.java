package pe.colletech.security.dao.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.colletech.security.dao.User;

/**
 * Esta clase proporciona la implementacion para establecer informacion del
 * usuario. Almacenando información del usuario que más tarde se encapsula en
 * objetos de autenticación
 */
public class MiUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private Boolean active;
	private List<SimpleGrantedAuthority> authorities;

	public MiUserDetails(User usuario) {
		this.authorities = new ArrayList<>();
		this.username = usuario.getUsername();
		this.password = usuario.getPassword();
		this.active = usuario.getActive();
		usuario.getRoles().forEach(a -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(a.getName()))));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}
}
