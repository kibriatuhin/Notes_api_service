package com.notes_api_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.notes_api_service.repository")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class NotesApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesApiServiceApplication.class, args);
	}

}


