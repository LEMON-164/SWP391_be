package com.lemon.supershop.swp391fa25evdm.testdrive.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDriveRes {

    private int id;
    private LocalDateTime scheduleDate;
    private String location;
    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELED
    private String notes;
    private int userId;
    private int dealerId;
    private int dealerCategoryId;
}
