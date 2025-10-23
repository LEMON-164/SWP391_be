package com.lemon.supershop.swp391fa25evdm.distribution.model.entity;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.contract.model.entity.Contract;
import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;

import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "distribution")
public class Distribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private int id;

    // Messages & Notes
    @Column(name = "InvitationMessage", columnDefinition = "NVARCHAR(500)")
    private String invitationMessage;

    @Column(name = "DealerNotes", columnDefinition = "NVARCHAR(500)")
    private String dealerNotes;

    @Column(name = "EvmNotes", columnDefinition = "NVARCHAR(500)")
    private String evmNotes;

    @Column(name = "Feedback", columnDefinition = "NVARCHAR(500)")
    private String feedback;

    @Column(name = "CreatedAt", columnDefinition = "DATETIME2")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "INVITED";
        }
    }

    @Column(name = "InvitedAt", columnDefinition = "DATETIME2")
    private LocalDateTime invitedAt;
    @Column(name = "Deadline", columnDefinition = "DATETIME2")
    private LocalDateTime deadline;

    @Column(name = "RequestedDeliveryDate", columnDefinition = "DATETIME2")
    private LocalDateTime requestedDeliveryDate;

    @Column(name = "EstimatedDeliveryDate", columnDefinition = "DATETIME2")
    private LocalDateTime estimatedDeliveryDate;

    @Column(name = "ActualDeliveryDate", columnDefinition = "DATETIME2")
    private LocalDateTime actualDeliveryDate;

    @Column(name = "RequestedQuantity")
    private Integer requestedQuantity;

    @Column(name = "ReceivedQuantity")
    private Integer receivedQuantity;

    @Column(name = "Status", columnDefinition = "NVARCHAR(20)")
    private String status;

    @OneToMany(mappedBy = "distribution", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DealerId")
    private Dealer dealer;

    @OneToOne(mappedBy = "distribution")
    private Contract contract;

    public Distribution() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
