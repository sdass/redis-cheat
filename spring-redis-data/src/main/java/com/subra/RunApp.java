package com.subra;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import com.subra.Application.StringSerializer;

enum Day1 {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY 
}

public class RunApp {

	public static void main(String[] args) {
		System.out.println("This is test2");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		System.out.println("ctx= " + ctx != null);
		othermethods(ctx);
		atomicCounterMethod(ctx);
						
	}
	
	public static void atomicCounterMethod(ApplicationContext ctx){
		//RedisTemplate<String, Integer> redis = ctx.getBean(RedisTemplate.class);
		RedisConnectionFactory rediscf = ctx.getBean(RedisConnectionFactory.class);
		RedisAtomicLong counter = new RedisAtomicLong("spring-data:atomic-counter", rediscf, -1L);
		System.out.println("atomic counter=" + counter );
	}
	public static void othermethods(ApplicationContext ctx){
		//RedisConnectionFactory cf = ctx.getBean(RedisConnectionFactory.class);
		RedisTemplate<String, Long> redis = ctx.getBean(RedisTemplate.class);
		redis.setKeySerializer(Application.StringSerializer.INSTANCE);
		redis.setValueSerializer(Application.LongSerializer.INSTANCE);
		
		ValueOperations<String, Long> ops = redis.opsForValue();
		String key = "spring-data-book:counter:hits";
		ops.setIfAbsent(key, 57L);
		Long x = ops.increment(key, 10);
		System.out.println("hits: " + ops.get("spring-data-book:counter:hits") + " x=" + x);
		
		
		
		
		
	}
		
	public void testingMinorFunctionality(){

		StringSerializer strSeriInstance = StringSerializer.INSTANCE;
				byte[] xb = StringSerializer.INSTANCE.serialize("abc");
				System.out.println(new String(xb));
				System.out.println(StringSerializer.INSTANCE.deserialize(xb));
				
				System.out.println(strSeriInstance.deserialize(xb));			
				System.out.println(Day1.SATURDAY);
				Day1 ff = Day1.MONDAY;
	}

}
