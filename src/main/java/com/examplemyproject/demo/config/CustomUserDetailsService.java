package com.examplemyproject.demo.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.examplemyproject.demo.entity.Users;
import com.examplemyproject.demo.repository.User_repository;
@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final User_repository repo;

    public CustomUserDetailsService(User_repository repo){
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String email){
        
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        Optional<Users> us = repo.findByEmail(email);

        Users user = us.get();

        return new UserPrincipal(user);

    }
}
