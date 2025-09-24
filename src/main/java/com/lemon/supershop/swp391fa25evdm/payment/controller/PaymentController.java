package com.lemon.supershop.swp391fa25evdm.payment.controller;

import com.lemon.supershop.swp391fa25evdm.payment.model.dto.request.PaymentReq;
import com.lemon.supershop.swp391fa25evdm.payment.model.dto.response.PaymentRes;
import com.lemon.supershop.swp391fa25evdm.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentRes>> getPaymentsByUser(@PathVariable int userId) {
        List<PaymentRes> payments = paymentService.getAllUserPayments(userId);
        return ResponseEntity.ok(payments);
    }

    @PostMapping("/order")
    public ResponseEntity<String> createPaymentOrder(@RequestBody PaymentReq dto) {
        paymentService.createPaymentOrder(dto);
        return ResponseEntity.ok("Payment for order created successfully.");
    }

    @PostMapping("/preorder")
    public ResponseEntity<String> createPaymentPreOrder(@RequestBody PaymentReq dto) {
        paymentService.createPaymentPreOrder(dto);
        return ResponseEntity.ok("Payment for preorder created successfully.");
    }

    @PostMapping("/installment")
    public ResponseEntity<String> createPaymentInstallment(@RequestBody PaymentReq dto) {
        paymentService.createPaymentInsPayment(dto);
        return ResponseEntity.ok("Installment payment created successfully.");
    }

    @PutMapping("/{id}/mark-paid")
    public ResponseEntity<String> markAsPaid(@PathVariable int id) {
        paymentService.markAsPaid(id);
        return ResponseEntity.ok("Payment marked as paid.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable int id) {
        paymentService.removePayment(id);
        return ResponseEntity.ok("Payment removed successfully.");
    }
}
