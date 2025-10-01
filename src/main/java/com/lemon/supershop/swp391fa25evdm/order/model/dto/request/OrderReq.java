package com.lemon.supershop.swp391fa25evdm.order.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderReq {
    private int userId;
    private int productId;
    // private int contractId;
    private int dealerId;

}
