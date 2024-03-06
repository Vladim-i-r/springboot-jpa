package com.vladimir.curso.springboot.jpa.springbootjpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vladimir.curso.springboot.jpa.springbootjpa.entities.Person;
import com.vladimir.curso.springboot.jpa.springbootjpa.repos.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{ // Se implementa como aplicacion de consola

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args){
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		findOne();
	}

	public void findOne(){
		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(7L);
		// //if (!optionalPerson.isEmpty) {
		// if (optionalPerson.isPresent()) {
		// 	person = optionalPerson.get();
		// }
		// System.out.println(person);

		repository.findById(1L).ifPresent(person -> { System.out.println(person); });
		//repository.findById(1L).ifPresent(System.out::println);								//Hace lo mismo pero abreviado 
		repository.findOne(1L).ifPresent(person -> { System.out.println(person); });
		repository.findOneName("Pepe").ifPresent(person -> { System.out.println(person); });
		repository.findByName("Josefa").ifPresent(person -> { System.out.println(person); });
		repository.findOneNameLike("ri").ifPresent(person -> { System.out.println(person); });
	}									

	public void list(){
		//// List<Person> persons = (List<Person>) repository.findAll();
		////List<Person> persons = (List<Person>) repository.findByProgLanguage("Java");
		//// List<Person> persons = (List<Person>) repository.buscarByProgLanguage("Java", "Andres");
		List<Person> persons = (List<Person>) repository.findByProgLanguageAndName("Java", "Andres");

		persons.stream().forEach(person -> {  System.out.println(person);  });
		
		List<Object[]> personsData = (List<Object[]>) repository.obtenerPersonData();  //? Esto cuando solo se requieren ciertos atributos
		List<Object[]> personsData2= (List<Object[]>) repository.obtenerPersonData("Java");  //? Override con 1 atributo
		List<Object[]> personsData3 = (List<Object[]>) repository.obtenerPersonData("Java", "Andres");  // ?  Override con 2 atributos

		personsData.stream().forEach(person -> {  System.out.println(person[0] + " es experto en " + person[1]);  }); System.out.println("");
		personsData2.stream().forEach(person -> {  System.out.println(person[0] + " es experto en " + person[1]);  }); System.out.println("");
		personsData3.stream().forEach(person -> {  System.out.println(person[0] + " es experto en " + person[1]);  });
	}

}
 