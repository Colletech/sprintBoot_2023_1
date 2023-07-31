package pe.colletech.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import pe.colletech.security.dao.User;
import pe.colletech.security.dao.details.MiUserDetails;

@Component
public class MiUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserService userService;

	/**
	 * Cargamos los datos obtenidos de la consulta hacia la BD y retornamos un
	 * objeto <b>UserDetails</b> como nuestra clase <b>MiUserDetails</b> lo
	 * implementa la podemos usar, en su constructor le pasamos el usuario de la BD
	 * para poblarlo.
	 * 
	 * @param username nombre del usuario a buscar
	 * @return UserDetails que poblara por medio de <b>Usuario</b>
	 * @throws UsernameNotFoundException Si no encuentra el registro en la BD.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> usuario = userService.buscarUsuarioPorUsername(username);
		usuario.orElseThrow(() -> new UsernameNotFoundException("No se encontro el usuario " + username + " en la BD"));
		return usuario.map(MiUserDetails::new).get();
	}
}
