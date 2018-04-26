package com.inso.Ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.inso.Ecommerce.model.Customer;
import com.inso.Ecommerce.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository repository;
	
	@Override
	public Customer findByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		return repository.findByEmail(email);
	}

	@Override
	public Customer findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return repository.findByName(name);
	}

	@Override
	public Customer save(Customer cust) {
		if (cust == null) {
			return null;
		}
		return repository.save(cust);
	}
	
}
