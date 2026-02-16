package com.examplemyproject.demo.Service;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.examplemyproject.demo.DTO.User_request;
import com.examplemyproject.demo.entity.Role;
import com.examplemyproject.demo.entity.Users;
import com.examplemyproject.demo.repository.Role_repository;
import com.examplemyproject.demo.repository.User_repository;

@ExtendWith(MockitoExtension.class)
public class Userservicetest {

    @InjectMocks
    Userservice service;

    @Mock
    User_repository user_repo;

    @Mock
    Role_repository role_repo;

    private User_request request;

    private Role role;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    // before the tests methods are ran this method set up all mock annotations
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        request = new User_request();
        request.setFirstName("Mustafa");
        request.setLastName("Hassan");
        request.setEmail("mh@gmail.com");
        request.setPassword("1234");
        request.setAge(23);
        role = new Role();
        role.setRoleName("USER");
    }


    @Test
    void shouldcreateUser(){
        

        // Arrange & create
        when(user_repo.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(role_repo.findByRoleNameIgnoreCase(role.getRoleName())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("password encoded");
  


        // act - perform action

        Users user = service.createUser(request);


        // assert
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("mh@gmail.com");

        // verify 
        verify(user_repo).save(any(Users.class));
        verify(passwordEncoder).encode(request.getPassword());

        
    }

    @Test
    void shouldfindbyname(){
        // Arrange
        Users user = new Users();
        user.setFirstName("Mustafa");
        user.setLastName("Hassan");

        when(user_repo.findByFirstNameAndLastName(request.getFirstName(), request.getLastName())).thenReturn(Optional.of(user));


        // act 

        Users found = service.findByName(request);


        // assert

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Mustafa");

        // verify 

        verify(user_repo).findByFirstNameAndLastName(request.getFirstName(), request.getLastName());

    }

    @Test
    void listallusers(){

        // aarange 
        Users a = new Users();
        a.setFirstName("abella");
        Users b = new Users();
        b.setFirstName("cena");

        List <Users> users = List.of(a,b);


        when(user_repo.findAll()).thenReturn(users);

        List<Users> results = service.listUsers();

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getFirstName()).isEqualTo("abella");

        verify(user_repo).findAll();

    }

    @Test
    void shoudldeleteuser(){

        Users user = new Users();

        user.setEmail("mh348@gmail.com");

        when(user_repo.findByEmail(request.getEmail()))
        .thenReturn(Optional.of(user));


        boolean result = service.deleteUser(request);

         assertThat(result).isTrue();

         verify(user_repo).delete(user);
         verify(user_repo).findByEmail(user.getEmail());

    }

    @Test
    void shouldUpdateUser(){

        Long id = 1L;

        Users existing = new Users();
        existing.setId(id);
        existing.setFirstName("Old");
        existing.setLastName("Name");

       when(user_repo.findById(id)).thenReturn(Optional.of(existing));


        service.updateUser(id, request);

        assertThat(existing.getFirstName()).isEqualTo("Mustafa");
        assertThat(existing.getLastName()).isEqualTo("Hassan");

        verify(user_repo).save(existing);
        verify(user_repo).findById(id);


    }
    
}
