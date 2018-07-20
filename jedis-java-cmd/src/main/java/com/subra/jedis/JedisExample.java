package com.subra.jedis;

// https://redislabs.com/lp/redis-java/

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class JedisExample {
	
	public static void springWay(){
		System.out.println("in springWay()..");
		JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
		jedisConnFactory.setHostName("localhost");
		jedisConnFactory.setPort(6379);
		jedisConnFactory.setTimeout(10);
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		
		jedisConnFactory.afterPropertiesSet();  //critical line 1(2)
		
		redisTemplate.setConnectionFactory(jedisConnFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		//redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		
		redisTemplate.afterPropertiesSet(); //critical line 2(2)
		
		
		System.out.println("redisTemplate=" + redisTemplate);
		redisTemplate.boundSetOps("myKey").add("3", "8", "12");
		System.out.println("reached here");
		Set<String> keys_set = redisTemplate.keys("*");
		//Set<byte[]> keys_set = redisTemplate.getConnectionFactory().getConnection().keys("*".getBytes());
		System.out.println("keys=" + keys_set);
		System.out.println("keys=" + keys_set.size());
		
		

		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		Map<Object, Object> entries = opsForHash.entries("persons");
		System.out.println("entries=" + entries);
	
		
	}
	
	public static void main(String[] args) throws Exception{			
		springWay();
		System.out.println("Connected to Redis..");
		//System.exit(0);
		
		//JedisPool jedisPoolCl = new JedisPool (new GenericObjectPoolConfig(), "localhost", 6379, Protocol.DEFAULT_TIMEOUT);
		JedisPool jedisPoolCl = new JedisPool (new JedisPoolConfig(), "localhost", 6379, Protocol.DEFAULT_TIMEOUT);
		//Jedis jedisClient = new Jedis("localhost", 6379);
		
		
		
		Jedis jedis = null;
		try{
			jedis = jedisPoolCl.getResource();
			ObjectMapper mapper = new ObjectMapper();
			
			String val = jedis.get("ab");
			System.out.println("val=" + val);
			//Map<String, String> keyVal = jedis.hgetAll("persons");
			//System.out.println("keyVal=" + keyVal);
			
			Map<byte[], byte[]>  keyval = jedis.hgetAll("persons".getBytes());
			System.out.println("map-size=" + keyval.size());
			for( Entry<byte[], byte[]> anEntry :  keyval.entrySet()){
				System.out.println( new String(anEntry.getKey(), "UTF-8") + " -->.");
				//String jsonResult = mapper.readValue(anEntry.getKey(), String.class);
				//System.out.println( "..jsonResult=" + jsonResult) ;
			}
			

			
			Map<String, String>  keyvalstr = jedis.hgetAll("persons");
			System.out.println("map-size=" + keyvalstr.size());
			for( Entry<String, String> anEntry :  keyvalstr.entrySet()){
				//System.out.println( new String(anEntry.getKey()) + " -->");
				//System.out.println((String)anEntry.getKey()) ;
				System.out.println( new String(anEntry.getValue()) + " -->");
			}
			
			
			
		}finally{
			jedisPoolCl.destroy();
		}
		
	}

}
