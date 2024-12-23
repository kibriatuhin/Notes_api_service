package com.notes_api_service.controller;

import com.notes_api_service.dto.CategoryDto;
import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.entity.Category;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import com.notes_api_service.service.CategoryService;
import com.notes_api_service.utils.CommonUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {

       return categoryService.saveCategory(categoryDto)
                ? CommonUtil.createBuildResponseMessage("Saved Success", HttpStatus.CREATED)
                : CommonUtil.createErrorResponseMessage("Category Not  Saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryResponseDto> categoryList = categoryService.getAllCategory();
        return CollectionUtils.isEmpty(categoryList)
                ? ResponseEntity.noContent().build()
                : CommonUtil.createBuildResponse(categoryList, HttpStatus.OK);

    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponseDto> categoryList = categoryService.getActiveCategory();
        return CollectionUtils.isEmpty(categoryList)
                ? CommonUtil.createErrorResponseMessage("Category Not  Found", HttpStatus.NOT_FOUND)
                : CommonUtil.createBuildResponse(categoryList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception {
        CategoryDto categoryDto = categoryService.getCatagoryById(id);

        return ObjectUtils.isEmpty(categoryDto)
                ? CommonUtil.createErrorResponseMessage("Internal Server Error ", HttpStatus.NOT_FOUND)
                : CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        String categoryDeleted = categoryService.deleteCategoryById(id);
        if (categoryDeleted.contains("S")){
            return CommonUtil.createBuildResponseMessage("Deleted Successfully", HttpStatus.OK);
        } else if (categoryDeleted.contains("D")) {
            return CommonUtil.createErrorResponseMessage("Your data already Deleted = " + id, HttpStatus.BAD_REQUEST);
        }else{
            return CommonUtil.createErrorResponseMessage("No data found = " +id , HttpStatus.NOT_FOUND);
        }
    }
}
