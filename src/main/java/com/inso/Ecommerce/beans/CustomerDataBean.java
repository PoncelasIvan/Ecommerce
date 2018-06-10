package com.inso.Ecommerce.beans;

public class CustomerDataBean {
	private String name;
	private String email;
	
	public CustomerDataBean(){}
	public CustomerDataBean(String name, String email){
		this.name = name;
		this.email = email;
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

}
