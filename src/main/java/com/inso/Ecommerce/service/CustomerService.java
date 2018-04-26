package com.inso.Ecommerce.service;

import com.inso.Ecommerce.model.Customer;

public interface CustomerService {

	Customer findByEmail(String email);

	Customer findByName(String name);
	
	Customer save(Customer cust);
}
