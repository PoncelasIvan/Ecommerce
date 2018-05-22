package com.inso.Ecommerce.service;

import java.util.List;

import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.model.Product;

public interface ProductService {
	List<Product> findByAdministrator(Administrator administrator);
	
	Product save(Product product);
	
	List<Product> findAll();
	
	Product findById(Integer id);
	
	void deleteById(Integer id);
	
	List<Product> search(String search);
}
