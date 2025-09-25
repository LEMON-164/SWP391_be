package com.lemon.supershop.swp391fa25evdm.product.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public String getName() {
        return name;
    }

    public String getVinNum() {
        return vinNum;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public Date getManufacture_date() {
        return manufacture_date;
    }

    public double getDealerPrice() {
        return dealerPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDealerCategoryId() {
        return dealerCategoryId;
    }

    public String getImage() {
        return image;
    }
}
