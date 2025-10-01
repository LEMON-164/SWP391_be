package com.lemon.supershop.swp391fa25evdm.testdrive.controller;

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

import com.lemon.supershop.swp391fa25evdm.testdrive.model.dto.TestDriveReq;
import com.lemon.supershop.swp391fa25evdm.testdrive.model.dto.TestDriveRes;
import com.lemon.supershop.swp391fa25evdm.testdrive.service.TestDriveService;

@RestController
@RequestMapping("/api/testdrives")
public class TestDriveController {

    @Autowired
    private TestDriveService testDriveService;
    
    @GetMapping("/listTestDrives")
    public ResponseEntity<List<TestDriveRes>> getAllTestDrives() {
        List<TestDriveRes> testDrives = testDriveService.getAllTestDrive();
        return ResponseEntity.ok(testDrives);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<TestDriveRes> getTestDriveById(@PathVariable int id) {
        TestDriveRes testDrive = testDriveService.getTestDriveById(id);
        return ResponseEntity.ok(testDrive);
    }

    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List<TestDriveRes>> getTestDriveByUserId(@PathVariable int userId) {
        List<TestDriveRes> testDrives = testDriveService.getTestDriveByUserId(userId);
        return ResponseEntity.ok(testDrives);
    }

    @GetMapping("/search/dealer/{dealerId}")
    public ResponseEntity<List<TestDriveRes>> getTestDriveByDealerId(@PathVariable int dealerId) {
        List<TestDriveRes> testDrives = testDriveService.getTestDriveByDealerId(dealerId);
        return ResponseEntity.ok(testDrives);
    }

    @GetMapping("/search/dealer-category/{dealerCategoryId}")
    public ResponseEntity<List<TestDriveRes>> getTestDriveByDealerCategoryId(@PathVariable int dealerCategoryId) {
        List<TestDriveRes> testDrives = testDriveService.getTestDriveByDealerCategoryId(dealerCategoryId);
        return ResponseEntity.ok(testDrives);
    }

    @PutMapping("/updateTestDrive/{id}")
    public ResponseEntity<String> updateTestDrive (@PathVariable int id, @RequestBody TestDriveReq testDriveReq) {
        testDriveService.updateTestDrive(id, testDriveReq);
        return ResponseEntity.ok("Test drive updated successfully");
    }

    @DeleteMapping("/deleteTestDrive/{id}")
    public ResponseEntity<String> deleteTestDrive (@PathVariable int id) {
        testDriveService.deleteTestDrive(id);
        return ResponseEntity.ok("Test drive deleted successfully");
    }

    @PostMapping("/createTestDrive")
    public ResponseEntity<String> createTestDrive (@RequestBody TestDriveReq testDriveReq) {
        testDriveService.createTestDrive(testDriveReq);
        return ResponseEntity.ok("Test drive created successfully");
    }   


}
