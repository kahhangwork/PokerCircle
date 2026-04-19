package com.pokercircle;

import com.pokercircle.domain.User;
import com.pokercircle.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = new User("julia@example.com", "Julia Chan", "mypass");
        userService.createUser(user);
        System.out.println(user.getPasswordHash());



    }



}


