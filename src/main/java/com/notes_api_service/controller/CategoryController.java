package com.notes_api_service.controller;

import com.notes_api_service.entity.Category;
import com.notes_api_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {

       return categoryService.saveCategory(category)
                ? new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED)
                : new ResponseEntity<>("Save Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-category")
    public ResponseEntity<?> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategory();
        return CollectionUtils.isEmpty(categoryList)
                ? new ResponseEntity<>("No Category Found", HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(categoryList, HttpStatus.OK);

    }
}
