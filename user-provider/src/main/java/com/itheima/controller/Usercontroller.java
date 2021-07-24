package com.itheima.controller;

import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class Usercontroller {
    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
    public User findById(@PathVariable(name = "id") Integer id){
        System.out.println("");
        return userService.findById(id);


    }


}
