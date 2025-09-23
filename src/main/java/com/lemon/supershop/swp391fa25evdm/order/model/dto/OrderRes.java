package com.lemon.supershop.swp391fa25evdm.order.model.dto;

import com.lemon.supershop.swp391fa25evdm.order.model.entity.OrderItem;

import java.util.List;

public class OrderRes {
    private int orderId;
    private String customerName;
    private List<OrderItemRes> orderItems;
    private double totalPrice;
    private String status;

    public OrderRes() {
    }

    public OrderRes(int orderId, String customerName, List<OrderItemRes> orderItems, double totalPrice, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderItems = orderItems;
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

    public List<OrderItemRes> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRes> orderItems) {
        this.orderItems = orderItems;
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
}
