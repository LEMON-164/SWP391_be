package com.lemon.supershop.swp391fa25evdm.order.model.dto.request;

public class OrderReq {
    private int userId;
    private int productId;
    private int contractId;
    private int dealerId;

    public OrderReq() {}

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getContractId() {
        return contractId;
    }

    public int getDealerId() {
        return dealerId;
    }
}
