package com.notes_api_service.endpoint;

import com.notes_api_service.dto.CategoryDto;
import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.utils.CommonUtil;
import com.notes_api_service.utils.Constants;
import static com.notes_api_service.utils.Constants.ROLE_ADMIN;
import static com.notes_api_service.utils.Constants.ROLE_ADMIN_USER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/v1/category")
@Tag(name = "Category APIs", description = "All the category operations APIs")
public interface CategoryControllerEndpoint {


    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "Save Category endpoint",tags = {"Category APIs"})
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) ;


    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "get all category endpoint",tags = {"Category APIs"})
    public ResponseEntity<?> getAllCategory() ;


    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    @Operation(summary = "get all active category endpoint",tags = {"Category APIs","User APIs"})
    public ResponseEntity<?> getActiveCategory() ;


    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    @Operation(summary = "get category by  id endpoint",tags = {"Category APIs","User APIs"})
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception ;


    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "delete category endpoint",tags = {"Category APIs"})
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) ;
}
