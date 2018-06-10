package com.inso.Ecommerce.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.inso.Ecommerce.beans.AdminDataBean;
import com.inso.Ecommerce.beans.LoginBean;
import com.inso.Ecommerce.beans.PasswordBean;
import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.model.Customer;
import com.inso.Ecommerce.service.AdministratorService;
import com.inso.Ecommerce.service.CustomerService;
import com.inso.Ecommerce.utilities.SessionManager;


@RestController
@RequestMapping("/api/admin")
public class AdministratorController {

	@Autowired
	private AdministratorService service;
	
	@Autowired
	private CustomerService cService;
	
	/**
	 * Returns the data(name, email) of the current administrator.
	 * @param request HTTP current request
	 * @return user and email of the current administrator
	 */
	@GetMapping("/")
	public ResponseEntity<Object> getAdministrator(HttpServletRequest request){
		Administrator admin = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(admin == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		MappingJacksonValue mappedAdmin = new MappingJacksonValue(admin);
		mappedAdmin.setFilters(new SimpleFilterProvider().addFilter(Administrator.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("email", "name")));
		return new ResponseEntity<>(mappedAdmin, HttpStatus.OK);
	}
	
	/**
	 * Creates a new administrator, only if the current user is a valid administrator.
	 * @param admin
	 * @param request HTTP current request
	 * @return HTTP 201 if all was okey
	 */
	@PostMapping("/")
	public ResponseEntity<Object> createAdministrator(@Valid @RequestBody Administrator admin, HttpServletRequest request){
		if(service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession())) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			if(service.findByEmail(admin.getEmail()) != null || service.findByName(admin.getName()) != null) return new ResponseEntity<>(HttpStatus.CONFLICT); 	
			service.save(new Administrator(admin.getEmail(), admin.getName(), admin.getPassword()));
			return new ResponseEntity<>(HttpStatus.CREATED);
		}		
	} 
	
	/**
	 * Update the data of the current administrator. Usefull when an administrator
	 * wants change his own password, email or name.
	 * @param admin Administrator with the new data
	 * @param request HTTP current request
	 * @return HTTP 200 if all was okey
	 */
	@PutMapping("/")
	public ResponseEntity<Object> updateAdministrator(@Valid @RequestBody AdminDataBean admin, HttpServletRequest request) {	
		Administrator rAdmin;
		if((rAdmin = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()))) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			Administrator aux;
			if(admin.getEmail() != null && !admin.getEmail().isEmpty()) {
				aux = service.findByEmail(admin.getEmail());
				if(aux != null && aux.getId() != rAdmin.getId()) return new ResponseEntity<>(HttpStatus.CONFLICT);	
				rAdmin.setEmail(admin.getEmail());
			}
			if(admin.getName() != null && !admin.getName().isEmpty()) {
				aux = service.findByName(admin.getName());
				if(aux != null && aux.getId() != rAdmin.getId()) return new ResponseEntity<>(HttpStatus.CONFLICT);
				rAdmin.setName(admin.getName());
			}
			service.save(rAdmin);
			SessionManager.getInstance().setSessionEmail(request.getSession(), admin.getEmail());
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
	public ResponseEntity<Object> updateAdminPass(@Valid @RequestBody PasswordBean admin, HttpServletRequest request){
		Administrator rAdmin;
		if((rAdmin = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()))) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
		String old = org.apache.commons.codec.digest.DigestUtils.sha256Hex(admin.getOldPassword());
		if(!rAdmin.getPassword().equals(old)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		if(admin.getNewPassword() == null || "".equals(admin.getNewPassword())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
		rAdmin.setPassword(admin.getNewPassword());			
		service.save(rAdmin);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	/**
	 * Delete the current administrator account
	 * @param request HTTP current request
	 * @return HTTP 200 if all was okey
	 */
	@DeleteMapping("/")
	public ResponseEntity<Object> deleteAdministrator(HttpServletRequest request){
		Administrator admin = service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		service.delete(admin);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Return a list of all administators in the system(excluding actual administrator)
	 * @param request HTTP current request
	 * @return List of all administrators
	 */
	@GetMapping("/administrators")
	public ResponseEntity<Object> getAllAdministrators(HttpServletRequest request){
		String email = SessionManager.getInstance().getSessionEmail(request.getSession());
		if(service.findByEmail(email) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			List<Administrator> a = service.findAll();
			Iterator<Administrator> i = a.iterator();
			while (i.hasNext()) {
			   Administrator adm = i.next();
			   if(adm.getEmail().equals(email)) i.remove();
			}
			MappingJacksonValue mappedAdmin = new MappingJacksonValue(a);
			mappedAdmin.setFilters(new SimpleFilterProvider().addFilter(Administrator.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("id", "email", "name")));
			return new ResponseEntity<>(mappedAdmin, HttpStatus.OK);
		}
	}
	
	/**
	 * Delete an administrator 
	 * @param id Id of the administrator
	 * @param request HTTP current request
	 * @return HTTP 200 if all was okey
	 */
	@DeleteMapping("/administrator/{id}")
	public ResponseEntity<Object> deleteAdmin(@PathVariable("id") Integer id, HttpServletRequest request){
		if(service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession())) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			Administrator aux = service.findById(id);
			if(aux == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			service.delete(aux);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	/**
	 * Return a list of all customers in the system
	 * @param request HTTP current request
	 * @return List with all customers
	 */
	@GetMapping("/customers")
	public ResponseEntity<Object> getAllCustomers(HttpServletRequest request){
		if(service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession())) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			List<Customer> c = cService.findAll();;
			MappingJacksonValue mappedCustomers = new MappingJacksonValue(c);
			mappedCustomers.setFilters(new SimpleFilterProvider().addFilter(Customer.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("id", "email", "name")));
			return new ResponseEntity<>(mappedCustomers, HttpStatus.OK);
		}
	}
	
	/**
	 * Return all data about a customer
	 * @param id Id of a customer
	 * @param request HTTP current request
	 * @return JSON with the customer data
	 */
	@GetMapping("/customer/{id}")
	public ResponseEntity<Object> getCustomerDetailed(@PathVariable("id") Integer id, HttpServletRequest request){
		if(service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession())) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			Customer cus = cService.findById(id);
			if(cus == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			MappingJacksonValue mappedCustomer = new MappingJacksonValue(cus);
			mappedCustomer.setFilters(new SimpleFilterProvider().addFilter(Customer.FILTER, SimpleBeanPropertyFilter.serializeAll()));
			return new ResponseEntity<>(mappedCustomer, HttpStatus.OK);
		}
	}
	
	/**
	 * Deletes a customer
	 * @param id Id of a customer
	 * @param request HTTP current request
	 * @return HTTP 200 if all was okey
	 */
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable("id") Integer id, HttpServletRequest request){
		if(service.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession())) == null) {
			SessionManager.getInstance().delete(request.getSession());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}else {
			Customer aux = cService.findById(id);
			if(aux == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			cService.delete(aux);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	/**
	 * Login for administrators
	 * @param admin User || email and password
	 * @param request HTTP current request
	 * @return HTTP 200 if all was okey
	 */
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginBean admin, HttpServletRequest request) {
		Administrator rAdmin = service.findByEmail(admin.getUser());
		if(rAdmin == null) rAdmin = service.findByName(admin.getUser());
		if(rAdmin == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		
		if(rAdmin.getPassword().equals(admin.getPassword())) {
			SessionManager.getInstance().setSessionEmail(request.getSession(), rAdmin.getEmail());
			return new ResponseEntity<>(HttpStatus.OK);
		}else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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