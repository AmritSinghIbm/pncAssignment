package com.loginapi.springboot.service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginapi.springboot.entity.User;
import com.loginapi.springboot.payload.request.MessageResponse;
import com.loginapi.springboot.payload.request.UserRequest;
import com.loginapi.springboot.repository.UserRepository;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;





@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public MessageResponse createUser(UserRequest userRequest) {
		User newUser = new User();
		newUser.setUsername(userRequest.getUsername());
		if(isValidPassword(userRequest.getPassword())==true) {
			newUser.setPassword(userRequest.getPassword());		
		}
		else {
			return new MessageResponse("Password need to be greater than 8 characters, containing at least 1 number, 1 Capitalized letter, 1 special character in this set “_ # $ % ");
		}
		
		WebServiceClient client = new WebServiceClient.Builder(916427, "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").host("geolite.info").build();

		

		// Do the lookup
		CityResponse response = null;
		try {
			response = client.city(userRequest.getIpAddress());
		} catch (IOException | GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		Country country = response.getCountry();
		 
		//System.out.println(country.getName());               // 'Country'
		City city = response.getCity();
		//System.out.println(city.getName());       // 'city'
		
		if(country.getName().equalsIgnoreCase("Canada")==true) {
			   newUser.setIpAddress(userRequest.getIpAddress());
			   newUser.setCity(city.getName());
		   }
		   else {
				return new MessageResponse("You are not eligible to register because of location restriction");
			}
		
		String dispUsr = userRequest.getUsername();
		
		userRepository.save(newUser);
		int idNumber = newUser.getUserId();
		return new MessageResponse(idNumber+" Welcome "+ dispUsr + " from "+city.getName());
		
	}
	

	 public static boolean isValidPassword(String password)
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
