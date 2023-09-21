package com.loginapi.springboot.payload.request;


import java.net.InetAddress;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRequest {
	
	@NotBlank
    @NotNull
    private String username;
 
    @NotBlank
    @NotNull
    private String password;
    
    @NotBlank
    @NotNull
    private InetAddress ipAddress;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public InetAddress getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

}
