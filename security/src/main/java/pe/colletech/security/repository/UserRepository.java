package pe.colletech.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.colletech.security.dao.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
