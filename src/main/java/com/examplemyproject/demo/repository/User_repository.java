package com.examplemyproject.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examplemyproject.demo.entity.Role;
import com.examplemyproject.demo.entity.Users;

@Repository
public interface User_repository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByFirstNameAndLastName(String firstName, String lastName);

    List<Users> findByRole(Role role);

    List<Users> findByRole_RoleName(String roleName); // Nested property for queries by role name
}
