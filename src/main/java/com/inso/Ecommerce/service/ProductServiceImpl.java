package com.inso.Ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.model.Product;
import com.inso.Ecommerce.repository.AdministratorRepository;
import com.inso.Ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Autowired
	private ProductRepository repository ;
	
	@Override
	public List<Product> findByAdministrator(Administrator administrator) {
		Optional<Administrator> admin = administratorRepository.findById(administrator.getId());
		return admin.isPresent() ? admin.get().getProducts() : null;
	}

	@Override
	public void save(Product product) {
		repository.save(product);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public Product findById(Integer id) {
		Optional<Product> aux = repository.findById(id);
		return (aux.isPresent()) ? aux.get() : null;
	}
}
