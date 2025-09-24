package com.lemon.supershop.swp391fa25evdm.order.model.dto.response;

import java.util.List;

public class OrderRes {
    private int orderId;
    private String customerName;
    private String productName;
    private int contractId;
    private double totalPrice;
    private String status;

    public OrderRes() {
    }

    public OrderRes(int orderId, String customerName, String productName, int contractId, double totalPrice, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.contractId = contractId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }
}
