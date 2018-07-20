package com.subra;

import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.subra.Person.Gender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedis2ApplicationTests {
	
	@Autowired
	PersonRepo personRepo;
	
	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void loadData(){
		System.out.println("This is begins1.");
		Person person = new Person();
		person.setId("1");
		person.setAge(55);
		person.setGender(Gender.Female);
		person.setName("Oracle");
		
		personRepo.save(person);
		
		Person person2 = new Person();
		person2.setId("2");
		person2.setAge(60);
		person2.setGender(Gender.Male);
		person2.setName("TheArchitect");
		
		personRepo.save(person2);
		
		Person person3 = new Person();
		person3.setId("3");
		person3.setAge(25);
		person3.setGender(Gender.Male);
		person3.setName("TheOne"); 
		
		personRepo.save(person3);
		System.out.println("Finding the One : "+personRepo.find("3"));
				
	}
	
	@Test
	public void inspectData(){
		
		
		Map<String,Person> personMatrixMap = personRepo.findAll();
		
		
		System.out.println("Currently in the Redis Matrix");
	
		System.out.println(personMatrixMap);
		
		/*
		System.out.println("Deleting The Architect ");
		
		personRepo.delete("2");
		
		personMatrixMap = personRepo.findAll();
		
		System.out.println("Remnants .. : ");
		
		System.out.println(personMatrixMap);
		*/
	}

}
