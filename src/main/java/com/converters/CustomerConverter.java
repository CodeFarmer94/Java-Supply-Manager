package com.converters;

import com.beans.transacation_party_beans.CustomerBean;
import com.beans.transacation_party_beans.SupplierBean;
import com.entities.Component;
import com.entities.Customer;
import com.entities.Supplier;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;

@FacesConverter(value = "customerConverter")
@Named
public class CustomerConverter implements Converter<Object> {

    @Override
    public Customer getAsObject(FacesContext fc, UIComponent comp, String value) {
        System.out.println("Converting to Object");
        try {
        	CustomerBean customerBean = fc.getApplication().evaluateExpressionGet(fc, "#{customerBean}", CustomerBean.class);
        	System.out.println("Customer Bean: " + value);
            Long id = Long.valueOf(value);
            Customer customer = customerBean.getEntityService().findById(Customer.class, id);
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent comp, Object value) {
    	if(value == "") {
    		return null;
    	}
        System.out.println("Converting to String");
        try {
            return String.valueOf(((Customer) value).getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
