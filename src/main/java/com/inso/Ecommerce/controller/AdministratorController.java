package com.inso.Ecommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.service.AdministratorService;
import com.inso.Ecommerce.utilities.SessionManager;


@RestController
@RequestMapping("/api/admin")
public class AdministratorController {
	
	@Autowired
	private AdministratorService service;
	
	@GetMapping("/")
	public ResponseEntity<Object> getAdminDetails(HttpServletRequest request){
		String aMail = SessionManager.getInstance().getSessionEmail(request.getSession());
		if(aMail == null || aMail.isEmpty()) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else if(service.findByEmail(aMail) != null){
			MappingJacksonValue mappedAdmin = new MappingJacksonValue(service.findByEmail(aMail));
			mappedAdmin.setFilters(new SimpleFilterProvider().addFilter(Administrator.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("email", "name")));
			return new ResponseEntity<>(mappedAdmin, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	
	@PostMapping("/create")
	public ResponseEntity<Object> create(@Valid @RequestBody Administrator admin, HttpServletRequest request){
		if(admin.getEmail().isEmpty() || admin.getName().isEmpty() || admin.getPassword().isEmpty() || admin.getId() != null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		if(service.findByEmail(admin.getEmail()) != null || service.findByName(admin.getName()) != null)
			return new ResponseEntity<>(HttpStatus.CONFLICT); 	
		
		service.save(admin);
		return new ResponseEntity<>(HttpStatus.CREATED);	
	} 
	
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody Administrator admin, HttpServletRequest request) {
		Administrator rAdmin;
		
		if(admin.getEmail() != null)
			 rAdmin = service.findByEmail(admin.getEmail());
		else
			rAdmin = service.findByName(admin.getName());
		
		if(rAdmin == null) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
		if(rAdmin.getPassword().equals(admin.getPassword())) {
			SessionManager.getInstance().setSessionEmail(request.getSession(), rAdmin.getEmail());
			return new ResponseEntity<>(HttpStatus.OK);
		}else { 
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request){
		SessionManager.getInstance().delete(request.getSession());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<Object> update(@Valid @RequestBody Administrator admin, HttpServletRequest request) {	
		Administrator rAdmin;
		if((rAdmin = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()))) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			Administrator aux;
			if(admin.getEmail() != null && !admin.getEmail().isEmpty()) {
				aux = service.findByEmail(admin.getEmail());
				if(aux != null && aux.getId() != rAdmin.getId())
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				
				rAdmin.setEmail(admin.getEmail());
			}
			
			aux = null;
			
			if(admin.getName() != null && !admin.getName().isEmpty()) {
				aux = service.findByName(admin.getName());
				if(aux != null && aux.getId() != rAdmin.getId())
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				
				rAdmin.setName(admin.getName());
			}
			
			if(admin.getPassword() != null && !admin.getPassword().isEmpty())
				rAdmin.setPassword(admin.getPassword());
			
			service.save(rAdmin);
			SessionManager.getInstance().setSessionEmail(request.getSession(), admin.getEmail());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}	
}