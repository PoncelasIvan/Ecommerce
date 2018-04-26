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


@JsonFilter(ProductSell.FILTER)  
@Entity
public class ProductSell {
	
	public static final String FILTER = "ProductSellFilter";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, scale = 0)
	private Integer id;
	
	@NotNull
	private int cuantity;
	
	@ManyToOne
    @JoinColumn(name = "ProductId")
	private Product product;
	
	@ManyToOne
    @JoinColumn(name = "SellId")
	private Sell sell;

	public int getCuantity() {
		return cuantity;
	}

	public void setCuantity(int cuantity) {
		this.cuantity = cuantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Sell getSell() {
		return sell;
	}

	public void setSell(Sell sell) {
		this.sell = sell;
	}
}
	