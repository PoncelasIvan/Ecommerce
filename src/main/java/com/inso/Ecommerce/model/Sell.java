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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


@JsonFilter(Sell.FILTER)  
@Entity
public class Sell {
	
	public static final String FILTER = "SellFilter";
	
	public static enum State {
			RECEIVED, 
			IN_PROGRESS, 
			COMPLETED
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, scale = 0)
	private Integer id;

	@NotNull
	private State state;

	@NotNull 
	private Date date;
	
	@ManyToOne
    @JoinColumn(name = "customerId")
	private Customer customer;
	
	@OneToMany(targetEntity = ProductSell.class, mappedBy = "sell", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductSell> products;
	
	public Sell() {}

	public Sell(@NotNull State state, @NotNull Date date, Customer customer) {
		super();
		this.state = state;
		this.date = date;
		this.customer = customer;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getDate() {
		return date.toString();
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCustomer() {
		return customer.getName();
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/*public List<List<String>> getProducts(){
		List<List<String>> prod = new ArrayList<List<String>>();
		for(int i = 0; i < products.size(); i++){
			List<String> aux = new ArrayList<String>();

			aux.add(""+products.get(i).getProduct().getId());
			aux.add(products.get(i).getProduct().getTitle());
			aux.add(""+products.get(i).getProduct().getPrice());

			aux.add(""+products.get(i).getCuantity());

			prod.add(aux);
		}
		return prod;
	}*/
	
	public ArrayNode getProducts(){
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode prod = mapper.createArrayNode();
		
		for(int i = 0; i < products.size(); i++){
			ObjectNode aux = mapper.createObjectNode();
			
			aux.put("id", products.get(i).getProduct().getId());
			aux.put("title", products.get(i).getProduct().getTitle());
			aux.put("price", products.get(i).getProduct().getPrice());
			aux.put("cuantity", products.get(i).getCuantity());

			prod.add(aux);
		}
		return prod;
	}
	
}