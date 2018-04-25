package com.inso.Ecommerce.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.inso.Ecommerce.model.Administrator;
import com.inso.Ecommerce.model.Image;
import com.inso.Ecommerce.model.Product;
import com.inso.Ecommerce.service.AdministratorService;
import com.inso.Ecommerce.service.ImageService;
import com.inso.Ecommerce.service.ProductService;
import com.inso.Ecommerce.utilities.SessionManager;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Value("${resources.path}")
	private String imagesPath;
	
	@Autowired
	private ProductService service;
	
	@Autowired 
	private AdministratorService aService;

	@Autowired
	private ImageService iService;
	
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
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id, HttpServletRequest request){
		Administrator administrator = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(administrator == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@PostMapping("/{id}/image")
	public ResponseEntity<Object> uploadImage(@PathVariable("id") int id, @RequestParam("image") MultipartFile file, HttpServletRequest request){
		Administrator admin = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(admin == null)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		try {
			if(!file.isEmpty()) {
	             BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
	             File destination = new File(imagesPath + DigestUtils.sha256Hex(admin.getEmail() + new Date().toString()) + ".png");
	             ImageIO.write(src, "png", destination);
	             Image img = new Image(destination.getPath(), service.findById(id));

	             iService.save(img);
			}else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/image/{imageId}")
	public ResponseEntity<Object> deleteImage(@PathVariable("imageId") int imageId, HttpServletRequest request){
		Administrator admin = aService.findByEmail(SessionManager.getInstance().getSessionEmail(request.getSession()));
		if(admin == null)
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		iService.deleteById(imageId);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
