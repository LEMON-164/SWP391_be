package com.lemon.supershop.swp391fa25evdm.distribution.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionReq {

    private int categoryId;
    private int dealerId;
    private int contractId;

}
