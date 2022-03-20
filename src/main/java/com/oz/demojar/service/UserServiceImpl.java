package com.oz.demojar.service;

import com.oz.demojar.dao.UserDao;
import com.oz.demojar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class UserServiceImpl implements UserService { //  throws InvalidDataAccessApiUsageException, NoSuchElementFoundException

    @Autowired
    private UserDao userRepo;

    @Autowired
    private void PersonServiceImpl(UserDao userDao) {
        this.userRepo = userDao;
    }

    public List<User> getAllUsers() {
        return userRepo.selectAllUsers();
    }

    public User getUserById(Long id) {
        return (userRepo.getUserById(id).isPresent()) ? userRepo.getUserById(id).get() : null;
    }

    public void saveUser(User user){
        userRepo.save(user);
    }
}
