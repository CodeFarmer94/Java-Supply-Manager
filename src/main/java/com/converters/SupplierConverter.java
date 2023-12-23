package com.converters;

import com.beans.transacation_party_beans.SupplierBean;
import com.entities.Component;
import com.entities.Supplier;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;

@FacesConverter(value = "supplierConverter")
@Named
public class SupplierConverter implements Converter<Object> {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
        System.out.println("Converting to Object");
        try {
            SupplierBean supplierBean = fc.getApplication().evaluateExpressionGet(fc, "#{supplierBean}", SupplierBean.class);

            Long id = Long.valueOf(value);

            return supplierBean.getEntityService().findById(Supplier.class, id);
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
            return String.valueOf(((Supplier) value).getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
