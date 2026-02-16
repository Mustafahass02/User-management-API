package com.examplemyproject.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "audit_logs")
public class Auditlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ✅ lowercase id

    private String action;
    private LocalDateTime createdAt;

    public Auditlog(){

    }
    
    public Auditlog(String action, LocalDateTime createdAt){
        this.action = action;
        this.createdAt = createdAt;
    }

    public void setAction(String action){
        this.action = action;

    }

    public String getAction (){
        return action;
    }

    
}
