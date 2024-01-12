package com.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.entities.Product;

import jakarta.json.bind.annotation.JsonbNumberFormat;

public class ProfitInvoiceDTO {



	private long customerId;
	private List<Long> productIdsList;
	@JsonbNumberFormat("yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
	
	public long getCustomerId() {
		return customerId;
	}

	public List<Long> getProductIdsList() {
		return productIdsList;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public void setProductIdsList(List<Long> productIdsList) {
		this.productIdsList = productIdsList;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
