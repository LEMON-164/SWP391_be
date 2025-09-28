package com.lemon.supershop.swp391fa25evdm.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.category.model.dto.CategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.CategoryRes;
import com.lemon.supershop.swp391fa25evdm.category.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping ("/listCategories")
    public ResponseEntity<List<CategoryRes>> getAllCategories() {
        List<CategoryRes> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<CategoryRes>> getAllCategoriesByName(@PathVariable String name) {
        List<CategoryRes> categories = categoryService.getCategoriesByName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<CategoryRes> getCategoryById(@PathVariable Integer id) {
        CategoryRes category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping ("/create")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryReq categoryReq) {
        categoryService.createCategory(categoryReq);
        return ResponseEntity.ok("Category created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryReq categoryReq) {
        categoryService.updateCategory(id, categoryReq);
        return ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @GetMapping("/search/special")
    public ResponseEntity<List<CategoryRes>> getSpecialCategories() {
        List<CategoryRes> categories = categoryService.getSpecialCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search/active")
    public ResponseEntity<List<CategoryRes>> getActiveCategories() {
        List<CategoryRes> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search/warranty/{year}")
    public ResponseEntity<List<CategoryRes>> getCategoriesWithWarrantyGreaterThan(@PathVariable Integer year) {
        List<CategoryRes> categories = categoryService.getCategoriesWithWarrantyGreaterThan(year);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search/brand/{brand}")
    public ResponseEntity<List<CategoryRes>> getCategoriesByBrand(@PathVariable String brand) {
        List<CategoryRes> categories = categoryService.getCategoriesByBrand(brand);
        return ResponseEntity.ok(categories);
    }
}
