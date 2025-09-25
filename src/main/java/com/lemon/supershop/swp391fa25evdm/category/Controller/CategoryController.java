package com.lemon.supershop.swp391fa25evdm.category.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.category.Service.CategoryService;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.CategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.CategoryRes;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping ("/all")
    public ResponseEntity<List<CategoryRes>> getAllCategories() {
        try {
            List<CategoryRes> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryRes>> getAllCategoriesByName(@RequestParam String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            List<CategoryRes> categories = categoryService.getAllCategoriesByName(name.trim());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryRes> getCategoryById(@PathVariable Integer id) {
        try {
            CategoryRes category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryRes> getCategoryByName(@PathVariable String name) {
        try {
            CategoryRes category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(category);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<CategoryRes> createCategory(@Valid @RequestBody CategoryReq categoryReq) {
        try {
            CategoryRes createdCategory = categoryService.createCategory(categoryReq);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryRes> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryReq categoryReq) {
        try {
            CategoryRes updatedCategory = categoryService.updateCategory(id, categoryReq);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/special")
    public ResponseEntity<List<CategoryRes>> getSpecialCategories() {
        try {
            List<CategoryRes> categories = categoryService.getSpecialCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<CategoryRes>> getActiveCategories() {
        try {
            List<CategoryRes> categories = categoryService.getActiveCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/warranty/{year}")
    public ResponseEntity<List<CategoryRes>> getCategoriesWithWarrantyGreaterThan(@PathVariable Integer year) {
        try {
            List<CategoryRes> categories = categoryService.getCategoriesWithWarrantyGreaterThan(year);
            return ResponseEntity.ok(categories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<CategoryRes>> getCategoriesByBrand(@PathVariable String brand) {
        try {
            List<CategoryRes> categories = categoryService.getCategoriesByBrand(brand);
            return ResponseEntity.ok(categories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CategoryRes>> filterCategories(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String version,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minBattery,
            @RequestParam(required = false) Double maxBattery,
            @RequestParam(required = false) Integer minRange,
            @RequestParam(required = false) Integer maxRange,
            @RequestParam(required = false) Integer minHp,
            @RequestParam(required = false) Integer maxHp,
            @RequestParam(required = false) Integer minTorque,
            @RequestParam(required = false) Integer maxTorque,
            @RequestParam(required = false) Double minBasePrice,
            @RequestParam(required = false) Double maxBasePrice,
            @RequestParam(required = false) Integer minWarranty,
            @RequestParam(required = false) Integer maxWarranty,
            @RequestParam(required = false) Boolean isSpecial,
            @RequestParam(required = false) String status
    ) {
        try {
            List<CategoryRes> categories = categoryService.filterCategories(
                    id, name, brand, version, type,
                    minBattery, maxBattery, minRange, maxRange,
                    minHp, maxHp, minTorque, maxTorque,
                    minBasePrice, maxBasePrice, minWarranty, maxWarranty,
                    isSpecial, status
            );
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
