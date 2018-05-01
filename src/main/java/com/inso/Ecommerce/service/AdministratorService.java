package com.inso.Ecommerce.service;

import java.util.List;

import com.inso.Ecommerce.model.Administrator;

public interface AdministratorService {

	List<Administrator> findAll();
	
	Administrator findById(Integer id);
	
	Administrator findByEmail(String email);

	Administrator findByName(String name);
	
	Administrator save(Administrator admin);
	
	void delete(Administrator admin);
}
