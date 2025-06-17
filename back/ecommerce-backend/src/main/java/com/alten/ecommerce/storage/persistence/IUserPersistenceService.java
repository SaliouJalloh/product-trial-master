package com.alten.ecommerce.storage.persistence;

import com.alten.ecommerce.storage.model.User;

public interface IUserPersistenceService {
  /** Retourne l'utilisateur avec l'ID donné ou lève une ResourceNotFoundException si non trouvé. */
  User findUserById(Long userId);

  User findUserByEmail(String email);

  User saveUser(User user);

  boolean existsByEmail(String email);
}
