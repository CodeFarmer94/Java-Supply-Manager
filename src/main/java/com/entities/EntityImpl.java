package com.entities;

import java.io.Serializable;


import com.interfaces.EntityInterface;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Entity implementation class for Entity: EntityImpl
 *
 */
@MappedSuperclass

public class EntityImpl implements Serializable, EntityInterface {

	
	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}
   
}
