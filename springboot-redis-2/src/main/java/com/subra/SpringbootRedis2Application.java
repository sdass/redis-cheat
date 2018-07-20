package com.subra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import com.subra.Person.*;

@SpringBootApplication
@ImportResource("classpath:spring-config.xml")
public class SpringbootRedis2Application {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootRedis2Application.class, args);
		
		//new SpringbootRedis2Application().doTest();
		
		
	}
	
	
}
