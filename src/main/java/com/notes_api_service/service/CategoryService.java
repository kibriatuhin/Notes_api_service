package com.notes_api_service.service;

import com.notes_api_service.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(Category category);
    public List<Category> getAllCategory();
}
