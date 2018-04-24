package com.inso.Ecommerce.service;

import java.util.List;

import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.model.Product;

public interface ProductService {
	List<Product> findByAdministrator(Administrator administrator);
	void save(Product product);
	List<Product> findAll();
	public Product findById(Integer id);
}
