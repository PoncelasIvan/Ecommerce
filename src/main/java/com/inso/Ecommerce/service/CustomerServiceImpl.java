package com.inso.Ecommerce.service;

import java.util.List;
import java.util.Optional;

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
		return StringUtils.isEmpty(email) ? null : repository.findByEmail(email);
	}

	@Override
	public Customer findByName(String name) {
		return StringUtils.isEmpty(name) ? null : repository.findByName(name);
	}

	@Override
	public Customer save(Customer cust) {
		return cust == null ? null : repository.save(cust);
	}

	@Override
	public void delete(Customer cust) {
		if(cust != null) repository.delete(cust);
	}

	@Override
	public List<Customer> findAll() {
		return repository.findAll();
	}

	@Override
	public Customer findById(Integer id) {
		if(id == null) return null;
		Optional<Customer> oc = repository.findById(id);
		return oc.isPresent() ? oc.get() : null;
	}
	
}
