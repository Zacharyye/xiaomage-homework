package com.zachary.starter;

import com.zachary.configuration.HelloServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StarterApplication {
	@Autowired
	private HelloServiceConfiguration helloService;

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args);
	}

	@RequestMapping("/name")
	public String getName() {
		return helloService.getName();
	}

	@RequestMapping("/hobby")
	public String getHobby () {
		return helloService.getHobby();
	}
}
