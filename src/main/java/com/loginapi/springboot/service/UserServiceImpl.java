package com.loginapi.springboot.service;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginapi.springboot.entity.User;
import com.loginapi.springboot.payload.request.MessageResponse;
import com.loginapi.springboot.payload.request.UserRequest;
import com.loginapi.springboot.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public MessageResponse createUser(UserRequest userRequest) {
		User newUser = new User();
		newUser.setUsername(userRequest.getUsername());
		isValidPassword(userRequest.getPassword());
		newUser.setPassword(userRequest.getPassword());
		userRepository.save(newUser);
		return new MessageResponse("New User created successfully");
	}

	@Override
	public User getASingleUser(Integer userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId);
	}
	 public static boolean
	    isValidPassword(String password)
	    {
	 
	        // Regex to check valid password.
	        String regex = "^(?=.*[0-9])"
	                       + "(?=.*[a-z])(?=.*[A-Z])"
	                       + "(?=.*[_#$%])"
	                       + "(?=\\S+$).{8,20}$";
	 
	        // Compile the ReGex
	        Pattern p = Pattern.compile(regex);
	 
	        // If the password is empty
	        // return false
	        if (password == null) {
	            return false;
	        }
	 
	        // Pattern class contains matcher() method
	        // to find matching between given password
	        // and regular expression.
	        Matcher m = p.matcher(password);
	 
	        // Return if the password
	        // matched the ReGex
	        return m.matches();
	    }
	 



}
