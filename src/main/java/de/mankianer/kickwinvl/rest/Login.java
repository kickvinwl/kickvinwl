package de.mankianer.kickwinvl.rest;

import de.mankianer.kickwinvl.entities.User;
import de.mankianer.kickwinvl.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

  @Autowired
  UserRepository userRepository;

  @RequestMapping(path = "/login/", method = RequestMethod.POST)
  public String login(@RequestParam String name, @RequestParam String pw)
  {
    User us = userRepository.findByUserName(name);
    //TODO validate User
    if(us == null) {
      us = new User(name);
    }
    us.setSessionKey(UUID.randomUUID().toString());
    userRepository.save(us);
    return us.getSessionKey();
  }

  @RequestMapping("/login/logout/{token}")
  public void logout(@PathVariable String token)
  {
    User user = userRepository.findBySessionKey(token);
    user.setSessionKey("");
    userRepository.save(user);
  }
}
