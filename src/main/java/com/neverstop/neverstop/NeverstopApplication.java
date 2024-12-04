package com.neverstop.neverstop;

import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/praveen")
public class NeverstopApplication {

	public static void main(String[] args) {

		SpringApplication.run(NeverstopApplication.class, args);
	}


	@GetMapping("/message")
	public String getMessage(){
		return "Hey CHANDU BHAI 22!! Your application sucessfully Deployed in Azure";
	}

}
