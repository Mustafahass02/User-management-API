package com.examplemyproject.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.examplemyproject.demo.DTO.User_request;
import com.examplemyproject.demo.Service.Userservice;
import com.examplemyproject.demo.entity.Users;
import com.examplemyproject.demo.model.AuthResponse;


@RestController
public class user_controller {

   private final Userservice userservice;

   public user_controller(Userservice userservice){
      this.userservice = userservice;
   }


  
   
@PostMapping("/auth/register")
@ResponseStatus(HttpStatus.ACCEPTED)
public Users createuser (@RequestBody User_request user_request){
    System.out.println("Received: firstName=" + user_request.getFirstName() + 
                      ", lastName=" + user_request.getLastName() + 
                      ", email=" + user_request.getEmail());
    
    Users user = userservice.createUser(user_request);
    return user;
}


@GetMapping("/users/listusers")
@PreAuthorize("hasRole('USER')")
public List<Users> listusers (){
   return userservice.listUsers();
}

@DeleteMapping("/users/deleteuser")
@ResponseStatus(HttpStatus.NO_CONTENT)
@PreAuthorize("hasRole('USER')")
public boolean deleete (@RequestBody User_request user_request){
   return  userservice.deleteUser(user_request);

}





   @PostMapping("/test-json")
 public String testJson(@RequestBody Map<String, Object> data) {
    return "Received: " + data.toString();
}


@PostMapping("/auth/login")
public AuthResponse loginning (@RequestBody User_request user_request){
   return userservice.login(user_request);
}

@PutMapping("/users/{id}")
public ResponseEntity<Users> updateUser(@PathVariable Long id, User_request request){
   Users user = userservice.updateUser(id, request);
   return ResponseEntity.ok(user);
}

   




    






    
}
