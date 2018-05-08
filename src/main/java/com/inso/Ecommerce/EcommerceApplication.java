package com.inso.Ecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Ecommerce created for Software Engineering II 
 * Universidad de Leon, España
 * 
 * @author Daniel Pastor Perez
 * @author Diego Santos Campo
 * @author Ivan Poncelas Vicente
 */

@EnableWebSecurity
@SpringBootApplication
public class EcommerceApplication extends WebSecurityConfigurerAdapter implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		http.csrf().disable();
	}
	
	@Override
	public void run(String... strings) throws Exception {
	}
	
}