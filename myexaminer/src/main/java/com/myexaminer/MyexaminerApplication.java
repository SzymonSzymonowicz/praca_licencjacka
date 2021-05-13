package com.myexaminer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
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
                registry.addMapping("/account").allowedMethods("*").allowedOrigins(LOCALHOST);
                registry.addMapping("/account/login").allowedMethods("*").allowedOrigins(LOCALHOST);
                registry.addMapping("/account/role").allowedMethods("*").allowedOrigins(LOCALHOST);
                registry.addMapping("/archive/check").allowedMethods("*").allowedOrigins(LOCALHOST);
                registry.addMapping("/archive/exercises").allowedMethods("*").allowedOrigins(LOCALHOST);
                registry.addMapping("/exam/{idGroup}").allowedOrigins(LOCALHOST);
                registry.addMapping("/exam/status").allowedMethods("*").allowedOrigins(LOCALHOST);
                registry.addMapping("/exercises/{id}").allowedOrigins(LOCALHOST);
                registry.addMapping("/individual-exams/lecturer-groups").allowedOrigins(LOCALHOST);
                registry.addMapping("/notebook").allowedMethods("*").allowedOrigins(LOCALHOST);
                registry.addMapping("/groups/account/{accountId}").allowedMethods("*").allowedOrigins(LOCALHOST);
            }
        };
    }
}
