package com.oz.demojar.service;

import com.oz.demojar.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void saveUser(User user);
}
