package com.inso.Ecommerce.service;

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
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		return repository.findByEmail(email);
	}

	@Override
	public Administrator findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return repository.findByName(name);
	}

	@Override
	public Administrator save(Administrator admin) {
		if (admin == null) {
			return null;
		}
		return repository.save(admin);
	}	
}
