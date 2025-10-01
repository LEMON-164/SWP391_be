package com.lemon.supershop.swp391fa25evdm.testdrive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.repository.DealerCategoryRepository;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;
import com.lemon.supershop.swp391fa25evdm.testdrive.model.dto.TestDriveReq;
import com.lemon.supershop.swp391fa25evdm.testdrive.model.dto.TestDriveRes;
import com.lemon.supershop.swp391fa25evdm.testdrive.model.entity.TestDrive;
import com.lemon.supershop.swp391fa25evdm.testdrive.repository.TestDriveRepository;
import com.lemon.supershop.swp391fa25evdm.user.repository.UserRepo;

@Service
public class TestDriveService {

    @Autowired
    private TestDriveRepository testDriveRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DealerRepo dealerRepo;
    @Autowired
    private DealerCategoryRepository dealerCategoryRepository;

    public List<TestDriveRes> getAllTestDrive() {
        List<TestDrive> testDrives = testDriveRepository.findAll();
        return testDrives.stream().map(this::convertToRes).toList();
    }

    public TestDriveRes getTestDriveById(int id) {
        TestDrive testDrive = testDriveRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TestDrive not found with id: " + id));
        return convertToRes(testDrive);
    }

    public List<TestDriveRes> getTestDriveByUserId(int userId) {
        List<TestDrive> testDrives = testDriveRepository.findByUserId(userId);
        return testDrives.stream().map(this::convertToRes).toList();
    }

    public List<TestDriveRes> getTestDriveByDealerId(int dealerId) {
        List<TestDrive> testDrives = testDriveRepository.findByDealerId(dealerId);
        return testDrives.stream().map(this::convertToRes).toList();
    }

    public List<TestDriveRes> getTestDriveByDealerCategoryId(int dealerCategoryId) {
        List<TestDrive> testDrives = testDriveRepository.findByDealerCategoryId(dealerCategoryId);
        return testDrives.stream().map(this::convertToRes).toList();
    }

    public void createTestDrive(TestDriveReq req) {
        TestDrive testDrive = convertToEntity(req);
        testDriveRepository.save(testDrive);
    }

    public void updateTestDrive(int id, TestDriveReq req) {
        TestDrive testDrive = testDriveRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Test drive not found with id: " + id));
        testDrive.setScheduleDate(req.getScheduleDate());
        testDrive.setLocation(req.getLocation());
        testDrive.setStatus(req.getStatus());
        testDrive.setNotes(req.getNotes());

        userRepo.findById(req.getUserId()).ifPresent(testDrive::setUser);
        dealerRepo.findById(req.getDealerId()).ifPresent(testDrive::setDealer);
        dealerCategoryRepository.findById(req.getDealerCategoryId()).ifPresent(testDrive::setDealerCategory);
        testDriveRepository.save(testDrive);
    }

    public void deleteTestDrive(int id) {
        if (!testDriveRepository.findById(id).isPresent()) {
            throw new RuntimeException("Test drive not found with id: " + id);
        } else {
            testDriveRepository.deleteById(id);
        }
    }

    //method refference: object::method 
    //tham chiếu đến một phương thức của object và sử dụng nó như một biểu thức lambda.
    //không cần thêm logic
    private TestDrive convertToEntity(TestDriveReq req) {
        TestDrive testDrive = new TestDrive();
        testDrive.setScheduleDate(req.getScheduleDate());
        testDrive.setLocation(req.getLocation());
        testDrive.setStatus(req.getStatus());
        testDrive.setNotes(req.getNotes());

        userRepo.findById(req.getUserId()).ifPresent(testDrive::setUser);
        dealerRepo.findById(req.getDealerId()).ifPresent(testDrive::setDealer);
        dealerCategoryRepository.findById(req.getDealerCategoryId()).ifPresent(testDrive::setDealerCategory);
        return testDrive;
    }

    //lambda expression: (parameters) -> expression
    //cần thêm logic VD: user.getId()
    private TestDriveRes convertToRes(TestDrive testDrive) {
        TestDriveRes res = new TestDriveRes();
        res.setId(testDrive.getId());
        res.setScheduleDate(testDrive.getScheduleDate());
        res.setLocation(testDrive.getLocation());
        res.setStatus(testDrive.getStatus());
        res.setNotes(testDrive.getNotes());
        
        // Null-safe conversion
        if (testDrive.getUser() != null) {
            res.setUserId(testDrive.getUser().getId());
        }
        if (testDrive.getDealer() != null) {
            res.setDealerId(testDrive.getDealer().getId());
        }
        if (testDrive.getDealerCategory() != null) {
            res.setDealerCategoryId(testDrive.getDealerCategory().getId());
        }
        return res;
    }

}
