package com.endpoints;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.entities.Component;
import com.entities.Product;
import com.entities.ProductComponent;
import com.services.GenericEntityService;

import com.DTO.ProductDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;



@Path("/products")
public class ProductEndpoint {

	
	@Inject
	private GenericEntityService<Product> productService;
	
	@Inject
	private GenericEntityService<Product> productInventoryService;
	@Inject
	private GenericEntityService<ProductComponent> productComponentService;
	@Inject
	private GenericEntityService<Component> componentService;

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts() {
	    List<Product> productList = productService.findAll(Product.class);

	    if (productList.isEmpty()) {
	        return Response.status(Response.Status.NO_CONTENT).build();
	    }

	    List<Product> copyList = productList.stream()
	            .map(e -> new Product(e.getName(), e.getPrice(), e.getDescription()))
	            .collect(Collectors.toList());

	    return Response.ok(copyList).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postProduct(ProductDTO productDTO, @Context UriInfo uriInfo) {
		Product newProduct = new Product(productDTO.getName(), productDTO.getPrice(), productDTO.getDescription());
		productService.save(newProduct);
		Optional<Product> savedProductOptional = productService.findByValue(Product.class, "name", newProduct.getName())
				.stream()
				.findFirst();
		
		if(savedProductOptional.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		Product savedProduct = savedProductOptional.get();
		productDTO.getComponentsIds().forEach(e -> {
			Component component = componentService.findById(Component.class, e);
			productComponentService.save(new ProductComponent(savedProduct, component, 1));
        });
        		
		return Response.ok().build();
	}
	
}
