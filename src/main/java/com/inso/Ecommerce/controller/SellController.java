package com.inso.Ecommerce.controller;

import java.util.Date;
import java.util.List;

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
import com.inso.Ecommerce.beans.ProductSellBean;
import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.model.Customer;
import com.inso.Ecommerce.model.Product;
import com.inso.Ecommerce.model.ProductSell;
import com.inso.Ecommerce.model.Sell;
import com.inso.Ecommerce.service.AdministratorService;
import com.inso.Ecommerce.service.CustomerService;
import com.inso.Ecommerce.service.ProductSellService;
import com.inso.Ecommerce.service.ProductService;
import com.inso.Ecommerce.service.SellService;
import com.inso.Ecommerce.utilities.SessionManager;


@RestController
@RequestMapping("/api/sell")
public class SellController {
	
	@Autowired
	AdministratorService aService;
	
	@Autowired
	CustomerService cService;
	
	@Autowired
	ProductSellService psService;
	
	@Autowired
	ProductService pService;
	
	@Autowired
	SellService service;
	
	
	@GetMapping("/")
	public ResponseEntity<Object> getSells(HttpServletRequest request){
		Administrator admin = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(admin != null){
			List<Sell> sells = service.findAll();
			MappingJacksonValue mappedSells = new MappingJacksonValue(sells);
			mappedSells.setFilters(new SimpleFilterProvider().addFilter(Sell.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("customer", "date", "state")));
			return new ResponseEntity<>(mappedSells, HttpStatus.OK);
		}
		
		Customer customer = cService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(customer != null){
			List<Sell> sells = customer.getSells(); 
			MappingJacksonValue mappedSells = new MappingJacksonValue(sells);
			mappedSells.setFilters(new SimpleFilterProvider().addFilter(Sell.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("date", "state")));
			return new ResponseEntity<>(mappedSells, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/")
	public ResponseEntity<Object> create(@Valid @RequestBody List<ProductSellBean> products,@Valid @RequestBody Sell sell, HttpServletRequest request){
		Customer cust= cService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(cust == null)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		sell.setCustomer(cust);

		Date date = new Date();
		sell.setDate(date);
		sell.setState(0);
		service.save(sell);
		
		for(int x = 0; x < products.size(); x++){
			ProductSell prosel = new ProductSell();
			int productId = products.get(x).getProductId();
			int cantidad = products.get(x).getCantidad();
			Product product = pService.findById(productId);
			
			prosel.setCuantity(cantidad);
			prosel.setProduct(product);
			prosel.setSell(sell);
			psService.save(prosel);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	public ResponseEntity<Object> update(@Valid @RequestBody Sell sell, HttpServletRequest request){
		Administrator admin = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(admin == null)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		Sell rSell = service.findById(sell.getId());
		if(rSell.getState() == sell.getState())
			return new ResponseEntity<>(HttpStatus.OK);
		rSell.setState(sell.getState());
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<Object> delete(@Valid @RequestBody Sell sell, HttpServletRequest request){
		Administrator admin = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(admin == null)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		Sell rSell = service.findById(sell.getId());
		service.delete(rSell);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
}