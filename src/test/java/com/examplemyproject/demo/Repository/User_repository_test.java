package com.examplemyproject.demo.Repository;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.examplemyproject.demo.entity.Role;
import com.examplemyproject.demo.entity.Users;
import com.examplemyproject.demo.repository.Role_repository;
import com.examplemyproject.demo.repository.User_repository;

@DataJpaTest
@Testcontainers
public class User_repository_test {

    
    @Autowired
    private User_repository user_repo;
    @Autowired
    private Role_repository role_repo;

    @Container 
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
               .withDatabaseName("mydb")
               .withUsername("mustafa")
               .withPassword("secret");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    

    // @BeforeEach
    // // initalises all mock fields before running any test methods
    // void setUp(){
    //     MockitoAnnotations.openMocks(this);
    // }


    @Test
    public void testSaveUser (){

        //Arrange set up the object/preconditions
        //Arrange set up the object/preconditions
           Role role = new Role();
           role.setRoleName("USER");

            Users user = new Users();
            user.setFirstName("Mustafa");
            user.setLastName("Hassan");
            user.setEmail("mh3489877@gmail.com");
            user.setPassword("Arsenal12");
            user.setAge(22);
            user.setRole(role);


        // Act perform the action your testing
        user_repo.save(user);


        //Assert check the result to see if the action passed
        assertThat(user).isNotNull();
        assertThat(user.getId()).isGreaterThan(0);
    }

    // Optional<Users> findByEmail(String email);

    @Test
    public void finduserbyemail(){
        // Arrange & create
        Role role = new Role();
        role.setRoleName("ADMIN");

        Users user = new Users();
        user.setFirstName("JOHN");
        user.setLastName("CENA");
        user.setEmail("JohnCena@gmail.com");
        user.setPassword("Arsenal1234");
        user.setAge(56);
        user.setRole(role);


        // act perform the action youre testing
        user_repo.save(user);

        // Assert check the result to see if it passed
        Optional <Users> found_user = user_repo.findByEmail(user.getEmail());

        assertThat(found_user).isPresent();
        assertThat(found_user.get().getEmail()).isEqualTo("JohnCena@gmail.com");

    }

    //  Optional<Users> findByFirstNameAndLastName(String firstName, String lastName);
   @Test
    public void findfirstnamelastname(){

        // Arrange & create
        Role role = new Role();
        role.setRoleName("USER");
        role_repo.save(role);

        Users user = new Users();
        user.setFirstName("ALINA");
        user.setLastName("YONNA");
        user.setEmail("Alinayonna@gmail.com");
        user.setPassword("6789076");
        user.setAge(26);
        user.setRole(role);


        // act perform the action youre testing
        user_repo.save(user);


        // Assert - check the results to see if your actions passed
        Optional<Users> found_user = user_repo.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        assertThat(found_user).isPresent();
        assertThat(found_user.get().getFirstName()).isEqualTo("ALINA");
        assertThat(found_user.get().getLastName()).isEqualTo("YONNA");
          
    }


    // List<Users> findByRole(Role role);
    @Test
    public void finduserbyrole(){


        // Arrange & create
        Role role = new Role();
        role.setRoleName("USER");

        role_repo.save(role);

        Users user = new Users();
        user.setFirstName("Isabella");
        user.setLastName("hardwich");
        user.setEmail("issabella@gmail.com");
        user.setPassword("987654");
        user.setAge(22);
        user.setRole(role);


        // act - perform action

        user_repo.save(user);

        // assert - check the results to see if your action passed 
        List<Users> acrux = user_repo.findByRole(role);
         assertThat(acrux).isNotEmpty();
         assertThat(acrux.get(0).getRole().getRoleName()).isEqualTo("USER");

    }









    
}
