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
}
