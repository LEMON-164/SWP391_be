package com.lemon.supershop.swp391fa25evdm.category.model.dto;

import jakarta.validation.constraints.*;

public class CategoryReq {
    @Size(max = 150, message = "Name must not exceed 150 characters")
    private String name;

    @Size(max = 100, message = "Brand must not exceed 100 characters")
    private String brand;

    @Size(max = 50, message = "Version must not exceed 50 characters")
    private String version;

    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;

    @DecimalMin(value = "0.0", message = "Battery capacity must be positive")
    private Double battery;

    @Min(value = 0, message = "Range must be positive")
    private Integer range;

    @Min(value = 0, message = "HP must be positive")
    private Integer hp;

    @Min(value = 0, message = "Torque must be positive")
    private Integer torque;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", message = "Base price must be positive")
    private Double basePrice;

    @Min(value = 0, message = "Warranty must be positive")
    private Integer warranty;

    private Boolean isSpecial;

    private String description;

    @Pattern(regexp = "^(ACTIVE|INACTIVE|DRAFT)$", message = "Status must be ACTIVE, INACTIVE, or DRAFT")
    private String status;

    // Default constructor
    public CategoryReq() {}

    // Constructor with required fields
    public CategoryReq(String name, String brand, Double basePrice) {
        this.name = name;
        this.brand = brand;
        this.basePrice = basePrice;
        this.isSpecial = false;
        this.status = "ACTIVE";
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public Double getBattery() {
        return battery;
    }

    public Integer getRange() {
        return range;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getTorque() {
        return torque;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public Integer getWarranty() {
        return warranty;
    }

    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBattery(Double battery) {
        this.battery = battery;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public void setTorque(Integer torque) {
        this.torque = torque;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public void setWarranty(Integer warranty) {
        this.warranty = warranty;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
