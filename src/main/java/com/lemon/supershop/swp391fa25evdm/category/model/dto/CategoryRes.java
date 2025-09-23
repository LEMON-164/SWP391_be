package com.lemon.supershop.swp391fa25evdm.category.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class CategoryRes {
    private Integer id;
    private String name;
    private String brand;
    private String version;
    private String type;
    private Double battery;
    private Integer range;
    private Integer hp;
    private Integer torque;
    private Double basePrice;
    private Integer warranty;

    @JsonProperty("isSpecial")
    private Boolean isSpecial;

    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public CategoryRes() {}

    // Constructor with all fields
    public CategoryRes(Integer id, String name, String brand, String version, String type,
                      Double battery, Integer range, Integer hp, Integer torque,
                      Double basePrice, Integer warranty, Boolean isSpecial,
                      String description, String status) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.version = version;
        this.type = type;
        this.battery = battery;
        this.range = range;
        this.hp = hp;
        this.torque = torque;
        this.basePrice = basePrice;
        this.warranty = warranty;
        this.isSpecial = isSpecial;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public Integer getId() {
        return id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
