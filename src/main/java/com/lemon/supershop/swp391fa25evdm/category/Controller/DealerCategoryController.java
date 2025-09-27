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
@RequestMapping("/api/dealerCategories")
public class DealerCategoryController {
    
    @Autowired
    private DealerCategoryService dealerCategoryService;

    @GetMapping ("/all")
    public ResponseEntity<List<DealerCategoryRes>> getAllDealerCategories() {
        List<DealerCategoryRes> dealerCategories = dealerCategoryService.getAllDealerCategories();
        return ResponseEntity.ok(dealerCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerCategoryRes> getDealerCategoryById(@PathVariable int id) {
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
