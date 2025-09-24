package com.lemon.supershop.swp391fa25evdm.payment.model.dto.response;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class InsPaymentRes {
    private int installmentNumber;
    private LocalDateTime dueDate;
    private double expectedAmount;
    private boolean paid;

    public InsPaymentRes() {
    }

    public InsPaymentRes(int installmentNumber, LocalDateTime dueDate, double expectedAmount, boolean paid) {
        this.installmentNumber = installmentNumber;
        this.dueDate = dueDate;
        this.expectedAmount = expectedAmount;
        this.paid = paid;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public double getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(double expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
