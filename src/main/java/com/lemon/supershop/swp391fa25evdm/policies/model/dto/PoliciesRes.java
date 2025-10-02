package com.lemon.supershop.swp391fa25evdm.policies.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliciesRes {
    private int id;
    private String name;
    private String type;
    private String description;
    private Date startDate;
    private Date endDate;
    private int categoryId;
    private int dealerId;
    
}
