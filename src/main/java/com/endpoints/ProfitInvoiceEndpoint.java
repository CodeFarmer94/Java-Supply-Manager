package com.endpoints;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.DTO.ProfitInvoiceDTO;
import com.entities.Component;
import com.entities.Customer;
import com.entities.Product;
import com.entities.ProfitInvoice;
import com.services.GenericEntityService;
import com.services.ProfitInvoiceService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/profit-invoice")
public class ProfitInvoiceEndpoint {

	
	@Inject
	private ProfitInvoiceService profitInvoiceService;
	@Inject
	private GenericEntityService<Product> productService;
	@Inject
	private GenericEntityService<Customer> customerService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postProfitInvoices(List<ProfitInvoiceDTO> profitInvoiceDTOList) {
		
		
		
		profitInvoiceDTOList.forEach(invoiceDTO -> {
			
			Set<Product> invoiceProductSet = new HashSet<>();
			invoiceDTO.getProductIdsList().forEach(id -> {
				Product foundProduct = productService.findById(Product.class, id);
				invoiceProductSet.add(foundProduct);
			});

			Customer foundCustomer = customerService.findById(Customer.class, invoiceDTO.getCustomerId());
			LocalDateTime createdAt = invoiceDTO.getDateTime();
			profitInvoiceService.save(new ProfitInvoice(invoiceProductSet, foundCustomer, createdAt));
		});
	
		
		return Response.ok().build();
	}
	
}
