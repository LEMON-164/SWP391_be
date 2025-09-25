package com.lemon.supershop.swp391fa25evdm.category.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.category.Service.DealerCategoryService;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryRes;

@RestController
@RequestMapping ("/api/dealer-categories")
public class DealerCategoryController {
    @Autowired
    DealerCategoryService dealerCategoryService;

    @GetMapping
    public List<DealerCategoryRes> getAllDealerCategories() {
        return dealerCategoryService.getAllDealerCategories();
    }


    @PostMapping 
    public DealerCategoryRes createDealerCategory(@RequestBody DealerCategoryReq dto) {
        dealerCategoryService.createDealerCategory(dto);
        return null;
    }



}
