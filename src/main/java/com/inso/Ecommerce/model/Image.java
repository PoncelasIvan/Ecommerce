package com.inso.Ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter(Image.FILTER)
@Entity
public class Image {
	public final static String FILTER = "ImageFilter";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, scale = 0)
	private Integer id;

	@NotNull
	private String url;
	
	@ManyToOne
    @JoinColumn(name = "productId")
	private Product product;
	
	public Image() {}
	
	public Image(@NotNull String url, Product product) {
		super();
		this.url = url;
		this.product = product;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getId(){
		return this.id;
	}
}
