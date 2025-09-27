package com.lemon.supershop.swp391fa25evdm.category.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DealerCategoryReq {
    private String name;
    private int quantity;
    private String description;
    private String status;
    private int categoryId;
    private int dealerId;

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
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

    public int getDealerId() {
        return dealerId;
    }
}
