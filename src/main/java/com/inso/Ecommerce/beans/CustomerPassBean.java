package com.inso.Ecommerce.beans;

public class CustomerPassBean {
	private String password;

	public CustomerPassBean(){}
	
	public CustomerPassBean(String pass){
		this.password = pass;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
