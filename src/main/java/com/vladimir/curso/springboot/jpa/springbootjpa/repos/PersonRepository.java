package com.vladimir.curso.springboot.jpa.springbootjpa.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vladimir.curso.springboot.jpa.springbootjpa.entities.Person;
import java.util.List;


public interface PersonRepository extends CrudRepository<Person, Long>{  // El <Person, Long> siginifica que esta asociada a la clase Person y su identificador es de tipo Long (ID)
                                                                         // No es necesario agregar nada a la clase, ya que CRUD es componente de Spring
    List<Person> findByProgLanguage(String progLanguage);                  //Esto es del CRUD repo
    
    @Query("select p from Person p where p.progLanguage=?1 and p.name=?2")   // No es consultya nativa de sql
    List<Person> buscarByProgLanguage(String progLanguage, String name);

    List<Person> findByProgLanguageAndName(String progLanguage, String name);  //Esto es del CRUD repo, por eso no es necesario el query 

    @Query("select p.name, p.progLanguage from Person p")  //? Esto cuando solo se requieren ciertos atributos
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.progLanguage from Person p where p.progLanguage=?1")  //? Esto cuando solo se requieren ciertos atributos y filtrado
    List<Object[]> obtenerPersonData(String progLanguage);

    @Query("select p.name, p.progLanguage from Person p where p.progLanguage=?1 and p.name=?2")  //? Esto cuando solo se requieren ciertos atributos y filtrado
    List<Object[]> obtenerPersonData(String progLanguage, String name);

} 
  