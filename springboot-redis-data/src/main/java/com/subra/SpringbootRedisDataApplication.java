package com.subra;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

@SpringBootApplication
public class SpringbootRedisDataApplication {

	
	public static void main(String[] args) {
		ApplicationContext ctx =  SpringApplication.run(SpringbootRedisDataApplication.class, args);
		System.out.println(ctx != null);
		RedisTemplate<String, Long> redTemplate = ctx.getBean(RedisTemplate.class);
		ValueOperations<String, Long> valOper = redTemplate.opsForValue();
				
		//valOper.set("name", "David F.");
		//Object s = valOper.get("name"); //("\xac\xed\x00\x05t\x00\x04name");
		//System.out.println(s);
		

		//BoundValueOperations<String, Object> bvalOps = redTemplate.boundValueOps("name");
		//System.out.println("<--" + bvalOps.get());
		/*
		//bvalOps.set("Different Name");
		bvalOps.append("Second.");
		valOper.append("name", "Third.");
		
		*/

		/*
		valOper.set("name", "FC Dill");
		Object obx = valOper.get("name");
		
		System.out.println("<-- " + obx); 
		*/
		/*
		valOper.set(17L, "Minor");
		*/
		
/*
		valOper.set("candy:stock", 556L);
		valOper.set("candy:sale", 220L);*/
		
		Long stock = valOper.get("candy:stock");
		Long sale = valOper.get("candy:sale");
		System.out.println(stock + " --- ----  " + sale );
		valOper.increment("candy:stock", 100L);
		
		BoundValueOperations boundValueOperations = redTemplate.boundValueOps("candy:stock");
		System.out.println("<< " + boundValueOperations.increment(35));
		
		
		
		
		//valOper.set
		
		
		
		System.exit(0);
		
		
				
		
	}
	
	@Bean public RedisConnectionFactory redisConnectionFactory1(){
		JedisConnectionFactory cf = new JedisConnectionFactory();
		cf.setHostName("localhost");
		//RedisStandaloneConfiguration redisconfg =  new RedisStandaloneConfiguration("localhost", 6379);
		//new JedisConnectionFactory(
		//new JedisConnectionFactory(new RedisStandaloneConfiguration("localhost"));
		cf.setPort(6379);
		cf.afterPropertiesSet();
		return cf;
		
	}
	
	@Primary
	@Bean(name="RedisTemplate") public RedisTemplate<String, Long> redisTemplate2(){
		RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory1());
		/*
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		*/
		
		
		//redisTemplate.setKeySerializer(CustomStringSerializer.INSTANCE);
		//redisTemplate.setValueSerializer(CustomStringSerializer.INSTANCE);
		/*
		redisTemplate.setKeySerializer(LongSerializer.INSTANCE_LONG);
		redisTemplate.setValueSerializer(CustomStringSerializer.INSTANCE);
		*/
		redisTemplate.setKeySerializer(CustomStringSerializer.INSTANCE);
		redisTemplate.setValueSerializer(LongSerializer.INSTANCE_LONG);
		
		/*redisTemplate.setKeySerializer(new CustomStringSerializer2());
		redisTemplate.setValueSerializer(new CustomStringSerializer2()); // new JdkSerializationRedisSerializer() ); //(new CustomStringSerializer2());
		*/
		return redisTemplate;
	}
	
	public static enum LongSerializer implements RedisSerializer<Long>{
		INSTANCE_LONG;

		@Override
		public byte[] serialize(Long aLong) throws SerializationException {
			if(aLong != null){
				return aLong.toString().getBytes();
			}else
				return new byte[0];
		}

		@Override
		public Long deserialize(byte[] bytes) throws SerializationException {
			if(bytes ==  null){
				return null;
				
			}else
				return Long.parseLong(new String(bytes));
				
		}			
		
	}
	
	
	public static enum CustomStringSerializer implements RedisSerializer<String>{
		INSTANCE;

		@Override
		public byte[] serialize(@Nullable String str) throws SerializationException {
			return  ( ( (str != null) && str.length() > 0 )? str.getBytes(StandardCharsets.UTF_8) : new byte[0]); 
		}

		@Override
		public String deserialize( @Nullable  byte[] bytes) throws SerializationException {
			//return ( bytes.length == 0 ? null : new String(bytes));
			if( (bytes != null) && (bytes.length == 0) ){
				return new String(bytes, StandardCharsets.UTF_8);
			}else{
				
				return null;
			}
		}	
		
	}
	
	
}//class eds

