package com.subra;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class SpringbootRedisMsgApplication {

	public static void main(String[] args) throws InterruptedException {
		// SpringApplication.run(SpringbootRedisMsgApplication.class, args);
		ApplicationContext ctx = SpringApplication.run(SpringbootRedisMsgApplication.class, args);
		StringRedisTemplate strRedisTmplate = ctx.getBean(StringRedisTemplate.class);
		CountDownLatch cntDnLatch = ctx.getBean(CountDownLatch.class);
		System.out.println("sending messge...");
		strRedisTmplate.convertAndSend("chat", "Hello From Redis...");
		cntDnLatch.await();
		System.exit(0);
		
	}
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

		return container;
	}
	
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	@Bean
	Receiver receiver(CountDownLatch latch) {
		return new Receiver(latch);
	}
	
	@Bean
	CountDownLatch latch() {
		return new CountDownLatch(1);
	}
	
	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

}
