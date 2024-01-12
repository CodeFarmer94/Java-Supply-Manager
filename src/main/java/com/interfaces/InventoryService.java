package com.interfaces;

import com.services.GenericEntityService;

public abstract class InventoryService <T extends ItemInterface, R extends ItemInterface> extends GenericEntityService<R> {

	public abstract void addItems( T item, int quantity);
	public abstract void consumeItems( T item, int quantity);
}
