package com.lemon.supershop.swp391fa25evdm.category.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReq {
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
    private Boolean isSpecial;
    private String description;
    private String status;

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

    public Boolean getSpecial() {
        return isSpecial;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
