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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.annotation.JsonFilter;


@JsonFilter(Sell.FILTER)  
@Entity
public class Sell {
	
	public static final String FILTER = "SellFilter";
	
	private static enum State{
			RECEIVED, IN_PROGRESS, COMPLETED
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, scale = 0)
	private Integer id;

	@NotNull
	private int state;

	@NotNull 
	private Date date;
	
	@ManyToOne
    @JoinColumn(name = "customerId")
	private Customer customer;
	
	@OneToMany(targetEntity = ProductSell.class, mappedBy = "sell", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductSell> products;
	
	public Sell() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	};

	
}