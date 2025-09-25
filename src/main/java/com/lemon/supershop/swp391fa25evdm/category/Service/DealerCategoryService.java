package com.lemon.supershop.swp391fa25evdm.category.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.Repository.DealerCategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryRes;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;

@Service
public class DealerCategoryService {
    @Autowired
    private DealerCategoryRepository dealerCategoryRepository;

    public List<DealerCategoryRes> getAllDealerCategories() {
        List<DealerCategory> dealerCategories = dealerCategoryRepository.findAll();
        return dealerCategories.stream().map(this::convertToRes).toList();
    }

    public DealerCategoryRes getDealerCategoryById(int id) {
        return dealerCategoryRepository.findById(id)
                .map(this::convertToRes)
                .orElse(null);
    }

    public DealerCategoryRes createDealerCategory(DealerCategoryReq dto) {
        DealerCategory dealerCategory = new DealerCategory();
        dealerCategory.setId(dto.getId());
        dealerCategory.setName(dto.getName());
        dealerCategory.setQuantity(dto.getQuantity());
        dealerCategory.setDescription(dto.getDescription());
        dealerCategory.setStatus(dto.getStatus());
        
        // Set Category reference using EntityManager if categoryId is provided
        if (dto.getCategoryId() > 0) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            dealerCategory.setCategory(category);
        }
        
        DealerCategory savedDealerCategory = dealerCategoryRepository.save(dealerCategory);
        return convertToRes(savedDealerCategory);
    }

    public void deleteDealerCategory(int id) {
        dealerCategoryRepository.deleteById(id);
    }

    public void updateDealerCategory(int id, DealerCategoryReq dto) throws Exception {
        DealerCategory dealerCategory = dealerCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Dealer Category not found with id: " + id));
        dealerCategory.setName(dto.getName());
        dealerCategory.setQuantity(dto.getQuantity());
        dealerCategory.setDescription(dto.getDescription());
        dealerCategory.setStatus(dto.getStatus());
        dealerCategoryRepository.save(dealerCategory);
    }

    private DealerCategoryRes convertToRes (DealerCategory dealerCategory) {
        return new DealerCategoryRes(
                dealerCategory.getId(),
                dealerCategory.getName(),
                dealerCategory.getQuantity(),
                dealerCategory.getDescription(),
                dealerCategory.getStatus(),
                dealerCategory.getCategory().getId()
        );
    }
}
