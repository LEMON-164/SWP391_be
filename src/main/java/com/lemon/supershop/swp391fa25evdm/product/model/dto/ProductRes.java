package com.lemon.supershop.swp391fa25evdm.product.model.dto;

import java.util.Date;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private int dealerCategoryId;

    public ProductRes(int id, String name, String vinNum, String engineNum, Date manufacture_date, String image, String description, String status, int categoryId, int dealerCategoryId) {
        this.id = id;
        this.name = name;
        this.vinNum = vinNum;
        this.engineNum = engineNum;
        this.manufacture_date = manufacture_date;
        this.image = image;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
        this.dealerCategoryId = dealerCategoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVinNum() {
        return vinNum;
    }

    public void setVinNum(String vinNum) {
        this.vinNum = vinNum;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public Date getManufacture_date() {
        return manufacture_date;
    }

    public void setManufacture_date(Date manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDealerCategoryId() {
        return dealerCategoryId;
    }

    public void setDealerCategoryId(int dealerCategoryId) {
        this.dealerCategoryId = dealerCategoryId;
    }
}
