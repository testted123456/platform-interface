package com.nonobank.inter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Properties;

@SpringBootApplication
@EnableAsync
public class InterfaceApplication {

	public static void main(String[] args) {

		SpringApplication app =
				new SpringApplication(InterfaceApplication.class);

		Properties properties = new Properties();
		properties.setProperty("spring.resources.static-locations",
				"classpath:/newLocation1/, classpath:/newLocation2/");
		app.setDefaultProperties(properties);

		app.setDefaultProperties(properties);

		app.run(args);
//		SpringApplication.run(InterfaceApplication.class, args);
	}
}
