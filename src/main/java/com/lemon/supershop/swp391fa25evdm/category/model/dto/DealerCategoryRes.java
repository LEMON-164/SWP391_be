package com.lemon.supershop.swp391fa25evdm.category.model.dto;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealerCategoryRes {
    private String id;
    private String name;
    private int quantity;
    private String description;
    private String status;
    private Category category;
    private Dealer dealer;
}
