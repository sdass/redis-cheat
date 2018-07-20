package com.subra;

import java.util.Map;

import com.subra.Person;

public interface PersonRepo 
{
		public void save(Person person);
		
		public Person find(String id);
		
		public Map<String, Person> findAll();
		
		public void	delete(String id);
}
