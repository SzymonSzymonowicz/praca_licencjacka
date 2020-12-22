package com.myexaminer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MyexaminerApplication {

	public static final String LOCALHOST = "http://localhost:3000";

	public static void main(String[] args) {
		SpringApplication.run(MyexaminerApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/account").allowedOrigins(LOCALHOST);
				registry.addMapping("/account/login").allowedOrigins(LOCALHOST);
				registry.addMapping("/exam/{idGroup}").allowedOrigins(LOCALHOST);
				registry.addMapping("/exercise/{idExam}").allowedOrigins(LOCALHOST);
			}
		};
	}
}
