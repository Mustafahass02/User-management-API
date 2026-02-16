package com.examplemyproject.demo.repository;
import com.examplemyproject.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Auditlog_repository extends JpaRepository<Auditlog, Long>{
    
}
