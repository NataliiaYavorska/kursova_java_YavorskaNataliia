package com.itvdn.cbs.Security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityApp {

	private static PasswordEncoder passwordEncoder;

	public SpringSecurityApp(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApp.class, args);

		generatePass();
	}

	public static void generatePass() {
		System.out.println("Encoded password: " + passwordEncoder.encode("12345"));
	}
}
