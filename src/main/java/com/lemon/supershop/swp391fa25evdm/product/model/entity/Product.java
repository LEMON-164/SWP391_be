package com.lemon.supershop.swp391fa25evdm.product.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;
import com.lemon.supershop.swp391fa25evdm.order.model.entity.OrderItem;
import com.lemon.supershop.swp391fa25evdm.payment.model.entity.InstallmentPlan;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private int id;

    @Column(name = "Name", columnDefinition = "VARCHAR(150)")
    private String name;

    @Column(name = "Vin", columnDefinition = "VARCHAR UNIQUE")
    private String vinNum;

    @Column(name = "Engine", columnDefinition = "VARCHAR UNIQUE")
    private String engineNum;

    @Column(name = "Manufacture", columnDefinition = "DATETIME2")
    private Date manufacture_date;

    @Column(name = "DealerPrice", nullable = false, columnDefinition = "DECIMAL(15,2)")
    private double dealerPrice;

    @Column(name = "Image", columnDefinition = "VARCHAR(MAX)")
    private String image;

    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "Status", columnDefinition = "VARCHAR(20)")
    private String status;

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "DealerCategoryId")
    @JsonIgnore
    private DealerCategory dealerCategory;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private InstallmentPlan installmentPlan;

    public Product() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVinNum() {
        return vinNum;
    }

    public void setVinNum(String vinNum) {
        this.vinNum = vinNum;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public Date getManufacture_date() {
        return manufacture_date;
    }

    public void setManufacture_date(Date manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DealerCategory getDealerCategory() {
        return dealerCategory;
    }

    public void setDealerCategory(DealerCategory dealerCategory) {
        this.dealerCategory = dealerCategory;
    }

    public InstallmentPlan getInstallmentPlan() {
        return installmentPlan;
    }

    public void setInstallmentPlan(InstallmentPlan installmentPlan) {
        this.installmentPlan = installmentPlan;
    }

    public double getDealerPrice() {
        return dealerPrice;
    }

    public void setDealerPrice(double dealerPrice) {
        this.dealerPrice = dealerPrice;
    }
}
