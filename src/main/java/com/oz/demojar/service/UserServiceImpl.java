package com.oz.demojar.service;

import com.oz.demojar.dao.UserDao;
import com.oz.demojar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
final class UserServiceImpl implements UserService { //  throws InvalidDataAccessApiUsageException, NoSuchElementFoundException

    @Autowired
    private final UserDao userRepo;

    @Autowired
    private UserServiceImpl(UserDao userDao) {
        this.userRepo = userDao;
    }

    public List<User> getAllUsers() {
        return userRepo.selectAllUsers();
    }

    public User getUserById(Long id) {
        return (userRepo.getUserById(id).isPresent()) ? userRepo.getUserById(id).get() : null;
    }

    public User getUserByUsername(String username) {
        return (userRepo.getUserByUsername(username).isPresent()) ? userRepo.getUserByUsername(username).get() : null;
    }

    public void saveUser(User user) {
        userRepo.save(User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .active(true)
                .roles(user.getRoles())
                .build());
    }

    public User createAdminUser(User user) {
        userRepo.save(User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .active(true)
                .roles(user.getRoles())
                .build());
        Long id = userRepo.findLastId() + 1L;
        user.setId(id);
        return user;
    }
}
