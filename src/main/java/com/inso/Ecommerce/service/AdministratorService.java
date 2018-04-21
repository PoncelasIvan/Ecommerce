package com.inso.Ecommerce.service;

import com.inso.Ecommerce.model.Administrator;

public interface AdministratorService {

	Administrator findByEmail(String email);

	Administrator findByName(String name);
	
	Administrator save(Administrator admin);
}
