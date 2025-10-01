package com.lemon.supershop.swp391fa25evdm.payment.controller;

import com.lemon.supershop.swp391fa25evdm.payment.model.dto.request.InsPaymentReq;
import com.lemon.supershop.swp391fa25evdm.payment.model.dto.response.InsPaymentRes;
import com.lemon.supershop.swp391fa25evdm.payment.service.InsPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/installmentPayment")
public class InsPaymentController {

    @Autowired
    private InsPaymentService insPaymentService;

    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<InsPaymentRes>> getByPlanId(@PathVariable("planId") int planId) {
        List<InsPaymentRes> payments = insPaymentService.getInstallmentPaymentByInsPlanId(planId);
        return ResponseEntity.ok(payments);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> createInstallmentPayments(@PathVariable("id") int id) {
        insPaymentService.createInstallmentPaymentbyPlanId(id);
        return ResponseEntity.ok("Installment payments created successfully");
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<String> deleteInstallmentPayment(@PathVariable("id") int id) {
        insPaymentService.deleteInstallmentPayment(id);
        return ResponseEntity.ok("Installment payment deleted successfully");
    }
}
