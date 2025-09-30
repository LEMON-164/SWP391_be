package com.lemon.supershop.swp391fa25evdm.distribution.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.distribution.model.dto.DistributionRes;
import com.lemon.supershop.swp391fa25evdm.distribution.service.DistributionService;

@RestController
@RequestMapping("/api/distributions")
public class DistributionController {
    
    @Autowired
    private DistributionService distributionService;

    @GetMapping("/listDistributions")
    public ResponseEntity<List<DistributionRes>> getAllDistributions() {
        List<DistributionRes> distributions = distributionService.getAllDistributions();
        return ResponseEntity.ok(distributions);
    }
}
