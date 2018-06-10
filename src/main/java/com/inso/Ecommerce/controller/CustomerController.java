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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.inso.Ecommerce.beans.CustomerDataBean;
import com.inso.Ecommerce.beans.PasswordBean;
import com.inso.Ecommerce.beans.LoginBean;
import com.inso.Ecommerce.model.Customer;
import com.inso.Ecommerce.service.CustomerService;
import com.inso.Ecommerce.utilities.SessionManager;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	/**
	 * Return the data of the current customer
	 * @param request HTTP current request
	 * @return data of the current customer
	 */
	@GetMapping("/")
	public ResponseEntity<Object> getCustomer(HttpServletRequest request){
		Customer cust = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(cust == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		
		MappingJacksonValue mappedCust = new MappingJacksonValue(cust);
		mappedCust.setFilters(new SimpleFilterProvider().addFilter(Customer.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("email", "name")));
		return new ResponseEntity<>(mappedCust, HttpStatus.OK);
	}
	
	/**
	 * Create a new customer
	 * @param cust Customer to create
	 * @param request HTTP current request
	 * @return HTTP 201 if all was okey
	 */
	@PostMapping("/")
	public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer cust, HttpServletRequest request){
		if(cust.getEmail().isEmpty() || cust.getName().isEmpty() || cust.getPassword().isEmpty() || cust.getId() != null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if(service.findByEmail(cust.getEmail()) != null || service.findByName(cust.getName()) != null)
			return new ResponseEntity<>(HttpStatus.CONFLICT); 	
		service.save(cust);
		return new ResponseEntity<>(HttpStatus.CREATED);	
	} 
	
	/**
	 * Update the personal data of the current customer
	 * @param cust CustomerDataBean with the new data
	 * @param request HTTP current request
	 * @return HTTP 200 if all was okey
	 */
	@PutMapping("/")
	public ResponseEntity<Object> updateCustomer(@Valid @RequestBody CustomerDataBean cust, HttpServletRequest request) {	
		Customer rCust;
		if((rCust = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()))) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			Customer aux;
			if(cust.getEmail() != null && !(cust.getEmail().isEmpty())) {
				aux = service.findByEmail(cust.getEmail());
				if(aux != null && (aux.getId() != rCust.getId()))
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				rCust.setEmail(cust.getEmail());
			}
			if(cust.getName() != null && !(cust.getName().isEmpty())) {
				aux = service.findByName(cust.getName());
				if(aux != null && (aux.getId() != rCust.getId()))
					return new ResponseEntity<>(HttpStatus.CONFLICT);	
				rCust.setName(cust.getName());
			}
			service.save(rCust);
			SessionManager.getInstance().setSessionEmail(request.getSession(), cust.getEmail());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	/**
	 * Update the password of the current customer
	 * @param cust CustomerPassBean with the new password
	 * @param request HTTP current request
	 * @return HTTP 200 if all was okey
	 */
	@PutMapping("/password")
	public ResponseEntity<Object> updateCustomerPass(@Valid @RequestBody PasswordBean cust, HttpServletRequest request){
		Customer rCust;
		if((rCust = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()))) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
		String old = org.apache.commons.codec.digest.DigestUtils.sha256Hex(cust.getOldPassword());
		if(!rCust.getPassword().equals(old)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		if(cust.getNewPassword() == null || "".equals(cust.getNewPassword())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
		rCust.setPassword(cust.getNewPassword());			
		service.save(rCust);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	/**
	 * Delete the current customer
	 * @param request HTTP current request
	 * @return HTTP 200
	 */
	@DeleteMapping("/")
	public ResponseEntity<Object> deleteCustomer(HttpServletRequest request){
		Customer cust = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		service.delete(cust);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Login for customers
	 * @param cust Customer with data to login
	 * @param request HTTP current request
	 * @return HTTP 200 if all was OKEY
	 */
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginBean cust, HttpServletRequest request) {
		Customer rCust = service.findByEmail(cust.getUser());
		if(rCust == null) rCust = service.findByName(cust.getUser());
		if(rCust == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		if(rCust.getPassword().equals(cust.getPassword())) {
			SessionManager.getInstance().setSessionEmail(request.getSession(), rCust.getEmail());
			return new ResponseEntity<>(HttpStatus.OK);
		} else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Logout
	 * @param request HTTP current request
	 * @return HTTP 200
	 */
	@DeleteMapping("/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request){
		SessionManager.getInstance().delete(request.getSession());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}