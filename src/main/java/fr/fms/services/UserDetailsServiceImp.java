package fr.fms.services;

import fr.fms.dao.UserRepository;
import fr.fms.dao.UserRepository;
import fr.fms.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    System.out.println("Loading user: " + username);
    System.out.println("Found: " + user);
    System.out.println("All users: " + userRepository.findAll());
    return org.springframework.security.core.userdetails.User.withUsername(
      user.getUsername()
    )
      .password(user.getPassword())
      .authorities(user.getType())
      .build();
  }
}
