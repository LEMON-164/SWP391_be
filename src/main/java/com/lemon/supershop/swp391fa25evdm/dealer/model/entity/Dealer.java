package com.lemon.supershop.swp391fa25evdm.dealer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;
import com.lemon.supershop.swp391fa25evdm.distribution.model.entity.Distribution;
import com.lemon.supershop.swp391fa25evdm.order.model.entity.Order;
import com.lemon.supershop.swp391fa25evdm.policies.model.entity.Policy;
import com.lemon.supershop.swp391fa25evdm.promotion.model.entity.Promotion;
import com.lemon.supershop.swp391fa25evdm.testdrive.model.entity.TestDrive;
import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "dealer")
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private int id;

    @Column(name = "Name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "Phone", nullable = false, columnDefinition = "VARCHAR(11)")
    private String phone;

    @Column(name = "Address", columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(name = "Tax", columnDefinition = "VARCHAR(50)")
    private String taxcode;

    @Column(name = "Status", nullable = false, columnDefinition = "VARCHAR(20)")
    private String status;

    @Column(insertable = false, updatable = false, name = "Create_at", nullable = false, columnDefinition = "DATETIME2 DEFAULT GETDATE()" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

    @OneToMany(mappedBy = "dealer")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "dealer")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "dealer")
    @JsonIgnore
    private Set<DealerCategory> dealerCategories = new HashSet<>();

    @OneToMany(mappedBy = "dealer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Distribution> distributions = new ArrayList<>();

    @OneToMany(mappedBy = "dealer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Policy> policies = new ArrayList<>();

    @OneToMany(mappedBy = "dealer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Promotion> promotions = new ArrayList<>();

    @OneToMany(mappedBy = "dealer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TestDrive> testDrives;


}
