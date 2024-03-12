package com.vladimir.curso.springboot.jpa.springbootjpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
//import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.vladimir.curso.springboot.jpa.springbootjpa.dto.PersonDto;
import com.vladimir.curso.springboot.jpa.springbootjpa.entities.Person;
import com.vladimir.curso.springboot.jpa.springbootjpa.repos.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{ // Se implementa como aplicacion de consola

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args){
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//findOne();
		//create();
		update();
		//delete();
		//delete2();
		// personalizedQueries();
		//personalizedQueries2();
		//personalizedQueriesDistinct();
		//personalizedQueriesBetweenOrder();
		//queriesFunctionAgregation();
		//subQueries();
	}

	@Transactional(readOnly = true)											//? Es una transaccion pero unicamente de consulta
	public void findOne(){
		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(7L);
		// //if (!optionalPerson.isEmpty) {
		// if (optionalPerson.isPresent()) {
		// 	person = optionalPerson.get();
		// }
		// System.out.println(person);

		personRepository.findById(1L).ifPresent(person -> { System.out.println(person); });
		//repository.findById(1L).ifPresent(System.out::println);								//Hace lo mismo pero abreviado 
		personRepository.findOne(1L).ifPresent(person -> { System.out.println(person); });
		personRepository.findOneName("Pepe").ifPresent(person -> { System.out.println(person); });
		personRepository.findByName("Josefa").ifPresent(person -> { System.out.println(person); });
		personRepository.findOneNameLike("ri").ifPresent(person -> { System.out.println(person); });
	}									

	public void list(){
		//// List<Person> persons = (List<Person>) repository.findAll();
		////List<Person> persons = (List<Person>) repository.findByProgLanguage("Java");
		//// List<Person> persons = (List<Person>) repository.buscarByProgLanguage("Java", "Andres");
		List<Person> persons = (List<Person>) personRepository.findByProgLanguageAndName("Java", "Andres");

		persons.stream().forEach(person -> {  System.out.println(person);  });
		
		List<Object[]> personsData = (List<Object[]>) personRepository.obtenerPersonData();  //? Esto cuando solo se requieren ciertos atributos
		List<Object[]> personsData2= (List<Object[]>) personRepository.obtenerPersonData("Java");  //? Override con 1 atributo
		List<Object[]> personsData3 = (List<Object[]>) personRepository.obtenerPersonData("Java", "Andres");  // ?  Override con 2 atributos

		personsData.stream().forEach(person -> {  System.out.println(person[0] + " es experto en " + person[1]);  }); System.out.println("");
		personsData2.stream().forEach(person -> {  System.out.println(person[0] + " es experto en " + person[1]);  }); System.out.println("");
		personsData3.stream().forEach(person -> {  System.out.println(person[0] + " es experto en " + person[1]);  });
	}

	@SuppressWarnings("null")
	@Transactional										//? Si llega a haber error, hace un rollback, pero inicia transaccion y commit
	public void create(){								//! Esto deberia estar en un service

		Scanner scanner = new Scanner(System.in);		//! Esto se puedee hacer asi porque es un poryecto mediante consola, commandLineRunner, si no deberia ser con API  
		System.out.println("Ingrese el nombre: ");
		String name = scanner.next();
		System.out.println("Ingrese el apellido: ");
		String lastname = scanner.next();
		System.out.println("Ingrese el lenguaje de programacion: ");
		String progLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null,name,lastname,progLanguage);
		////Person person = new Person(null,"Lalo", "Thor", "Python");

		Person personNew = personRepository.save(person);
		personRepository.findById(personNew.getId()).ifPresent(System.out::println);

		////System.out.println(personNew);
	}

	@Transactional
	public void update(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a editar: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = personRepository.findById(id);
		////optionalPerson.ifPresent(person -> {
		if (optionalPerson.isPresent()) {
			Person personDB = optionalPerson.orElseThrow();
			System.out.println(personDB);
			System.out.println("Ingrese el lenguaje de programacion: ");
			String newProg = scanner.next();
			personDB.setProgLanguage(newProg);
			Person personUpdt = personRepository.save(personDB);
			System.out.println(personUpdt);
		} else {
			System.out.println("***El id indicado no existe!***");
		}
		////});
		scanner.close();
	}

	@Transactional
	public void delete(){
		personRepository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar: ");
		Long id = scanner.nextLong();
		personRepository.deleteById(id);
		
		personRepository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@SuppressWarnings("null")
	@Transactional
	public void delete2(){
		personRepository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = personRepository.findById(id);			// Lo de abajo, es manera moderna, elegante en lugar de un if conditional
		optionalPerson.ifPresentOrElse(person -> personRepository.delete(person) , ()-> System.out.println("***Lo sentimos, no existe una persona CON ese id!***"));
								     //personRepository::delete(person)	
		
		personRepository.findAll().forEach(System.out::println);
		scanner.close();
	}


	@Transactional(readOnly = true)
	public void personalizedQueries(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("========== CONSULTA DE NOMBRE POR ID =================");
		System.out.println("Ingrese el ID: ");
		Long id = scanner.nextLong();

		System.out.println("MOSTRANDO SOLO EL NOMBRE");
		String name = personRepository.getNameById(id);
		System.out.println(name);
		
		System.out.println("MOSTRANDO EL ID");
		Long idDb = personRepository.getIdById(id);
		System.out.println(idDb);
		
		System.out.println("MOSTRANDO SOLO EL NOMBRE COMPLETO");
		String fullname = personRepository.getFullnameById(id);
		System.out.println(fullname);

		System.out.println("=============CONSULTA POR CAMPOS PERSONALIZADOS POR ID===========");
		//// Object[] personReg = (Object[]) personRepository.obtenerFullPersonDataById(id);
		//// System.out.println("id=" + personReg[0] + ", nombre=" + personReg[1] + ", apellido=" + personReg[2] + ", lenguaje=" + personReg[3]);
		Optional<Object> optObj = personRepository.obtenerFullPersonDataById(id);
		if (optObj.isPresent()) {
			Object[] personReg = (Object[]) optObj.orElseThrow();
			System.out.println("id=" + personReg[0] + ", nombre=" + personReg[1] + ", apellido=" + personReg[2] + ", lenguaje=" + personReg[3]);
		} 

		System.out.println("=============CONSULTA POR CAMPOS PERSONALIZADOS EN LISTA===========");
		List<Object[]> regs = personRepository.obtenerPersonDataList();
		regs.forEach(reg -> System.out.println("id=" + reg[0] + ", nombre=" + reg[1] + ", apellido=" + reg[2] + ", lenguaje=" + reg[3]));


		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2(){
		System.out.println("=============CONSULTA POR OBJETO PERSONA Y OTRO ELEMENTO===========");
		List<Object[]> personsRegs =  personRepository.findAllMixPerson();
		personsRegs.forEach(regs -> {
			System.out.println("progLanguage=" + regs[1] + ", person=" + regs[0]);
		});

		System.out.println("============CONSULTA QUE POBLA Y DEVUELVE PBJETO ENTITY DE UNA INSTANCIA PERSONALIZADA===========");
		List<Person> persons = personRepository.findAllCustomObjPerson();
		persons.forEach(System.out::println);

		System.out.println("=============CONSULTA QUE POBLA Y DEVULEVE OBJETO DTO DE UNA CLASE PERSONALIZADA===========");
		List<PersonDto> personsDto = personRepository.findAllPersonDto();
		personsDto.forEach(System.out::println);
		
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct(){
		System.out.println("========CONSULTAS CON NOMBRES DE PERSONAS========");
		List<String> names = personRepository.findAllNames();
		names.forEach(System.out::println);
		
		System.out.println("========CONSULTAS CON NOMBRES UNICOS DE PERSONAS========");
		List<String> namesDN = personRepository.findAllNamesDistinctName();
		namesDN.forEach(System.out::println);

		System.out.println("========CONSULTAS CON LENGUAJES UNICOS ========");
		List<String> namesDP = personRepository.findAllNamesDistinctProg();
		namesDP.forEach(System.out::println);

		System.out.println("========CONSULTAS CON TOTAL LENGUAJES UNICOS ========");
		Long namesCount = personRepository.findAllNamesCountDistinctProg();
		System.out.println("El total de lenguajes de programacion son: "+namesCount);
	}

	public void personalizedQueriesBetweenOrder(){
		System.out.println("========CONSULTAS POR RANGOS========");
		System.out.println("========POR ID CUSTOM========");
		List<Person> persons = personRepository.findAllBetweenId();
		persons.forEach(System.out::println);

		System.out.println("========POR NOMBRE CUSTOM ========");
		List<Person> personsN = personRepository.findAllBetweenName("J", "Q");
		personsN.forEach(System.out::println);

		System.out.println("========POR ID   CRUD========");
		List<Person> personsB = personRepository.findByIdBetween(2L,5L);
		personsB.forEach(System.out::println);

		System.out.println("========POR NOMBRE    CRUD========");
		List<Person> personsc = personRepository.findByNameBetween("J", "Q");
		personsc.forEach(System.out::println);

		System.out.println("========POR ID   CRUD========");
		List<Person> personsD = personRepository.findByIdBetweenOrderByNameAsc(2L,5L);
		personsD.forEach(System.out::println);

		System.out.println("========POR NOMBRE    CRUD========");
		List<Person> personsF = personRepository.findByNameBetweenOrderByNameDescLastnameDesc("J", "Q");
		personsF.forEach(System.out::println);

		persons = personRepository.getAllOrdered();
		persons.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void queriesFunctionAgregation(){
		System.out.println("==========CONSULTA CON EL TOTAL DE REGISTROS DE LA TABLA PERSONA===========");
		Long count = personRepository.getTotalPerson();
		System.out.println(count);

		System.out.println("=========CONSULTA CON EL VALOR MINIMO DEL ID=========");
		Long min = personRepository.getMinId();
		System.out.println(min);
		
		System.out.println("=========CONSULTA CON EL VALOR MINIMO DEL ID=========");
		Long max = personRepository.getMaxId();
		System.out.println(max);

		System.out.println("============CONSULTA CON EL NOMBRE Y SU LARGO===========");
		List<Object[]> regs = personRepository.getPersonNameLength();
		regs.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name=" + name + ", lenght=" + length);
			
		});

		System.out.println("==========CONSULTA CON EL NOMBRE MAS CORTO =========");
		Integer minLengthName = personRepository.getMinLengthName();
		System.out.println(minLengthName);

		System.out.println("==========CONSULTA CON EL NOMBRE MAS LARGO =========");
		Integer maxLengthName = personRepository.getMaxLengthName();
		System.out.println(maxLengthName);

		System.out.println("============CONSULTAS RESUMEN DE FUNCIONES DE AGREGACION MIN, MAX, SUM, AVG, COUNT===========");
		Object[] resumeReg = (Object[]) personRepository.getResumeAggregationFunction();
		System.out.println("min=" + resumeReg[0] + ", max=" + resumeReg[1] + ", sum=" + resumeReg[2] + ", avg= " + resumeReg[3] + ", count=" + resumeReg[4]);
	}

	@Transactional(readOnly = true)
	public void subQueries(){
		System.out.println("=======CONSULTA POR EL NOMBRE MAS CORTO Y SU LARGO======");
		List<Object[]> registers = personRepository.getShortestName();
		registers.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name=" + name + ", lenght=" + length);
		});


		System.out.println("=======CONSULTA POR EL NOMBRE MAS LARGO Y SU LARGO======");
		List<Object[]> registerss = personRepository.getLongestName();
		registerss.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name=" + name + ", lenght=" + length);
		});

		System.out.println("=========CONSULTA WHERE IN=========");
		List<Person> persons = personRepository.getPersonByIds();
		persons.forEach(System.out::println);

		System.out.println("=========CONSULTA WHERE IN 2=========");
		List<Person> persons2 = personRepository.getPersonByIds2(Arrays.asList(1L,2L,5L,7L));
		persons2.forEach(System.out::println);

	}


}
