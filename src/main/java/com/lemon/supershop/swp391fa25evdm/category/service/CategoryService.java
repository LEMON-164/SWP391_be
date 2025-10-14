package com.lemon.supershop.swp391fa25evdm.category.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lemon.supershop.swp391fa25evdm.category.model.dto.CategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.CategoryRes;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.category.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryRes> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToRes).toList();
    }

    public List<CategoryRes> getCategoryByName(String name) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
        return categories.stream().map(this::convertToRes).toList();
    }

    public CategoryRes getCategoryById(Integer id) {
        if (id != null) {
            Optional<Category> categoryOpt = categoryRepository.findById(id);
            return categoryOpt.map(this::convertToRes).orElse(null);
        }
        return null;
    }

    public CategoryRes createCategory(CategoryReq dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Category data cannot be null");
        }
        if (dto.getName() != null && categoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new RuntimeException("Category with name '" + dto.getName() + "' already exists");
        }
        Category category = convertToEntity(dto);
        categoryRepository.save(category);
        return convertToRes(category);
    }

    public CategoryRes updateCategory(int id, CategoryReq dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Category request cannot be null");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        Optional<Category> nameCheck = categoryRepository.findByNameIgnoreCase(dto.getName());
        if (nameCheck.isPresent() && !Objects.equals(nameCheck.get().getId(), id)) {
            throw new RuntimeException("Category with name '" + dto.getName() + "' already exists");
        }

        category.setName(dto.getName());
        category.setBrand(dto.getBrand());
        category.setVersion(dto.getVersion());
        category.setType(dto.getType());
        category.setBattery(dto.getBattery());
        category.setRange(dto.getRange());
        category.setHp(dto.getHp());
        category.setTorque(dto.getTorque());
        category.setBasePrice(dto.getBasePrice());
        category.setWarranty(dto.getWarranty());
        category.setDescription(dto.getDescription());
        category.setStatus(dto.getStatus());
        
        category.setSpecial(dto.isSpecial());
        Category updatedCategory = categoryRepository.save(category);
        return convertToRes(updatedCategory);
    }

    public boolean deleteCategory(Integer id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            categoryRepository.delete(existingCategory.get());
            return true;
        }
        return false;
    }

    public List<CategoryRes> getSpecialCategories() {
        List<Category> categories = categoryRepository.findByIsSpecialTrue();
        return categories.stream().map(this::convertToRes).toList();
    }

    public List<CategoryRes> getCategoriesWithWarrantyGreaterThan(Integer years) {
        if (years == null || years < 0) {
            throw new IllegalArgumentException("Warranty years must be a positive number");
        }
        List<Category> categories = categoryRepository.findByWarrantyGreaterThan(years);
        return categories.stream().map(this::convertToRes).toList();
    }

    public List<CategoryRes> getCategoriesByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be null or empty");
        }
        List<Category> categories = categoryRepository.findByBrandIgnoreCase(brand.trim());
        return categories.stream().map(this::convertToRes).toList();
    }

    public List<CategoryRes> getActiveCategories() {
        List<Category> categories = categoryRepository.findActiveCategories();
        return categories.stream().map(this::convertToRes).toList();
    }

    // Helper methods for conversion
    private Category convertToEntity(CategoryReq dto) {
        Category category = new Category();

        category.setName(dto.getName());
        category.setBrand(dto.getBrand());
        category.setVersion(dto.getVersion());
        category.setType(dto.getType());
        category.setSpecial(dto.getSpecial());
        category.setBasePrice(dto.getBasePrice());
        category.setWarranty(dto.getWarranty());
        category.setDescription(dto.getDescription());
        category.setStatus(dto.getStatus());
        category.setSpecial(dto.isSpecial());
        return category;
    }

    public CategoryRes updateCategoryBasePrice(Integer id, Double newBasePrice) {
        if (id == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        if (newBasePrice == null || newBasePrice < 0) {
            throw new IllegalArgumentException("Base price must be a positive number");
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existingCategory.setBasePrice(newBasePrice);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToRes(updatedCategory);
    }

    private CategoryRes convertToRes(Category category) {
        if (category != null) {
            CategoryRes categoryRes = new CategoryRes();
            if (category.getId() > 0){
                categoryRes.setId(category.getId());
            }
            if (category.getName() != null) {
                categoryRes.setName(category.getName());
            }
            if (category.getBrand() != null) {
                categoryRes.setBrand(category.getBrand());
            }
            if (category.getVersion() != null) {
                categoryRes.setVersion(category.getVersion());
            }
            if (category.getType() != null) {
                categoryRes.setType(category.getType());
            }
            if (category.getBasePrice() > 0){
                categoryRes.setBasePrice(category.getBasePrice());
            }
            if (category.getWarranty() > 0){
                categoryRes.setWarranty(category.getWarranty());
            }
            if (category.getDescription() != null){
                categoryRes.setDescription(category.getDescription());
            }
            if (category.getStatus() != null){
                categoryRes.setStatus(category.getStatus());
            }
            return categoryRes;
        } else {
            return null;
        }
    }
}
