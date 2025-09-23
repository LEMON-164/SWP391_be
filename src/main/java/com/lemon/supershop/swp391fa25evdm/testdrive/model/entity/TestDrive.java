package com.lemon.supershop.swp391fa25evdm.testdrive.model.entity;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;
import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "testdrive")
public class TestDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private int id;

    @Column(name = "schedule_date", columnDefinition = "DATETIME2")
    private LocalDateTime scheduleDate;

    @Column(name = "location", columnDefinition = "NVARCHAR(255)")
    private String location;

    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELED

    @Column(name = "notes", columnDefinition = "NVARCHAR(MAX)")
    private String notes;

    @Column(insertable = false, updatable = false, name = "Create_at", nullable = false, columnDefinition = "DATETIME2 DEFAULT GETDATE()" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

    // ===== Relation =====

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;  // khách hàng đặt test drive

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DealerId", nullable = false)
    private Dealer dealer; // đại lý tổ chức test drive

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DealerCategoryId", nullable = false)
    private DealerCategory dealerCategory; // mẫu xe được chạy thử

    public TestDrive() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public DealerCategory getDealerCategory() {
        return dealerCategory;
    }

    public void setDealerCategory(DealerCategory dealerCategory) {
        this.dealerCategory = dealerCategory;
    }
}
