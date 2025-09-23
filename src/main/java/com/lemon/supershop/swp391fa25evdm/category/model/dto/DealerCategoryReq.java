package com.lemon.supershop.swp391fa25evdm.category.model.dto;

public class DealerCategoryReq {
    private String name;
    private int quantity;
    private String description;
    private String status;

    //getters

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

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
