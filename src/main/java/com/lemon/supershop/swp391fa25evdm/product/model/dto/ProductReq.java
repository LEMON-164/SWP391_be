package com.lemon.supershop.swp391fa25evdm.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReq {
    private String name;
    private String vinNum;
    private String engineNum;
    private String manufacture_date;
    private double dealerPrice;
    private String description;
    private String status;
    private String categoryId;
    private String dealerCategoryId;
    private String image;

}
