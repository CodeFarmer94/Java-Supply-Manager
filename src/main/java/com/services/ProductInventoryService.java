package com.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.entities.Product;
import com.entities.ProductInventory;
import com.events.ProduceProductEvent;
import com.interfaces.InventoryService;

import com.interfaces.ItemInterface;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.qualifiers.ProductInventoryServiceQualifier;
import com.qualifiers.SellProductEvent;

@ProductInventoryServiceQualifier
@Stateless
public class ProductInventoryService extends InventoryService<Product, ProductInventory> {

	
	public void listenProduceProduct(@Observes  ProduceProductEvent event) {

		Product product = event.getProduct();
		int quantity = event.getQuantity();

		consumeItems(product, quantity);
	
	}
	
	public void addItems(@Observes @SellProductEvent Map<Product, Integer> quantityMap) {

		quantityMap.entrySet().forEach(entry -> {
			addItems(entry.getKey(), entry.getValue());
		});

	}

	public void addItems(Product product, int quantity) {

		List<ProductInventory> inStockList = findAll(ProductInventory.class);

		Optional<ProductInventory> found = inStockList.stream().filter(e -> e.getName().equals(product.getName()))
				.findFirst();

		if (found.isEmpty()) {
			save(new ProductInventory(product, quantity));
		} else {
			ProductInventory productInventory = found.get();
			productInventory.setQuantity(productInventory.getQuantity() + quantity);
			update(productInventory);
		}
	}

	public void consumeItems(Product product, int quantity) {

		List<ProductInventory> inStockList = findAll(ProductInventory.class);
		Optional<ProductInventory> found = inStockList.stream().filter(e -> e.getName().equals(product.getName()))
				.findFirst();

		if (found.isEmpty()) {
			throw new IllegalStateException("Product not in stock");
		} else {
			ProductInventory productInventory = found.get();
			if (productInventory.getQuantity() < quantity) {
				throw new IllegalStateException("Product quantity in stock insufficient");
			}
			productInventory.setQuantity(productInventory.getQuantity() - quantity);
			update(productInventory);
		}

	}

}