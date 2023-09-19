package com.loginapi.springboot.repository;

import com.loginapi.springboot.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

	User findById(Integer userId);
  
    
}