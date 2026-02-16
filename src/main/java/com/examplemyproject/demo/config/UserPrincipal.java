package com.examplemyproject.demo.config;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.examplemyproject.demo.entity.Users;
public class UserPrincipal implements UserDetails{
    
    private final Users user;

    public UserPrincipal(Users user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName()));
    }

    @Override
    public String getUsername(){
        return user.getEmail();
    }
    @Override
    public String getPassword(){
        return user.getPassword();
    }

    // Account never locked
    public boolean isAccountNonLocked(){
        return true;
    }

    // Credentials never expire 
    public boolean isCredentialsNonExpired(){
        return true;
    }

    // Account is enabled
    public boolean isEnabled(){
        return true;
    }

    public Users getUser(){
        return user;
    }


    
}
