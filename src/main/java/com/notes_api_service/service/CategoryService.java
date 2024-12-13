package com.notes_api_service.service;

import com.notes_api_service.dto.CategoryDto;
import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.entity.Category;
import com.notes_api_service.exception.customException.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryResponseDto> getAllCategory();
    public List<CategoryResponseDto> getActiveCategory();

    public CategoryDto getCatagoryById(Integer id) throws Exception;

    public String deleteCategoryById(Integer id);

}
