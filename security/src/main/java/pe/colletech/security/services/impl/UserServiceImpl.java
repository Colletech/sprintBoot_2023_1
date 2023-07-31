package pe.colletech.security.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mongodb.client.result.UpdateResult;
import pe.colletech.security.dao.User;
import pe.colletech.security.repository.UserRepository;
import pe.colletech.security.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoOperations operations;

	@Override
	@Transactional(readOnly = true)
	public List<User> buscarTodosUsuarios() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User buscarUsuarioPorDni(Long dni) {
		return getUser(dni.toString());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> buscarUsuarioPorUsername(String username) {
		Query query = new Query(Criteria.where("username").is(username));
		return Optional.ofNullable(operations.findOne(query, User.class));
	}

	@Override
	@Transactional(readOnly = true)
	public User buscarUsuarioPorCorreo(String correo) {
		Query query = new Query(Criteria.where("email").is(correo));
		return Optional.ofNullable(operations.findOne(query, User.class)).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public User registrarUsuario(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(true);
		userRepository.save(user);
		Query query = new Query(Criteria.where("_id").is(user.getId()));
		return Optional.ofNullable(operations.findOne(query, User.class)).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public void deshabilitarUsuarioPorDni(Long Dni) throws Exception {
		Query query = new Query(Criteria.where("dni").is(Dni.toString()));
		Update update = new Update();
		update.set("active", Boolean.FALSE);
		UpdateResult result = operations.updateFirst(query, update, User.class);
		if (result.getMatchedCount() == 0) {
			throw new Exception(Dni.toString());
		}
	}

	private User getUser(String dni) {
		Query query = new Query(Criteria.where("dni").is(dni));
		return Optional.ofNullable(operations.findOne(query, User.class)).orElse(null);
	}
}
