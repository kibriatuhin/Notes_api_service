package com.notes_api_service.controller;

import com.notes_api_service.dto.CategoryDto;
import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.entity.Category;
import com.notes_api_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {

       return categoryService.saveCategory(categoryDto)
                ? new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED)
                : new ResponseEntity<>("Save Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryResponseDto> categoryList = categoryService.getAllCategory();
        return CollectionUtils.isEmpty(categoryList)
                ? new ResponseEntity<>("No Category Found", HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(categoryList, HttpStatus.OK);

    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponseDto> categoryList = categoryService.getActiveCategory();
        return CollectionUtils.isEmpty(categoryList)
                ? new ResponseEntity<>("No Category Found", HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(categoryList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {

         CategoryDto categoryDto = categoryService.getCatagoryById(id);

         return ObjectUtils.isEmpty(categoryDto)
                 ? new ResponseEntity<>("No Category Found = " + id, HttpStatus.NOT_FOUND)
                 : new ResponseEntity<>(categoryDto, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        String categoryDeleted = categoryService.deleteCategoryById(id);
        if (categoryDeleted.contains("S")){
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } else if (categoryDeleted.contains("D")) {
            return new ResponseEntity<>("Your data already Deleted = " + id, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>("No data found = " +id , HttpStatus.NOT_FOUND);
        }
    }
}
