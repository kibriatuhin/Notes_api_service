package com.notes_api_service.service.service_Impl;

import com.notes_api_service.dto.CategoryDto;
import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.entity.Category;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import com.notes_api_service.repository.CategoryRepository;
import com.notes_api_service.service.CategoryService;
import com.notes_api_service.utils.Validation;
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
    @Autowired
    Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        //Validation
        validation.categoryValidation(categoryDto);

        Category category = modelMapper.map(categoryDto, Category.class);
        if(ObjectUtils.isEmpty(category.getId())){
            category.setIsDeleted(false);
           /* category.setCreatedBy(1);*/
            category.setCreatedOn(new Date());
        }else {
            updateCategory(category);
        }
         Category category1 =  categoryRepository.save(category);;
        return !ObjectUtils.isEmpty(category1);
    }
    private void updateCategory(Category category) {
        categoryRepository.findById(category.getId()).ifPresent(existingCategory -> {
            category.setIsDeleted(false);
            /*category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());*/
            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setCreatedOn(existingCategory.getCreatedOn());
        });
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {
      //  String test = null;
      //  test.toUpperCase();

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
    public CategoryDto getCatagoryById(Integer id) throws Exception {

        return categoryRepository.findByIdAndIsDeletedFalse(id)
                .map(value ->  modelMapper.map(value, CategoryDto.class))
                .orElseThrow(()->new ResourceNotFoundException("Category not found "+ id));
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
