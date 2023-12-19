package com.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address  implements Serializable{
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
	private String street;
    private String city;
    private String zipCode;
    private String country;
    
    
    public Address(String street, String city, String zipCode, String country) {
	
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
	}
    
    public Address () {
    	
    }
    
    @Override
    public String toString() {
        // Customize the format as needed
        return String.format("%s, %s, %s, %s", street, city, zipCode, country);
    }
    /* ------ Getters and Setters  ------ */
    
    
    public Long getId() {
		return id;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
