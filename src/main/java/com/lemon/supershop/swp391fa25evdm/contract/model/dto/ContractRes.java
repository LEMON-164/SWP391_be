package com.lemon.supershop.swp391fa25evdm.contract.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractRes {
    private int id;
    private LocalDateTime signedDate;
    private String fileUrl; // link PDF hợp đồng lưu trên server
    private int orderId;
    private int userId;
    private String status;
}
