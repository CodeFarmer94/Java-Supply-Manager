package com.validators;

import java.util.Set;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Stateless
public class ObjValidator {

    @Inject
    private Validator validator;

    /* Validates any T object using constraint declared in constructor */

    public <T> void validateObj(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Validation failed for ")
                         .append(obj.getClass().getSimpleName())
                         .append(": ")
                         .append(violations.size())
                         .append(" violations found.");

     
            System.out.println(errorMessage.toString());

            // Log each specific validation error
            for (ConstraintViolation<T> violation : violations) {
                StringBuilder violationMessage = new StringBuilder();
                violationMessage.append("Property '")
                                .append(violation.getPropertyPath())
                                .append("' with value '")
                                .append(violation.getInvalidValue())
                                .append("': ")
                                .append(violation.getMessage());

               
                System.out.println(violationMessage.toString());
            }

            // Throw a ConstraintViolationException with the detailed information
            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
    }
}
