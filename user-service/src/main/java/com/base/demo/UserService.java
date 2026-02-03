package com.base.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@OpenAPIDefinition(
//		servers = {
//				@Server(url = "/")
//		}
//)
@SpringBootApplication
public class UserService {

	public static void main(String[] args) {
		SpringApplication.run(UserService.class, args);
	}

}
