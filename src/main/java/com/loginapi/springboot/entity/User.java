package com.loginapi.springboot.entity;

import java.net.InetAddress;

import jakarta.persistence.*;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String username;
    
    private String password;
    
    private InetAddress ipAddress;
    
    private String city;
    
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }


}