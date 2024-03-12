package com.vladimir.curso.springboot.jpa.springbootjpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity // Peristencia
@Table(name="persons")
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para MySQL se agrega esto, significa auto-incremental
    private Long id;

    private String name;
    private String lastname;
    
    @Column(name="programming_language")
    private String progLanguage;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public Person() {                               //? Este constructor lo usa JPA/Hibernate para poblar la tabla
    }
    

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }


    public Person(Long id, String name, String lastname, String progLanguage) {  //? Este constructor nos funciona a nosotros pero siempre que se crea este, se debera crear el vacio 
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.progLanguage = progLanguage;
    }

    @PrePersist
    public void prePersist(){
        System.out.println("Evento del ciclo de vida del entity, pre-persist");
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        System.out.println("Evento del ciclo de vida del objeto entity pre-update");
        this.updatedAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getProgLanguage() {
        return progLanguage;
    }
    public void setProgLanguage(String progLanguage) {
        this.progLanguage = progLanguage;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", lastname=" + lastname + ", progLanguage=" + progLanguage + ", created at=" + createdAt + ", updated at= " + updatedAt + "]";
    }

    
}
