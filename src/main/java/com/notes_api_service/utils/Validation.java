package com.notes_api_service.utils;

import com.notes_api_service.dto.CategoryDto;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.dto.TodoDto;
import com.notes_api_service.dto.UserRequestDto;
import com.notes_api_service.entity.Category;
import com.notes_api_service.entity.Role;
import com.notes_api_service.enums.TodoStatus;
import com.notes_api_service.exception.customException.DtoValidationException;
import com.notes_api_service.exception.customException.ExistDataException;
import com.notes_api_service.repository.CategoryRepository;
import com.notes_api_service.repository.RoleRepository;
import com.notes_api_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class Validation {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    public void categoryValidation(CategoryDto category) {
        Map<String,Object> errors = new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(category)){
            throw new IllegalArgumentException("Category objects/JSON should not be null/empty");
        }
        //validation  field
        validateField("name", category.getName(), 3, 100, errors);
        validateField("description", category.getDescription(), 3, 100, errors);
        validateBooleanField(category.getIsActive(), errors);

        if (!errors.isEmpty()){
            throw new DtoValidationException(errors);
        }
    }

    public void notesValidation(NotesDto notesDto){
        Map<String,Object> errors = new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(notesDto)){
            throw new IllegalArgumentException("Notes objects/JSON should not be null/empty");
        }
        validateField("title", notesDto.getTitle(), 4, 100, errors);
        validateField("description", notesDto.getDescription(), 5, 200, errors);
        checkNotesCategory("category",notesDto.getCategory().getId(),errors);

        if (!errors.isEmpty()){
            throw new DtoValidationException(errors);
        }
    }

    private  void checkNotesCategory(String fieldName,Integer id,Map<String, Object> errors){
        Optional<Category> category =  categoryRepository.findById(id);
        if (category.isEmpty()){
            errors.put(fieldName,String.format(" Category ID  %s invalid ", id));
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

    public void todoValidation(TodoDto todoDto) throws Exception{
        TodoDto.StatusDto reqStatus = todoDto.getStatus();

        Boolean statusFound = false;
        for (TodoStatus st : TodoStatus.values()){
            if (st.getId().equals(reqStatus.getId())) {
                statusFound = true;
               // break;
            }
        }
        if (!statusFound){
            throw new IllegalArgumentException("Invalid status " + reqStatus);
        }
    }


    //User Validation
    public void userValidation(UserRequestDto userRequestDto){

        if (!StringUtils.hasText(userRequestDto.getFirstName())){
            throw new IllegalArgumentException("First name is invalid");
        }

        if (!StringUtils.hasText(userRequestDto.getLastName())){
            throw new IllegalArgumentException("Last name is invalid");
        }

        if (!StringUtils.hasText(userRequestDto.getEmail())
                || !userRequestDto.getEmail().matches(Constants.EMAIL_REGEX)){
            throw new IllegalArgumentException("Email address is invalid");

        }else {
            Boolean existEmail = userRepository.existsByEmail(userRequestDto.getEmail());
            if (existEmail){
                throw  new ExistDataException("Email address already exist");
            }
        }
        if (!StringUtils.hasText(userRequestDto.getPhoneNo())
                || !userRequestDto.getPhoneNo().matches(Constants.MOBILE_REGEX)){
            throw new IllegalArgumentException("Phone number is invalid");
        }



        if (CollectionUtils.isEmpty(userRequestDto.getRoles())){
            throw new IllegalArgumentException("Roles are invalid");
        }else {
            List<Integer> rolesId = roleRepository.findAll().stream().map(Role::getId).toList();
            List<Integer> invalidReqRoleIds =  userRequestDto.getRoles().stream()
                    .map(UserRequestDto.RoleDto::getId)
                    .filter(rid->!rolesId.contains(rid)).toList();
            if (!CollectionUtils.isEmpty(invalidReqRoleIds)){
                throw new IllegalArgumentException("Roles are invalid"+ invalidReqRoleIds);
            }
        }



    }
}
