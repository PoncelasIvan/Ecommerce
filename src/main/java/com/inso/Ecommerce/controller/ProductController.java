package com.inso.Ecommerce.controller;

import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.model.Product;
import com.inso.Ecommerce.service.AdministratorService;
import com.inso.Ecommerce.service.ProductService;
import com.inso.Ecommerce.utilities.SessionManager;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@Autowired 
	private AdministratorService aService;

	
	@GetMapping("/")
	public ResponseEntity<Object> getProducts(HttpServletRequest request){
		Administrator administrator = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		SimpleFilterProvider filter = new SimpleFilterProvider().addFilter(Product.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("name", "price"));
		if(administrator != null) {
			MappingJacksonValue mappedProducts = new MappingJacksonValue(administrator.getProducts());
			mappedProducts.setFilters(filter);
			return new ResponseEntity<>(mappedProducts, HttpStatus.OK);
		}
		
		MappingJacksonValue mappedProducts = new MappingJacksonValue(service.findAll());
		mappedProducts.setFilters(filter);
		return new ResponseEntity<>(mappedProducts, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Object> create(@Valid @RequestBody Product product, HttpServletRequest request){
		Administrator administrator = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(administrator == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		product.setDate(new Date());
		product.setAdministrator(administrator);
		service.save(product);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getDetails(@PathVariable("id") int id, HttpServletRequest request){
		Product prod = service.findById(id);
		if(prod == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		MappingJacksonValue mappedProduct = new MappingJacksonValue(service.findById(id));
		mappedProduct.setFilters(new SimpleFilterProvider().addFilter(Product.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("name", "description", "price", "stock")));
		return new ResponseEntity<>(mappedProduct, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(HttpServletRequest request){
		Administrator administrator = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(administrator == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}	
	
}
