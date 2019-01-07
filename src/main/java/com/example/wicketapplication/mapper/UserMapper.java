package com.example.wicketapplication.mapper;

import com.example.wicketapplication.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String secondName = resultSet.getString("second_name");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");

        return new User(id, firstName, secondName, username, password);
    }
}
