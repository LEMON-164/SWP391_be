package com.lemon.supershop.swp391fa25evdm.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryRes;
import com.lemon.supershop.swp391fa25evdm.category.service.DealerCategoryService;

@RestController
@RequestMapping("/api/dealerCategories")
public class DealerCategoryController {
    
    @Autowired
    private DealerCategoryService dealerCategoryService;

    @GetMapping ("/listDealerCategories")
    public ResponseEntity<List<DealerCategoryRes>> getAllDealerCategories() {
        List<DealerCategoryRes> dealerCategories = dealerCategoryService.getAllDealerCategories();
        return ResponseEntity.ok(dealerCategories);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<DealerCategoryRes> getDealerCategoryById(@PathVariable int id) {
        DealerCategoryRes dealerCategory = dealerCategoryService.getDealerCategoryById(id);
        return ResponseEntity.ok(dealerCategory);
    }

    @PostMapping ("/create")
    public ResponseEntity<String> createDealerCategory(@RequestBody DealerCategoryReq dealerCategoryReq) {
        dealerCategoryService.createDealerCategory(dealerCategoryReq);
        return ResponseEntity.ok("Dealer Category created successfully");

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDealerCategory(@PathVariable int id, @RequestBody DealerCategoryReq dealerCategoryReq) {
        try {
            dealerCategoryService.updateDealerCategory(id, dealerCategoryReq);
            return ResponseEntity.ok("Dealer Category updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDealerCategory(@PathVariable int id) {
        dealerCategoryService.deleteDealerCategory(id);
        return ResponseEntity.ok("Dealer Category deleted successfully");

    }
}
