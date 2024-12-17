package com.notes_api_service.utils;

import com.notes_api_service.dto.CategoryDto;
import com.notes_api_service.exception.customException.DtoValidationException;
import org.modelmapper.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {
    public void categoryValidation(CategoryDto category) {
        Map<String,Object> errors = new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(category)){
            throw new IllegalArgumentException("Category objects/JSON should not be null/empty");
        }
        //validation  field
        validateField("name", category.getName(), 10, 100, errors);
        validateField("description", category.getDescription(), 10, 100, errors);
        validateBooleanField(category.getIsActive(), errors);

            /*if (ObjectUtils.isEmpty(category.getName())){
                errors.put("name","Name should not be empty");
            }else {
                if (category.getName().length() <10){
                    errors.put("name","Name should contain at least 10 characters");
                }
                if (category.getName().length() >100){
                    errors.put("name","Name should contain at most 100 characters");
                }
            }

            //validation description field
            if (ObjectUtils.isEmpty(category.getDescription())){
                errors.put("description","Description should not be empty");
            }else {
                if (category.getDescription().length() <10){
                    errors.put("description","Description should contain at least 10 characters");
                }
                if (category.getDescription().length() >100){
                    errors.put("description","Description should contain at most 100 characters");
                }
            }

            //isActive field
            if (ObjectUtils.isEmpty(category.getIsActive())){
                errors.put("isActive","Active field should not be empty");
            }*/

        if (!errors.isEmpty()){
            throw new DtoValidationException(errors);
        }
    }

    private void validateField(String fieldName, String fieldValue, int minLength, int maxLength, Map<String, Object> errors) {
        if (ObjectUtils.isEmpty(fieldValue)) {
            errors.put(fieldName, String.format("%s should not be empty", capitalize(fieldName)));
        } else {
            if (fieldValue.length() < minLength) {
                errors.put(fieldName, String.format("%s should contain at least %d characters", capitalize(fieldName), minLength));
            }
            if (fieldValue.length() > maxLength) {
                errors.put(fieldName, String.format("%s should contain at most %d characters", capitalize(fieldName), maxLength));
            }
        }
    }

    private void validateBooleanField(Boolean isActive, Map<String, Object> errors) {
        if (isActive == null) {
            errors.put("isActive", "Active field should not be null");
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
