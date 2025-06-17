package com.alten.ecommerce.service.userdetail;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alten.ecommerce.storage.model.User;
import com.alten.ecommerce.storage.persistence.IUserPersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

  private final IUserPersistenceService userPersistenceService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = this.userPersistenceService.findUserByEmail(email);
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), List.of() // pas de rôles ici
        );
  }
}
