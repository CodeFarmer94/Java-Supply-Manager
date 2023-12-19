package com.converters;

import com.beans.itemBeans.ComponentBean;
import com.entities.Component;


import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;

@FacesConverter(value = "componentConverter")
@Named
public class ComponentConverter implements Converter<Object> {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
        System.out.println("Converting to Object");
        try {
            ComponentBean componentBean = fc.getApplication().evaluateExpressionGet(fc, "#{componentBean}", ComponentBean.class);

            Long id = Long.valueOf(value);

            return componentBean.getEntityService().findById(Component.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent comp, Object value) {
        System.out.println("Converting to String");
        try {
            return String.valueOf(((Component) value).getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
