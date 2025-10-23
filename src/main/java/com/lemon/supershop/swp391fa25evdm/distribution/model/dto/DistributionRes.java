package com.lemon.supershop.swp391fa25evdm.distribution.model.dto;

import com.lemon.supershop.swp391fa25evdm.dealer.model.dto.DealerRes;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductReq;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class DistributionRes {

    private int id;
    private DealerRes dealer;
    private int contractId;
    private String status;
    private List<ProductRes> products;
    private String invitationMessage;
    private String dealerNotes;
    private String evmNotes;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime invitedAt;
    private LocalDateTime deadline;
    private LocalDateTime requestedDeliveryDate;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private Integer requestedQuantity;
    private Integer receivedQuantity;

    public DistributionRes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public List<ProductRes> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRes> products) {
        this.products = products;
    }

    public DealerRes getDealer() {
        return dealer;
    }

    public void setDealer(DealerRes dealer) {
        this.dealer = dealer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvitationMessage() {
        return invitationMessage;
    }

    public void setInvitationMessage(String invitationMessage) {
        this.invitationMessage = invitationMessage;
    }

    public String getDealerNotes() {
        return dealerNotes;
    }

    public void setDealerNotes(String dealerNotes) {
        this.dealerNotes = dealerNotes;
    }

    public String getEvmNotes() {
        return evmNotes;
    }

    public void setEvmNotes(String evmNotes) {
        this.evmNotes = evmNotes;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getInvitedAt() {
        return invitedAt;
    }

    public void setInvitedAt(LocalDateTime invitedAt) {
        this.invitedAt = invitedAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    public void setRequestedDeliveryDate(LocalDateTime requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public LocalDateTime getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDateTime actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }
}

