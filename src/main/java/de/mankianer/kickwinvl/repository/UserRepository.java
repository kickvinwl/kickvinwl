package de.mankianer.kickwinvl.repository;

import de.mankianer.kickwinvl.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  public User findByUserName(String name);

  public User findBySessionKey(String token);

}
