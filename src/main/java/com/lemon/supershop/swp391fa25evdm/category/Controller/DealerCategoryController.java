package com.lemon.supershop.swp391fa25evdm.category.Controller;

import com.lemon.supershop.swp391fa25evdm.category.Repository.DealerCategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.Service.DealerCategoryService;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryReq;
import com.lemon.supershop.swp391fa25evdm.category.model.dto.DealerCategoryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
