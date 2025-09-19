package com.lemon.supershop.swp391fa25evdm.payment.model.dto.request;

public class InsPlanReq {
    private int months;
    private double interestRate;
    private int productId;

    public InsPlanReq() {
    }

    public int getMonths() {
        return months;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getProductId() {
        return productId;
    }
}
