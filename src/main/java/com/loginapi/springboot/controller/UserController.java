package com.loginapi.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginapi.springboot.payload.request.MessageResponse;
import com.loginapi.springboot.payload.request.UserRequest;
import com.loginapi.springboot.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    
    @PostMapping("/add")
    public ResponseEntity<MessageResponse> addUser( @RequestBody UserRequest user) {
        MessageResponse newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    
}