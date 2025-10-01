package com.lemon.supershop.swp391fa25evdm.policies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.policies.model.dto.PoliciesReq;
import com.lemon.supershop.swp391fa25evdm.policies.model.dto.PoliciesRes;
import com.lemon.supershop.swp391fa25evdm.policies.service.PoliciesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/policies")
public class PoliciesController {
    
    @Autowired
    private PoliciesService policiesService;

    @GetMapping ("/listPolicies")
    public ResponseEntity<List<PoliciesRes>> getAllPolicies() {
        List<PoliciesRes> policies = policiesService.getAllPolicies();
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/search/category/{categoryId}")
    public ResponseEntity<List<PoliciesRes>> getPoliciesByCategoryId(@PathVariable int categoryId) {
        List<PoliciesRes> policies = policiesService.getPoliciesByCategoryId(categoryId);
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/search/dealer/{dealerId}")
    public ResponseEntity<List<PoliciesRes>> getPoliciesByDealerId(@PathVariable int dealerId) {
        List<PoliciesRes> policies = policiesService.getPoliciesByDealerId(dealerId);
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<PoliciesRes> getPolicyById(@PathVariable int id) {
        PoliciesRes policy = policiesService.getPolicyById(id);
        return ResponseEntity.ok(policy);
    }

    @PostMapping ("/create")
    public ResponseEntity<String> createPolicy(@Valid @RequestBody PoliciesReq policiesReq) {
        policiesService.createPolicy(policiesReq);
        return ResponseEntity.ok("Policy created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePolicy(@PathVariable int id, @Valid @RequestBody PoliciesReq policiesReq) {
        policiesService.updatePolicy(id, policiesReq);
        return ResponseEntity.ok("Policy updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable int id) {
        policiesService.deletePolicy(id);
        return ResponseEntity.ok("Policy deleted successfully");
    }

}
