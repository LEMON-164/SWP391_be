package com.lemon.supershop.swp391fa25evdm.product.model.dto;

import java.sql.Date;

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
    private Date manufacture_date;
    private double dealerPrice;
    private String description;
    private String status;
    private int categoryId;
    private String dealerCategoryId;
    private String image;

}
