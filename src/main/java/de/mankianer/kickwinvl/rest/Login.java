package de.mankianer.kickwinvl.rest;

import de.mankianer.kickwinvl.entities.User;
import de.mankianer.kickwinvl.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

  @Autowired
  UserRepository userRepository;

  @RequestMapping("login")//(method = RequestMethod.POST)
  public Iterable<User> login(@RequestParam String name, @RequestParam(required = false) String pw)
  {
    User us = userRepository.findByUserName(name);
    if(us == null) {
      us = new User(name);
    }
    us.setSessionKey(UUID.randomUUID().toString());
    userRepository.save(us);
    return userRepository.findAll();
  }

  @RequestMapping("logout")
  public void logout(@RequestParam String sessionKey)
  {
    User user = userRepository.findBySessionKey(sessionKey);
    user.setSessionKey("");
    userRepository.save(user);
  }
}
