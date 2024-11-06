package com.forrrest.appmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
	"com.forrrest.appmanagementservice",
	"com.forrrest.common"
})
public class AppManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppManagementServiceApplication.class, args);
	}

}
