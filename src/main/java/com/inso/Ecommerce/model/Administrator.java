package com.inso.Ecommerce.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.annotation.JsonFilter;


@JsonFilter(Administrator.FILTER)
@Entity
public class Administrator {
	public static final String FILTER = "AdministratorFilter";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, scale = 0)
	private Integer id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String email;
	
	@NotNull
	private String password;

	@OneToMany(targetEntity = Product.class, mappedBy = "administrator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Product> products;
	 
	public Administrator() {};
	
	public Administrator(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public Administrator(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = DigestUtils.sha256Hex(password);
	}
	
	public void setPasswordHashed(String hash) {
		this.password = hash;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
