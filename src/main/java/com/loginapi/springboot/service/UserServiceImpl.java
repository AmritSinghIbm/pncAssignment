package com.loginapi.springboot.service;


import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginapi.springboot.entity.User;
import com.loginapi.springboot.payload.request.MessageResponse;
import com.loginapi.springboot.payload.request.UserRequest;
import com.loginapi.springboot.repository.UserRepository;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;





@Service
public class UserServiceImpl implements UserService {
	
	IPGeolocationAPI api = new IPGeolocationAPI("9c8615d32d6646ea9196e8a5dc4d7d7c");
	
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
		
		
		GeolocationParams geoParams = new GeolocationParams();
		geoParams.setIPAddress(userRequest.getIpAddress());
		Geolocation geolocation = api.getGeolocation(geoParams);
		
		if(geolocation.getStatus() == 200) {
		   if(geolocation.getCountryName().equalsIgnoreCase("Canada")==true) {
			   newUser.setIpAddress(userRequest.getIpAddress());	
		   }
		   else {
				return new MessageResponse("You are not eligible to register because of location restriction");
			}
		   
		}else {
			return new MessageResponse("Geo API connection error");
		}
		
		userRepository.save(newUser);
		return new MessageResponse("New User created successfully");	
		
	}

	@Override
	public User getASingleUser(Integer userId) {
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
	 
	 /* public static boolean ifLocationCanada(String ipAddress) throws IOException, GeoIp2Exception {
		 
			 	String targetCountry = "CANADA";
			 	
		 		WebServiceClient client = new WebServiceClient.Builder(916427, "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")
		 			    .build();

	 			InetAddress ipInetAddress = InetAddress.getByName(ipAddress);

	 			// Do the lookup
	 			CountryResponse response = client.country(ipInetAddress);

	 			Country country = response.getCountry();
	 			
	 			String countryName = country.getName();
	 			
	 			boolean bool = countryName.equalsIgnoreCase(targetCountry);
	 			
	 			return bool;
	 	}*/
	 



}
