package com.lemon.supershop.swp391fa25evdm.testdrive.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDriveReq {

    private LocalDateTime scheduleDate;
    private String location;
    private String notes;
    private String status;
    private int userId;
    private int dealerId;
    private int dealerCategoryId;
}
