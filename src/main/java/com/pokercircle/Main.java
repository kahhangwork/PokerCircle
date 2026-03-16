package com.pokercircle;

import java.time.LocalDateTime;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class Main {
    public static void main(String[] args) {
        User user = new User("bob@example.com", "Bob Dylan", "mypass");
        User user3 = new User("john@example.com", "John Collins", "mypass");
        // User user2 = new User();
        // User user3 = new User(2, "alice@example.com", "Alice"); 
        
        // System.out.println(user.getId());

        // System.out.println(user);
        // System.out.println(user2);
        // System.out.println(user3);  


        // DataSourceFactory factory = DataSourceFactory.instance();
        // factory.getDataSource();
        UserDao userDao = new UserDao();
        try {
            // int id = userDao.create(user3);
            // User user2 = userDao.read(8);
            // userDao.delete(8);
            System.out.println(userDao.readAll().size());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
    }
}
