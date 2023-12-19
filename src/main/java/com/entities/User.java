package com.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;
	
	@NotNull
	@Size(min= 5, max = 30)
	@Column(unique = true)
	private String username;
	
	@NotNull
	@Size(min=8 , max = 30)
	private String password;
	
	
	// Default constructor
	public User() {
		super();
	}
   
	// Parameterized constructor
	public User(String username, String password) {
		
		this.username = username;
		this.password = password;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getID() {
		return id;
	}

	public void setID(long iD) {
		id = iD;
	}


}
