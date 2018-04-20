package com.inso.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inso.Ecommerce.model.Administrator;


@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
	public Administrator findByEmail(String email);
	public Administrator findByName(String name);
}
