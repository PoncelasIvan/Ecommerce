package com.inso.Ecommerce.beans;

public class PasswordBean {
	private String oldPassword;
	private String newPassword;

	public PasswordBean(){}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
