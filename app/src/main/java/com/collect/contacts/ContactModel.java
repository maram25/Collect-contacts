package com.collect.contacts;

import com.google.gson.annotations.SerializedName;

public class ContactModel {
	@SerializedName("Name")
	private String Name;
	@SerializedName("phone")
	private String  phone;
	@SerializedName("Email")
	private String  Email;


	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
}
