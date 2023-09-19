package com.loginapi.springboot.service;


import org.springframework.stereotype.Component;

import com.loginapi.springboot.entity.User;
import com.loginapi.springboot.payload.request.MessageResponse;
import com.loginapi.springboot.payload.request.UserRequest;

@Component
public interface UserService {
	MessageResponse createUser(UserRequest userRequest);
	User getASingleUser(Integer userId);
}
