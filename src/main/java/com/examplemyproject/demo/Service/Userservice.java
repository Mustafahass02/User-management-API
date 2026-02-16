package com.examplemyproject.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examplemyproject.demo.DTO.User_request;
import com.examplemyproject.demo.entity.Role;
import com.examplemyproject.demo.entity.Users;
import com.examplemyproject.demo.model.AuthResponse;
import com.examplemyproject.demo.repository.Role_repository;
import com.examplemyproject.demo.repository.User_repository;

@Service
public class Userservice {

    private final User_repository user_repo;
    private final Role_repository role_repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwt;

    public Userservice(User_repository user_repo, Role_repository role_repo, PasswordEncoder passwordEncoder, JwtService jwt) {
        this.user_repo = user_repo;
        this.role_repo = role_repo;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

   public Users createUser(User_request user_request) {
    Optional<Users> existing = user_repo.findByEmail(user_request.getEmail());

    if (existing.isPresent()) {
        throw new RuntimeException("Email already exists");
    }

   Role role = role_repo.findByRoleNameIgnoreCase(user_request.getRoleName())
    .orElseThrow(() -> new RuntimeException("Role not found"));



    Users user = new Users();
    user.setFirstName(user_request.getFirstName());
    user.setLastName(user_request.getLastName());
    user.setAge(user_request.getAge());
    user.setEmail(user_request.getEmail());
    user.setPassword(passwordEncoder.encode(user_request.getPassword()));
    user.setRole(role); // attach the managed Role

    user_repo.save(user);
    return user;
}

    public Users findByName(User_request user_request) {
        Optional<Users> user = user_repo.findByFirstNameAndLastName(
            user_request.getFirstName(),                    // Changed: getFirstName()
            user_request.getLastName()                      // Changed: getLastName()
        );

        if (user.isEmpty()) {
            throw new RuntimeException("Name doesn't exist");
        }

        System.out.println("User found: " + user_request.getFirstName() + " " + user_request.getLastName());
        Users actual = new Users();
        actual = user.get();

        return actual;
    }

    public boolean deleteUser(User_request user_request) {
        Optional<Users> existing = user_repo.findByEmail(user_request.getEmail());

        if (existing.isEmpty()) {
            
            throw new RuntimeException("Email does not exist");
        }

        user_repo.delete(existing.get());
        return true;
    }

    public List<Users> listUsers() {
        return user_repo.findAll();
    }

    public List<Users> findByRole(Role role) {
        return user_repo.findByRole(role);
    }

    public List<Users> findByRoleName(String roleName) {
        return user_repo.findByRole_RoleName(roleName);
    }

    public Optional<Role> findRoleById(Long id) {
        return role_repo.findById(id)
                .or(() -> { throw new RuntimeException("Role ID does not exist"); });
    }

    public Users updateUser(Long id, User_request user_request) {
        Users existingUser = user_repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFirstName(user_request.getFirstName());    // Changed: getFirstName()
        existingUser.setLastName(user_request.getLastName());      // Changed: getLastName()
        existingUser.setAge(user_request.getAge());
        existingUser.setEmail(user_request.getEmail());
        existingUser.setPassword(user_request.getPassword());

        user_repo.save(existingUser);
        return existingUser;
    }


   
    public AuthResponse login(User_request user_request){
        Optional<Users> retrieved = user_repo.findByEmail(user_request.getEmail());

        if(retrieved.isEmpty()){
             throw new RuntimeException("account does not exist");
        }

        Users user = retrieved.get();

        if(!passwordEncoder.matches(user_request.getPassword(), user.getPassword()) ){
              throw new RuntimeException("invalid credentials");
            

        }else{
            String token = jwt.generateToken(user);
            return new AuthResponse (token);
        }

    }

    public Optional<Users> findemail (String email){
        if (email == null || email.isBlank()) {
        throw new IllegalArgumentException("Email cannot be null or empty");
    }

        return user_repo.findByEmail(email);
    }



}