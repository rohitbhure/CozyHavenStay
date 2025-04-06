package com.hexaware.CozyHavenStay;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy

public class CozyHavenStayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CozyHavenStayApplication.class, args);
	}
	 
	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
