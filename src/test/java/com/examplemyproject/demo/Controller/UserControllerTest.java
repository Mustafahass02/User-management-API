package com.examplemyproject.demo.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.examplemyproject.demo.config.*;
import com.examplemyproject.demo.entity.*;
import com.examplemyproject.demo.Service.*;
import com.examplemyproject.demo.controller.user_controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.examplemyproject.demo.DTO.*;

@WebMvcTest(value = user_controller.class)
@AutoConfigureMockMvc(addFilters = false)// tells spring only load the controller json serialization and validation
public class UserControllerTest {

    @Autowired 
    private MockMvc mockMvc;  // this is responsible for simulating HTTP requests instead of using postman

    @MockBean // replaces the real service with a mock. controller interacts with a fake service.
    private Userservice service;

    @MockBean  // Add this
    private CustomUserDetailsService customUserDetailsService;

    @MockBean  // Add this to mock JwtService
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper; // turns java objects into JSON

    @Test
    void shouldCreateUser() throws Exception {
        Users user = new Users();
        user.setEmail("mh@gmail.com");

        when(service.createUser(any())).thenReturn(user);

        User_request request = new User_request();
        request.setEmail(user.getEmail());

        // mockMvc.perform(post("/users"))
        //        .contentType(MediaType.APPLICATION_JSON);
        //        .content(objectMapper.writeValueAsString(request))
        //        .andExpect(status().isOk())
        //        .andExpect(jsonPath("$.email").value("mh@gmail.com"));

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.email").value("mh@gmail.com"));

        verify(service).createUser(any());
    }

    @Test
    void shouldUpdateuser() throws Exception{
       Long id = 1L;

       User_request request = new User_request();
       request.setFirstName("Mustafa");
       request.setLastName("Hassan");
       request.setEmail("mh@gmail.com");
       request.setAge(23);
       request.setPassword("1234");

    //    mockMvc.perform(put("/users/{id}"), id)
    //            .contentType(MediaType.APPLICATION_JSON)
    //            .content(objectMapper.writeValueAsString(request))
    //            .andExpect(status().isOk());

        mockMvc.perform(put("/users/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());


        verify(service).updateUser(eq(id), any(User_request.class));






    }

    @Test
    void shoudldeleteuser() throws Exception{
        User_request request = new User_request();
        request.setEmail("mh@gmail.com");

        when(service.deleteUser(any(User_request.class))).thenReturn(true);

        mockMvc.perform(delete("/users/deleteuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(service).deleteUser(any(User_request.class));

    }


}
