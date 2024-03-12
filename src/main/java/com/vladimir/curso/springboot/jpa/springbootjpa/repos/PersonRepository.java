package com.vladimir.curso.springboot.jpa.springbootjpa.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vladimir.curso.springboot.jpa.springbootjpa.dto.PersonDto;
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
    
    @Query("select p.id, p.name, p.lastname, p.progLanguage from Person p")                   // ? JPQL
    List<Object[]> obtenerPersonDataList();

    @Query("select p, p.progLanguage from Person p")                   // ? JPQL 
    List<Object[]> findAllMixPerson();

    @Query("select new Person(p.name, p.lastname) from Person p")                     //? Instanciando un contructor con 2 atributos
    List<Person> findAllCustomObjPerson();
    
    @Query("select new com.vladimir.curso.springboot.jpa.springbootjpa.dto.PersonDto(p.name, p.lastname) from Person p")          //? Cuando es DTO se le debe poner la ruta ya que no es Entity 
    List<PersonDto> findAllPersonDto();

    @Query("select p.name from Person p")
    List<String> findAllNames();

    @Query("select distinct(p.name) from Person p")
    List<String> findAllNamesDistinctName();

    @Query("select distinct(p.progLanguage) from Person p")
    List<String> findAllNamesDistinctProg();

    @Query("select count(distinct(p.progLanguage)) from Person p")
    Long findAllNamesCountDistinctProg();

    @Query("select p from Person p where p.id between 2 and 5 order by p.name desc")
    List<Person> findAllBetweenId();

    ////@Query("select p from Person p where p.name between 'J' and 'Q'")  //Aqui la J no se incluye 
    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name asc, p.lastname desc")  //Aqui la J no se incluye 
    List<Person> findAllBetweenName(String c1, String c2);

    List<Person> findByIdBetween(Long id1, Long id2);               // CRUD

    List<Person> findByNameBetween(String name1, String name2);     // CRUD
    
    List<Person> findByIdBetweenOrderByNameAsc(Long id1, Long id2);               // CRUD

    List<Person> findByNameBetweenOrderByNameDescLastnameDesc(String name1, String name2);     // CRUD

    @Query("select p from Person p order by p.name")
    List<Person> getAllOrdered();

    List<Person> findAllByOrderByNameAsc();                 // CRUD

    @Query("select count(p.name) from Person p")
    Long getTotalPerson();

    @Query("select min(p.id) from Person p")
    Long getMinId();

    @Query("select max(p.id) from Person p")
    Long getMaxId();

    @Query("select p.name, length(p.name) from Person p")
    public List<Object[]> getPersonNameLength();

    @Query("select min(length(p.name)) from Person p")
    public Integer getMinLengthName();

    @Query("select max(length(p.name)) from Person p")
    public Integer getMaxLengthName();

    @Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
    public Object getResumeAggregationFunction();

    @Query("select p.name, length(p.name) from Person p where length(p.name)=(select min(length(p.name)) from Person p)")  // SUBQUERY
    public List<Object[]> getShortestName();

    @Query("select p.name, length(p.name) from Person p where length(p.name)=(select max(length(p.name)) from Person p)")  // SUBQUERY
    public List<Object[]> getLongestName();

    @Query("select p from Person p where p.id=(select max(p.id) from Person p)")
    public Optional<Person> getLastRegistration();

    @Query("select p from Person p where p.id in (1,2,5)")
    public List<Person> getPersonByIds();

    @Query("select p from Person p where p.id in ?1")  //not in tambien se puede
    public List<Person> getPersonByIds2(List<Long> ids);
}
