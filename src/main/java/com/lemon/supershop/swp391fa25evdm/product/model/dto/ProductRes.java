package com.lemon.supershop.swp391fa25evdm.product.model.dto;

import java.util.Date;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRes {
    private int id;
    private String name;
    private String vinNum;
    private String engineNum;
    private Date manufacture_date;
    private String image;
    private String description;
    private String status;
    private int categoryId;
    private String dealerCategoryId;
}
