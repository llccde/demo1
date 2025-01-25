package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class DemoApplication {
	public static void main1(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}


@Controller
class WelcomeController {

	@GetMapping("/welcome")
	public int welcome() {
		return Global.num;
	}
	@GetMapping("/getElement")
	public String getElement(){
		return "<p>hello</p>";
	}
	@GetMapping("/testStr")
	public Structure testStr(){
		return new Structure();
	}
}
