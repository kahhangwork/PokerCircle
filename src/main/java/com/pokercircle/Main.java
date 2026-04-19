package com.pokercircle;

import com.pokercircle.domain.User;
import com.pokercircle.domain.Group;
import com.pokercircle.service.UserService;
import com.pokercircle.service.GroupService;

public class Main {
    public static void main(String[] args) {

        // User user3 = new User("jim@example.com", "Jim Smith", "mypass");
        // User user2 = new User();
        // User user3 = new User(2, "alice@example.com", "Alice"); 
        
        // System.out.println(user.getId());

        // System.out.println(user);
        // System.out.println(user2);
        // System.out.println(user3);  


        // DataSourceFactory factory = DataSourceFactory.instance();
        // factory.getDataSource();
        // UserDao userDao = new UserDao();
        // try {
        //     userDao.delete(7);
        //     System.out.println(userDao.readAll().size());
        // } catch (DaoException ex) {
        //     ex.printStackTrace();
        // }       

        UserService userService = new UserService();
        // userService.createUser(user3);
        // userService.getAllUsers().forEach(System.out::println);

        User user = userService.getUser(1);
        // GroupService grpService = new GroupService();
        // Group group = new Group ("user", user.getId(), "User Group");
        // grpService.createGroup(group);
        // user.setPasswordHash("mypass");
        // userService.updateUser(user);
        // userService.deleteUser(11);

        GroupService grpService = new GroupService();
        Group group = new Group ("user")



    }



}


