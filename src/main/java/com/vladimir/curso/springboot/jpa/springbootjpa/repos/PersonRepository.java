package com.vladimir.curso.springboot.jpa.springbootjpa.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vladimir.curso.springboot.jpa.springbootjpa.entities.Person;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> { // El <Person, Long> siginifica que esta asociada a la clase Person y su identificador es de tipo Long (ID)
                                                                         // No es necesario agregar nada a la clase, ya que CRUD es componente de Spring

    List<Person> findByProgLanguage(String progLanguage); // * Esto es del CRUD repo

    @Query("select p from Person p where p.progLanguage=?1 and p.name=?2") // No es consultya nativa de sql
    List<Person> buscarByProgLanguage(String progLanguage, String name);

    List<Person> findByProgLanguageAndName(String progLanguage, String name); // * Esto es del CRUD repo, por eso no es necesario el query

    @Query("select p.name, p.progLanguage from Person p")                   // ? Esto cuando solo se requieren ciertos atributos
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.progLanguage from Person p where p.progLanguage=?1") // ? Esto cuando solo se requiere 1  atributo y filtrado
    List<Object[]> obtenerPersonData(String progLanguage);

    @Query("select p.name, p.progLanguage from Person p where p.progLanguage=?1 and p.name=?2") // ? Esto cuando solo se requieren 2 atributos y filtrado
    List<Object[]> obtenerPersonData(String progLanguage, String name);

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    Optional<Person> findByName(String name);                                   // * CRUD
    
    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneNameLike(String name);
    
    Optional<Person> findByNameContaining(String name);                         // * CRUD

    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);                                             // Personalizado

    @Query("select p.id from Person p where p.id=?1")                       // Personalizado
    Long getIdById(Long id); 

    @Query("select concat(p.name, ' ', p.lastname) as Fullname from Person p where p.id=?1")    // Personalizado
    String getFullnameById(Long id); 

    @Query("select p.id, p.name, p.lastname, p.progLanguage from Person p where p.id=?1")                   // Personalizado          
    Optional<Object> obtenerFullPersonDataById(Long id);
    //Object obtenerFullPersonDataById(Long id);
    
    @Query("select p.id, p.name, p.lastname, p.progLanguage from Person p")                   // ? Esto cuando solo se requieren ciertos atributos
    List<Object[]> obtenerPersonDataList();

    @Query("select p, p.progLanguage from Person p")                   // ? Esto cuando solo se requieren ciertos atributos
    List<Object[]> findAllMixPerson();

    @Query("select new Person(p.name, p.lastname) from Person p")                     //? El constructor de 2 atributos debe existir 
    List<Person> findAllCustomObjPerson();
    
}
