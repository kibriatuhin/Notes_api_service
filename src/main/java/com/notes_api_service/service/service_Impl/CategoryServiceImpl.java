package com.notes_api_service.service.service_Impl;

import com.notes_api_service.dto.CategoryDto;
import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.entity.Category;
import com.notes_api_service.repository.CategoryRepository;
import com.notes_api_service.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {


        Category category = modelMapper.map(categoryDto, Category.class);
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());

        Category savedCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(category ->
                        modelMapper.map(category, CategoryResponseDto.class))
                .toList();
    }

    @Override
    public List<CategoryResponseDto> getActiveCategory() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(category ->
                        modelMapper.map(category, CategoryResponseDto.class))
                .filter(categoryResponseDto -> !categoryResponseDto.getIsActive())
                .toList();
    }
}
