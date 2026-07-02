package com.qparchives.service;

import com.qparchives.model.User;

public interface UserService {

    User registerUser(User user);

    User loginUser(String username, String password);

    boolean usernameExists(String username);

}