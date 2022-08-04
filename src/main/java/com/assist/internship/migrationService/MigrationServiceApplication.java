package com.assist.internship.migrationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "")
public class MigrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MigrationServiceApplication.class, args);
	}

}
