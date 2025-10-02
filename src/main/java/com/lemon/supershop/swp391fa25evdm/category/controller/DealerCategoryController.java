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
    public ResponseEntity<DealerCategoryRes> createDealerCategory(@RequestBody DealerCategoryReq dealerCategoryReq) {
        DealerCategoryRes categoryRes = dealerCategoryService.createDealerCategory(dealerCategoryReq);
        if (categoryRes != null) {
            return ResponseEntity.ok(categoryRes);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DealerCategoryRes> updateDealerCategory(@PathVariable int id, @RequestBody DealerCategoryReq dealerCategoryReq) {
        try {
            DealerCategoryRes categoryRes = dealerCategoryService.updateDealerCategory(id, dealerCategoryReq);
            if (categoryRes != null) {
                return ResponseEntity.ok(categoryRes);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDealerCategory(@PathVariable int id) {
        try {
            DealerCategoryRes existingDealerCategory = dealerCategoryService.getDealerCategoryById(id);
            if (existingDealerCategory == null) {
                return new ResponseEntity<>("Dealer Category not found with id: " + id, HttpStatus.NOT_FOUND);
            }
            
            Boolean deletedDealerCategory = dealerCategoryService.deleteDealerCategory(id);
            if (deletedDealerCategory) {
                return new ResponseEntity<>("Dealer Category deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to delete dealer category", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting dealer category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
