package com.lemon.supershop.swp391fa25evdm.category.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.Repository.CategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.Repository.DealerCategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryRes;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;

@Service
public class DealerCategoryService {
    @Autowired
    private DealerCategoryRepository dealerCategoryRepository;

    @Autowired
    private DealerRepo dealerRepo;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<DealerCategoryRes> getAllDealerCategories() {
        List<DealerCategory> dealerCategories = dealerCategoryRepository.findAll();
        return dealerCategories.stream().map(this::convertToRes).toList();
    }

    public DealerCategoryRes getDealerCategoryById(String id) {
        return dealerCategoryRepository.findById(id)
                .map(this::convertToRes)
                .orElse(null);
    }

    public void createDealerCategory(DealerCategoryReq dto) {
        DealerCategory dealerCategory = new DealerCategory();
        dealerCategory.setId(dto.getId());
        dealerCategory.setName(dto.getName());
        dealerCategory.setQuantity(dto.getQuantity());
        dealerCategory.setDescription(dto.getDescription());
        dealerCategory.setStatus(dto.getStatus());
        dealerCategory.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
    }

    public void deleteDealerCategory(String id) {
        if (dealerCategoryRepository.existsById(id) && id != null){
            dealerCategoryRepository.deleteById(id);
        }
    }

    public void updateDealerCategory(String id, DealerCategoryReq dto) throws Exception {
        DealerCategory existingDealerCategory = dealerCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("DealerCategory not found with id: " + id));
        if (dto.getName() != null) {
            existingDealerCategory.setName(dto.getName());
        }
        if (dto.getQuantity() != 0) {
            existingDealerCategory.setQuantity(dto.getQuantity());
        }
        if (dto.getDescription() != null) {
            existingDealerCategory.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            existingDealerCategory.setStatus(dto.getStatus());
        }
        if (dto.getCategoryId() != 0) {
            existingDealerCategory.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        }
        if (dto.getDealerId() != 0) {
            existingDealerCategory.setDealer(dealerRepo.findById(dto.getDealerId()).orElse(null));
        }
        dealerCategoryRepository.save(existingDealerCategory);
    }

    private DealerCategoryRes convertToRes (DealerCategory dealerCategory) {
        if (dealerCategory == null) {
            return null;
        }
        Integer categoryId = dealerCategory.getCategory() != null ? 
            dealerCategory.getCategory().getId() : 0;
        
        Integer dealerId = dealerCategory.getDealer() != null ? 
            dealerCategory.getDealer().getId() : 0;
        
        return new DealerCategoryRes(
            dealerCategory.getId(),
            dealerCategory.getName(),
            dealerCategory.getQuantity(),
            dealerCategory.getDescription(),
            dealerCategory.getStatus(),
            categoryId, 
            dealerId     
        );
    }
}
