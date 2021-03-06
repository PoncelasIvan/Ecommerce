package com.inso.Ecommerce.beans;

import org.apache.commons.codec.digest.DigestUtils;

public class LoginBean {
	private String user;
	private String password;
	
	public LoginBean() {}
	public LoginBean(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = DigestUtils.sha256Hex(password);
	}
	
	
}
