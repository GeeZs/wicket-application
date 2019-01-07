package com.example.wicketapplication.service;

import com.example.wicketapplication.dao.UserDAOImpl;
import com.example.wicketapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAOImpl userDAO;

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public User getUserByName(String firstName){
        return userDAO.getUserByName(firstName);
    }

    public User getUserByUsername(String username){
        return userDAO.getUserByUsername(username);
    }

    public void addUser(User user){
        userDAO.addUser(user);
    }

    public void updateUser(User user){
        userDAO.updateUser(user);
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
