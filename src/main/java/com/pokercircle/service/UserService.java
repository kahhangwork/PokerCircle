package com.pokercircle.service;

import java.util.List;
import com.pokercircle.dao.DaoException;
import com.pokercircle.dao.UserDao;
import com.pokercircle.domain.User;
import org.mindrot.jbcrypt.BCrypt;

// To Do: Hash user paswsword 
// To Do: Relationship between user and group

public class UserService {
    private UserDao userDao;

    // CONSTRUCTORS
    public UserService() {
        this.userDao = new UserDao();
    }

    public User createUser(User user) {
        try {
            user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt()));
            userDao.create(user);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return user;
    }


    public User getUser(Integer id) {
        try {
            return userDao.read(id);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public List<User> getAllUsers() {
        try {
            return userDao.readAll();
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public boolean updateUser(User user) {
        try {
            return userDao.update(user) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
        }

    
    public boolean deleteUser(Integer id) {
        try {
            return userDao.delete(id) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        } return false;
    }
}
