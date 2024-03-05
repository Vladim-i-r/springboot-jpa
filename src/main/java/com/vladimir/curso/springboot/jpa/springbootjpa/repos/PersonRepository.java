package com.vladimir.curso.springboot.jpa.springbootjpa.repos;

import org.springframework.data.repository.CrudRepository;

import com.vladimir.curso.springboot.jpa.springbootjpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{  // El <Person, Long> siginifica que esta asociada a la clase Person y su identificador es de tipo Long (ID)
                                                                         // No es necesario agregar nada a la clase, ya que CRUD es componente de Spring
} 
