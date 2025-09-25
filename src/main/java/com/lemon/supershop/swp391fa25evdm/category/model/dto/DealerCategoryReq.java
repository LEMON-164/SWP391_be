package com.lemon.supershop.swp391fa25evdm.category.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealerCategoryReq {
    private String id;
    private String name;
    private int quantity;
    private String description;
    private String status;
    private int categoryId;
}
