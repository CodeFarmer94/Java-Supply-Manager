package com.services;

import java.io.Serializable;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.entities.ProfitInvoice;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class ProfitInvoiceService extends GenericEntityService<ProfitInvoice> implements Serializable{

	
	private static final long serialVersionUID = 1L;

	public Map<Month, List<ProfitInvoice>> getInvoicesPerMonth() {
		
		List<ProfitInvoice>profitInvoiceList = this.findAllSorted(ProfitInvoice.class, "createdAt", "DESC");
		return profitInvoiceList.stream().collect(Collectors.groupingBy( e -> e.getCreatedAt().getMonth()));
	}
	
	public Map<Month, List<ProfitInvoice>> getInvoicesPerQuarter(){
		
		List<ProfitInvoice>profitInvoiceList = this.findAllSorted(ProfitInvoice.class, "createdAt", "DESC");
		return profitInvoiceList.stream().collect(Collectors.groupingBy( e -> e.getCreatedAt().getMonth().firstMonthOfQuarter()));
	}
	
	
	public List<ProfitInvoice> getInvoicesPerYear(int year) {

		List<ProfitInvoice>profitInvoiceList = this.findAllSorted(ProfitInvoice.class, "createdAt", "DESC");
        return profitInvoiceList.stream().filter(e -> e.getCreatedAt().getYear() == year).collect(Collectors.toList());
    }
}

