package com.inso.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inso.Ecommerce.model.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	public Customer findByEmail(String email);
	public Customer findByName(String name);
}
