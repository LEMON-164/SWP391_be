package com.lemon.supershop.swp391fa25evdm.category.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lemon.supershop.swp391fa25evdm.category.Repository.CategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.CategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.CategoryRes;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;



@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryRes> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToRes).toList();
    }

    public List<CategoryRes> getAllCategoriesByName(String name) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
        return categories.stream().map(this::convertToRes).toList();
    }

    public CategoryRes getCategoryById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        return categoryRepository.findById(id)
                .map(this::convertToRes)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public CategoryRes getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        return categoryRepository.findByNameIgnoreCase(name.trim())
                .map(this::convertToRes)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + name));
    }

    public CategoryRes createCategory(CategoryReq dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Category request cannot be null");
        }

        // Check if category with same name already exists
        Optional<Category> existing = categoryRepository.findByNameIgnoreCase(dto.getName());
        if (existing.isPresent()) {
            throw new RuntimeException("Category with name '" + dto.getName() + "' already exists");
        }

        Category category = convertToEntity(dto);
        Category savedCategory = categoryRepository.save(category);
        return convertToRes(savedCategory);
    }

    public CategoryRes updateCategory(int id, CategoryReq dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Category request cannot be null");
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));


        Optional<Category> nameCheck = categoryRepository.findByNameIgnoreCase(dto.getName());
        if (nameCheck.isPresent() && !Objects.equals(nameCheck.get().getId(), id)) {
            throw new RuntimeException("Category with name '" + dto.getName() + "' already exists");
        }

        updateEntityFromDto(existingCategory, dto);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToRes(updatedCategory);
    }

    public CategoryRes deleteCategory (Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryRepository.delete(existingCategory);
        return convertToRes(existingCategory);
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

    public List<CategoryRes> filterCategories(Integer id, String name, String brand, String version, String type,
                                            Double minBattery, Double maxBattery,
                                            Integer minRange, Integer maxRange,
                                            Integer minHp, Integer maxHp,
                                            Integer minTorque, Integer maxTorque,
                                            Double minBasePrice, Double maxBasePrice,
                                            Integer minWarranty, Integer maxWarranty,
                                            Boolean isSpecial, String status) {

        List<Category> categories = categoryRepository.filterCategories(
                id, name, brand, version, type,
                minBattery, maxBattery, minRange, maxRange,
                minHp, maxHp, minTorque, maxTorque,
                minBasePrice, maxBasePrice, minWarranty, maxWarranty,
                isSpecial, status
        );
        return categories.stream().map(this::convertToRes).toList();
    }

    // Helper methods for conversion
    private Category convertToEntity(CategoryReq dto) {
        Category category = new Category();
        updateEntityFromDto(category, dto);
        return category;
    }

    private void updateEntityFromDto(Category category, CategoryReq dto) {
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
        category.setSpecial(dto.getSpecial() != null ? dto.getSpecial() : Boolean.FALSE);
        category.setDescription(dto.getDescription());
        category.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
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
            if (category.getBattery() > 0){
                categoryRes.setBattery(category.getBattery());
            }
            if (category.getRange() > 0){
                categoryRes.setRange(category.getRange());
            }
            if (category.getHp() > 0){
                categoryRes.setHp(category.getHp());
            }
            if (category.getTorque() > 0){
                categoryRes.setTorque(category.getTorque());
            }
            if (category.getBasePrice() > 0){
                categoryRes.setBasePrice(category.getBasePrice());
            }
            if (category.getWarranty() > 0){
                categoryRes.setWarranty(category.getWarranty());
            }
            if (category.isSpecial()){
                categoryRes.setSpecial(true);
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
