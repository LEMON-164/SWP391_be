package com.lemon.supershop.swp391fa25evdm.category.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.Repository.CategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.Repository.DealerCategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryRes;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
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
        dealerCategory.setDealer(dealerRepo.findById(dto.getDealerId()).orElse(null));

        DealerCategory savedDealerCategory = dealerCategoryRepository.save(dealerCategory);
        return convertToRes(savedDealerCategory);
    }

    public DealerCategoryRes deleteDealerCategory(String id) {
        DealerCategory dealerCategory = dealerCategoryRepository.findById(id)
                .orElse(null);
        if (dealerCategory != null) {
            dealerCategoryRepository.deleteById(id);
            return convertToRes(dealerCategory);
        }
        return null;
    }

    public DealerCategoryRes updateDealerCategory(String id, DealerCategoryReq dto) throws Exception {
        DealerCategory dealerCategory = dealerCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Dealer Category not found with id: " + id));
        dealerCategory.setName(dto.getName());
        dealerCategory.setQuantity(dto.getQuantity());
        dealerCategory.setDescription(dto.getDescription());
        dealerCategory.setStatus(dto.getStatus());

        if (dto.getCategoryId() > 0) {
            categoryRepository.findById(dto.getCategoryId())
                .ifPresentOrElse(
                    dealerCategory::setCategory,
                    () -> dealerCategory.setCategory(null)
                );
        } else {
            dealerCategory.setCategory(null);
        }
        
        if (dto.getDealerId() > 0) {
            dealerRepo.findById(dto.getDealerId())
                .ifPresentOrElse(
                    dealerCategory::setDealer,
                    () -> dealerCategory.setDealer(null)
                );
        } else {
            dealerCategory.setDealer(null);
        }

        DealerCategory updatedDealerCategory = dealerCategoryRepository.save(dealerCategory);
        return convertToRes(updatedDealerCategory);
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
