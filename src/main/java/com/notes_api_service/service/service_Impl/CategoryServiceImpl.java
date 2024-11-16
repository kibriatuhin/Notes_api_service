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

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        return categoryRepository.findByIsDeletedFalse().stream()
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
                .filter(CategoryResponseDto::getIsActive)
                .toList();
    }

    @Override
    public CategoryDto getCatagoryById(Integer id) {

        return categoryRepository.findByIdAndIsDeletedFalse(id)
                .map(value ->  modelMapper.map(value, CategoryDto.class))
                .orElse(null);
    }

    @Override
    public String deleteCategoryById(Integer id) {

       return categoryRepository.findById(id).map(category -> {
            if (category.getIsDeleted()) {
                return "D"; // Already deleted
            }
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return "S"; // Successfully deleted
        }).orElse("F"); // Not found

    }
}
