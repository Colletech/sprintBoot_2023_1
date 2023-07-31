package pe.colletech.security.services;

import java.util.List;
import java.util.Optional;

import pe.colletech.security.dao.User;

public interface IUserService {
	
	/**
	 * Envuelve al metodo <b>findAll</b> de <b>MongoRepository</b> el cual devuelve
	 * todos los registros de la tabla <b>user</b>
	 * 
	 * @return List(user)
	 */
	List<User> buscarTodosUsuarios();

	/**
	 * Envuelve al metodo <b>findByDni</b> de <b>MongoRepository</b> el cual retorna el
	 * registro buscado por su dni.
	 * 
	 * @param dni
	 * @return user
	 */
	User buscarUsuarioPorDni(Long dni);

	/**
	 * Envuelve el metodo de la consulta personalizada en el repositorio
	 * {@link pe.colletech.security.repository.UserRepository}
	 * <b>buscarUsuarioPorUsername</b> que retorna un registro de la BD por su
	 * username.
	 * 
	 * @param username
	 * @return Optional(user)
	 */
	Optional<User> buscarUsuarioPorUsername(String username);

	/**
	 * Envuelve el metodo de la consulta personalizada en el repositorio
	 * {@link pe.colletech.security.repository.UserRepository}
	 * <b>buscarUsuarioPorCorreo</b> que retorna un registro de la BD por su correo.
	 * 
	 * @param correo
	 * @return Usuario
	 */
	User buscarUsuarioPorCorreo(String correo);

	/**
	 * Envuelve al metodo <b>save</b> de <b>MongoRepository</b> que guarda en la BD al
	 * usuario que se pasa atraves del su parametro
	 * 
	 * @param user
	 */
	User registrarUsuario(User user);

	/**
	 * Envuelve al metodo <b>findByDni</b> de <b>MongoRepository</b> este deshabilitar un
	 * registro de la BD por medio de su dni.
	 * 
	 * @param dni
	 * @throws Exception 
	 */
	void deshabilitarUsuarioPorDni(Long Dni) throws Exception;
}
