package com.lemon.supershop.swp391fa25evdm.contract.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemon.supershop.swp391fa25evdm.distribution.model.entity.Distribution;
import com.lemon.supershop.swp391fa25evdm.order.model.entity.Order;
import com.lemon.supershop.swp391fa25evdm.preorder.model.entity.PreOrder;
import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private int id;

    @Column(name = "SignDate", columnDefinition = "DATETIME2")
    private LocalDateTime signedDate;

    @Column(name = "FileUrl", columnDefinition = "VARCHAR(255)")
    private String fileUrl; // link PDF hợp đồng lưu trên server

    @OneToMany(mappedBy = "contract")
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "contract")
    @JsonIgnore
    private List<PreOrder> preOrders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "UserId")
    @JsonIgnore
    private User user;

    @OneToOne()
    @JoinColumn(name = "DistributionId")
    private Distribution distribution;

    public Contract() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(LocalDateTime signedDate) {
        this.signedDate = signedDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
