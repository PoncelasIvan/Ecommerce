package com.inso.Ecommerce.service;

import java.util.List;

import com.inso.Ecommerce.model.Customer;

public interface CustomerService {
	
	List<Customer> findAll();

	Customer findById(Integer id);
	
	Customer findByEmail(String email);

	Customer findByName(String name);
	
	Customer save(Customer cust);
	
	void delete(Customer cust);

}
