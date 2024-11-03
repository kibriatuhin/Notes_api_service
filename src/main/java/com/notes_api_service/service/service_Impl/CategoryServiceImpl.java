package com.notes_api_service.service.service_Impl;

import com.notes_api_service.entity.Category;
import com.notes_api_service.repository.CategoryRepository;
import com.notes_api_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Boolean saveCategory(Category category) {
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category savedCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(savedCategory);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
