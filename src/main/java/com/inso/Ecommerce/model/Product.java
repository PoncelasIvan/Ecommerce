package com.inso.Ecommerce.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter(Product.FILTER)
@Entity
public class Product {
	public static final String FILTER = "ProductFilter";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, scale = 0)
	private Integer id;
	
	@NotNull
	private String name;
	
	private String description;
	
	@NotNull
	@Min(0)
	private int price;

	@NotNull
	@Min(0)
	private int stock;
	
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@ManyToOne
    @JoinColumn(name = "administratorId")
	private Administrator administrator;
	
	@OneToMany(targetEntity = Image.class, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Image> images;
	
	@OneToMany(targetEntity = ProductSell.class, mappedBy = "product", fetch = FetchType.EAGER)
	private List<Sell> sells;
	
	public Product() {}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getDate() {
		return creationDate;
	}

	public void setDate(Date date) {
		this.creationDate = date;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}
}
