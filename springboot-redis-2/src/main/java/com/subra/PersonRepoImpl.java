package com.subra;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


public class PersonRepoImpl implements  PersonRepo
{
		
		private RedisTemplate<String, Person> redisTemplate;
		
		private HashOperations<String, String, Person> opsForHash;
		
		private static String PERSON_KEY = "Person";

		public RedisTemplate<String, Person> getRedisTemplate()
		{
				return redisTemplate;
		}

		public void setRedisTemplate(RedisTemplate<String, Person> redisTemplate)
		{
				this.redisTemplate = redisTemplate;
				this.opsForHash = redisTemplate.opsForHash();
		}

		@Override
		public void save(Person person)
		{
				//this.redisTemplate.opsForHash().put(PERSON_KEY, person.getId(), person);
			HashOperations<String, String, Person> hashOperation = this.redisTemplate.opsForHash(); //put(PERSON_KEY, person.getId(), person);
			System.out.println("before---1");
			//hashOperation.put(PERSON_KEY, person.getId(), person);
			opsForHash.put(PERSON_KEY, person.getId(), person);
			System.out.println("after---");
		}

		@Override
		public Person find(String id)
		{
				//return (Person)this.redisTemplate.opsForHash().get(PERSON_KEY, id);
			return (Person) opsForHash.get(PERSON_KEY, id);
		}

		@Override
		public Map<String,Person> findAll()
		{
				//return this.redisTemplate.opsForHash().entries(PERSON_KEY);
				return opsForHash.entries(PERSON_KEY);
			//return opsForHash.entries(PERSON_KEY);
		}

		@Override
		public void delete(String id)
		{
				this.redisTemplate.opsForHash().delete(PERSON_KEY,id);
				
		}

}
