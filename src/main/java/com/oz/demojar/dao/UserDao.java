package com.oz.demojar.dao;

import com.oz.demojar.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> selectAllUsers();

    int deleteUserById(Long id);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    void save(User user);

    long findLastId();

}
