package com.examplemyproject.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User_request {
    
    @JsonProperty("firstName")  // This tells Jackson to map JSON "firstName" to this field
    private String firstName;
    
    @JsonProperty("lastName")   // This tells Jackson to map JSON "lastName" to this field
    private String lastName;
    
    @JsonProperty("age")
    private int age;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private String roleName; 
    
    // Empty constructor
    public User_request() {}
    
    // Getters and setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}