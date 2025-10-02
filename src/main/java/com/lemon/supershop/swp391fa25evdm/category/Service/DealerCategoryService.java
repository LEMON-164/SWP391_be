package com.lemon.supershop.swp391fa25evdm.category.service;

import java.util.List;
import java.util.Optional;

import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryRes;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;
import com.lemon.supershop.swp391fa25evdm.category.repository.CategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.repository.DealerCategoryRepository;
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

    public DealerCategoryRes getDealerCategoryById(int id) {
        return dealerCategoryRepository.findById(id)
                .map(this::convertToRes)
                .orElse(null);
    }

    public void createDealerCategory(DealerCategoryReq dto) {
        DealerCategory dealerCategory = new DealerCategory();
        if (dto.getName() != null){
            dealerCategory.setName(dto.getName());
        }
        if (dto.getQuantity() > 0){
            dealerCategory.setQuantity(dto.getQuantity());
        }
        if (dto.getDescription() != null){
            dealerCategory.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null){
            dealerCategory.setStatus(dto.getStatus());
        }
        if (dto.getCategoryId() > 0) {
            Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
            if (category.isPresent()) {
                dealerCategory.setCategory(category.get());
            }
        }
        if (dto.getDealerId() > 0) {
            Optional<Dealer> dealer = dealerRepo.findById(dto.getDealerId());
            if (dealer.isPresent()) {
                dealerCategory.setDealer(dealer.get());
            }
        }

        dealerCategoryRepository.save(dealerCategory);
    }

    public boolean deleteDealerCategory(int id) {
        Optional<DealerCategory> dealerCategory = dealerCategoryRepository.findById(id);
        if (dealerCategory.isPresent()) {
            dealerCategoryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void updateDealerCategory(int id, DealerCategoryReq dto) throws Exception {
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
        if (dealerCategory != null) {
            DealerCategoryRes dto = new DealerCategoryRes();
            if (dealerCategory.getName() != null){
                dto.setName(dealerCategory.getName());
            }
            if (dealerCategory.getQuantity() >= 0){
                dto.setQuantity(dealerCategory.getQuantity());
            }
            if (dealerCategory.getDescription() != null){
                dto.setDescription(dealerCategory.getDescription());
            }
            if (dealerCategory.getStatus() != null){
                dto.setStatus(dealerCategory.getStatus());
            }
            if (dealerCategory.getCategory() != null){
                dto.setCategoryId(dealerCategory.getCategory().getId());
            }
            if (dealerCategory.getDealer() != null){
                dto.setDealerId(dealerCategory.getDealer().getId());
            }
            return dto;
        } else {
            return null;
        }
    }
}
