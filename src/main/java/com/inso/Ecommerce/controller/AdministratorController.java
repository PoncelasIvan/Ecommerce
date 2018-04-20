package com.inso.Ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.service.AdministratorService;


@RestController
@RequestMapping("/api/admin")
public class AdministratorController {
	
	@Autowired
	private AdministratorService service;
	
	@PostMapping("/create")
	public ResponseEntity<Object> create(@Valid @RequestBody Administrator admin){
		if(StringUtils.isEmpty(admin.getPassword())) 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		
		if(service.findByEmail(admin.getEmail()) != null || service.findByName(admin.getName()) != null)
			return new ResponseEntity<>(HttpStatus.CONFLICT); 	
		
		service.save(admin);
		return new ResponseEntity<>(HttpStatus.CREATED);	
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody Administrator admin) {	
		if(StringUtils.isEmpty(admin.getPassword())) 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Administrator rAdmin;
		
		if(admin.getEmail() != null)
			 rAdmin = service.findByEmail(admin.getEmail());
		else
			rAdmin = service.findByName(admin.getEmail());
		
		if(rAdmin == null) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
		if(rAdmin.getPassword().equals(admin.getPassword())) {
			MappingJacksonValue mappedAdmin = new MappingJacksonValue(rAdmin);
			mappedAdmin.setFilters(new SimpleFilterProvider().addFilter(Administrator.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("email", "name")));
			return new ResponseEntity<>(mappedAdmin, HttpStatus.OK);
		}else { 
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/update")
	public ResponseEntity<Object> update(@Valid @RequestBody Administrator admin) {	
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	
}