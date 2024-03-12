package com.vladimir.curso.springboot.jpa.springbootjpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Embedded
    private Audit audit = new Audit();                            // Se agrega embedded para reutilizar el codigo de Audit, de una clase Embeddable - incorporable, integrable

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
        return "[id=" + id + ", name=" + name + ", lastname=" + lastname + ", progLanguage=" + progLanguage + ", created at=" + audit.getCreatedAt() + ", updated at= " + audit.getUpdatedAt() + "]";
    }

    
}
