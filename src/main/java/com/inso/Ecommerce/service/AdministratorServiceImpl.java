package com.inso.Ecommerce.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.repository.AdministratorRepository;

@Service
public class AdministratorServiceImpl implements AdministratorService{

	@Autowired
	private AdministratorRepository repository;
	
	@Override
	public Administrator findByEmail(String email) {
		return StringUtils.isEmpty(email) ? null : repository.findByEmail(email);
	}

	@Override
	public Administrator findByName(String name) {
		return StringUtils.isEmpty(name) ? null : repository.findByName(name);
	}

	@Override
	public Administrator save(Administrator admin) {
		return admin == null ? null : repository.save(admin);
	}

	@Override
	public void delete(Administrator admin) {
		if(admin != null) repository.delete(admin);
	}

	@Override
	public List<Administrator> findAll() {
		return repository.findAll();
	}

	@Override
	public Administrator findById(Integer id) {
		if(id == null) return null;
		Optional<Administrator> admin = repository.findById(id);
		return admin.isPresent() ? admin.get() : null;
	}
	
}
