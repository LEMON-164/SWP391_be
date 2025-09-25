package com.lemon.supershop.swp391fa25evdm.category.Controller;

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

import com.lemon.supershop.swp391fa25evdm.category.Service.DealerCategoryService;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryRes;

@RestController
@RequestMapping("/api/dealer-categories")
public class DealerCategoryController {
    
    @Autowired
    private DealerCategoryService dealerCategoryService;

    // ✅ GET ALL - Lấy tất cả dealer categories
    @GetMapping ("/all")
    public ResponseEntity<List<DealerCategoryRes>> getAllDealerCategories() {
        try {
            List<DealerCategoryRes> dealerCategories = dealerCategoryService.getAllDealerCategories();
            if (dealerCategories.isEmpty()) {
                return new ResponseEntity<>(dealerCategories, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(dealerCategories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<DealerCategoryRes> getDealerCategoryById(@PathVariable String id) {
        try {
            DealerCategoryRes dealerCategory = dealerCategoryService.getDealerCategoryById(id);
            if (dealerCategory != null) {
                return new ResponseEntity<>(dealerCategory, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping ("/create")
    public ResponseEntity<DealerCategoryRes> createDealerCategory(@RequestBody DealerCategoryReq dealerCategoryReq) {
        try {
            if (dealerCategoryReq.getId() == null || dealerCategoryReq.getId().isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            
            DealerCategoryRes createdDealerCategory = dealerCategoryService.createDealerCategory(dealerCategoryReq);
            if (createdDealerCategory != null) {
                return new ResponseEntity<>(createdDealerCategory, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDealerCategory(@PathVariable String id, @RequestBody DealerCategoryReq dealerCategoryReq) {
        try {
            DealerCategoryRes existingDealerCategory = dealerCategoryService.getDealerCategoryById(id);
            if (existingDealerCategory == null) {
                return new ResponseEntity<>("Dealer Category not found with id: " + id, HttpStatus.NOT_FOUND);
            }
            
            DealerCategoryRes updatedDealerCategory = dealerCategoryService.updateDealerCategory(id, dealerCategoryReq);
            if (updatedDealerCategory != null) {
                return new ResponseEntity<>("Dealer Category updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update dealer category", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating dealer category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ DELETE - Xóa dealer category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDealerCategory(@PathVariable String id) {
        try {
            DealerCategoryRes existingDealerCategory = dealerCategoryService.getDealerCategoryById(id);
            if (existingDealerCategory == null) {
                return new ResponseEntity<>("Dealer Category not found with id: " + id, HttpStatus.NOT_FOUND);
            }
            
            DealerCategoryRes deletedDealerCategory = dealerCategoryService.deleteDealerCategory(id);
            if (deletedDealerCategory != null) {
                return new ResponseEntity<>("Dealer Category deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to delete dealer category", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting dealer category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
