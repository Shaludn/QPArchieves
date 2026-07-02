package com.qparchives.service.impl;

import com.qparchives.model.User;
import com.qparchives.repository.UserRepository;
import com.qparchives.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User registerUser(User user) {
        return repository.save(user);
    }

    @Override
    public User loginUser(String username, String password) {

        User user = repository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    @Override
    public boolean usernameExists(String username) {
        return repository.existsByUsername(username);
    }
}