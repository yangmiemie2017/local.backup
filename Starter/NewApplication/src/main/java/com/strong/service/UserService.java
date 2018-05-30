package com.strong.service;

import com.strong.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * @author cy
 */
//public interface UserService extends UserDetailsService {
public interface UserService {
    Optional<User> getUserById(Long id);

    boolean saveUser(User user);

    boolean modifyUserOnPasswordById(User user);

    boolean deleteUserById(Long id);

}
