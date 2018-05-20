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
	private String title;
	
	private String author;
	
	private String synopsis;
	
	private String format;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getImg(){
		Image easy = images.get(0);
		if(easy != null)
			return easy.getUrl();
		return "";
	}
	
	public String getImgs(){
		String urls = "";
		for(int i=0;i<images.size();i++){
			urls = urls + images.get(i).getUrl() + " ";
		}
		return urls;
	}
}
