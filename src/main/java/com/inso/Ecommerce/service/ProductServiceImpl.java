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
		if(administrator == null) return null;
		Optional<Administrator> admin = administratorRepository.findById(administrator.getId());
		return admin.isPresent() ? admin.get().getProducts() : null;
	}

	@Override
	public Product save(Product product) {
		return product == null ? null : repository.save(product);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public Product findById(Integer id) {
		Optional<Product> aux = repository.findById(id);
		return aux.isPresent() ? aux.get() : null;
	}

	@Override
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
	
	@Override
	public List<Product> search(String search){
		return repository.search(search);
	}
}
