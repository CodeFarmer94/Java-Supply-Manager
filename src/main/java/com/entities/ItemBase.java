package com.entities;

import java.io.Serializable;
import java.util.UUID;

import org.apache.openjpa.persistence.jdbc.Unique;

import com.interfaces.ItemInterface;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class ItemBase extends EntityImpl implements ItemInterface{
	
	
	
	private static final long serialVersionUID = 1L;

	/* Provides basic implementation for items */ 
	
  

    @Size(min = 4, max = 255)
    @NotNull
    @Unique
    protected String name;

    @NotNull
    @Positive
    protected double price;
    
    protected String description;
    
    @NotNull
	protected  String code;
	
    public ItemBase() {
        // Default constructor
    }

    public ItemBase(String name, double price,  String description) {
        this.name = name;
        this.price = price;
      
        this.description = description;
        this.code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9);
    }

    
    
    
    // Getters and setters for common fields

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

