package com.lemon.supershop.swp391fa25evdm.category.model.dto;

public class DealerCategoryRes {
    private String id;
    private String name;
    private int quantity;
    private String description;
    private String status;

    // Constructor

    public DealerCategoryRes(String id, String name, int quantity, String description, String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.status = status;
    }

    // Getters

    public String getId() {
        return id;
    }

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
}
