package com.lemon.supershop.swp391fa25evdm.product.model.dto;

import java.util.Date;

public class ProductRes {
    private int id;
    private String name;
    private String vinNum;
    private String engineNum;
    private double battery;
    private int range;
    private int hp;
    private int torque;
    private boolean isSpecial;
    private Date manufacture_date;
    private String image;
    private String description;
    private double price;
    private String status;
    private int categoryId;
    private int dealerCategoryId;

    public ProductRes() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVinNum(String vinNum) {
        this.vinNum = vinNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public void setManufacture_date(Date manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDealerCategoryId(int dealerCategoryId) {
        this.dealerCategoryId = dealerCategoryId;
    }

    public int getId() {
        return id;
    }

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

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getDealerCategoryId() {
        return dealerCategoryId;
    }

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }
}
