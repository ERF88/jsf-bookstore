package com.github.erf88.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	private String email;
	private String password;

	public Integer getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String name) {
		this.password = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
