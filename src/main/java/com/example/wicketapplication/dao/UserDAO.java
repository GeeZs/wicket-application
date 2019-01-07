package com.example.wicketapplication.dao;

import com.example.wicketapplication.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserByName(String firstName);
    User getUserByUsername(String username);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
}
