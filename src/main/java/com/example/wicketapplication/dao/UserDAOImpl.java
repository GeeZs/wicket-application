package com.example.wicketapplication.dao;

import com.example.wicketapplication.mapper.UserMapper;
import com.example.wicketapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl extends JdbcDaoSupport implements UserDAO{

    @Autowired
    public UserDAOImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "select * from usr";
        Object[] params = new Object[]{};
        UserMapper mapper = new UserMapper();
        List<User> list = null;
        if (this.getJdbcTemplate() != null) {
            list = this.getJdbcTemplate().query(sql, params, mapper);
            return list;
        }else {
            return null;
        }

    }

    @Override
    public User getUserByName(String firstName) {
        String sql = "select * from usr where first_name=?";
        return getUser(firstName, sql);
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "select * from usr where username=?";
        return getUser(username, sql);
    }

    private User getUser(String username, String sql) {
        Object[] params = new Object[]{username};
        UserMapper mapper = new UserMapper();
        User user = null;
        if (this.getJdbcTemplate() != null) {
            user = this.getJdbcTemplate().queryForObject(sql, params, mapper);
        }
        if (user != null){
            return user;
        }else {
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into usr(id, first_name, second_name, username, password) values (?, ?, ?, ?, ?)";
        Object[] params = new Object[]{user.getId(), user.getFirstName(), user.getSecondName(), user.getUsername(), user.getPassword()};
        if (this.getJdbcTemplate() != null) {
            this.getJdbcTemplate().update(sql, params);
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "update usr set first_name=?, second_name=?, username=?, password=? where id=?";
        Object[] params = new Object[]{user.getFirstName(), user.getSecondName(), user.getUsername(), user.getPassword(), user.getId()};
        if (this.getJdbcTemplate() != null) {
            this.getJdbcTemplate().update(sql, params);
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "delete from usr where id=?";
        if (this.getJdbcTemplate() != null) {
            this.getJdbcTemplate().update(sql, id);
        }
    }

}
