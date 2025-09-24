package com.lemon.supershop.swp391fa25evdm.payment.service;

import com.lemon.supershop.swp391fa25evdm.order.model.entity.Order;
import com.lemon.supershop.swp391fa25evdm.order.repository.OrderRepo;
import com.lemon.supershop.swp391fa25evdm.payment.model.dto.request.PaymentReq;
import com.lemon.supershop.swp391fa25evdm.payment.model.dto.response.PaymentRes;
import com.lemon.supershop.swp391fa25evdm.payment.model.entity.InstallmentPayment;
import com.lemon.supershop.swp391fa25evdm.payment.model.entity.Payment;
import com.lemon.supershop.swp391fa25evdm.payment.repository.InsPaymentRepo;
import com.lemon.supershop.swp391fa25evdm.payment.repository.PaymentRepo;
import com.lemon.supershop.swp391fa25evdm.preorder.model.entity.PreOrder;
import com.lemon.supershop.swp391fa25evdm.preorder.repository.PreOrderRepo;
import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import com.lemon.supershop.swp391fa25evdm.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PreOrderRepo preOrderRepo;

    @Autowired
    private InsPaymentRepo insPaymentRepo;

    public List<PaymentRes> getAllUserPayments(int id) {
        User user = userRepo.findById(id).get();
        if (user != null) {
            return paymentRepo.findByUserId(id).stream().map(payment -> {
                PaymentRes paymentRes = new PaymentRes();
                if (payment.getOrder() != null){
                    paymentRes.setOrderId(payment.getOrder().getId());
                }
                if (payment.getPreOrder() != null){
                    paymentRes.setPreorderId(payment.getPreOrder().getId());
                }
                if (payment.getMethod() != null){
                    paymentRes.setMethod(payment.getMethod());
                }
                if (payment.getPaidAt() != null){
                    paymentRes.setPaid_at(payment.getPaidAt());
                }
                paymentRes.isPaidStatus(payment.isPaidStatus());
                return paymentRes;
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public void createPaymentOrder(PaymentReq dto) {
        User user = userRepo.findById(dto.getUserId()).get();
        if (user != null) {
            Payment payment = new Payment();
            Order order = orderRepo.findById(dto.getPayforId()).get();
            if (order != null) {
                payment.setOrder(order);
            }
            payment.setUser(user);
            if (!dto.getMethod().isEmpty()){
                payment.setMethod(dto.getMethod());
            }
            paymentRepo.save(payment);
        }
    }

    public void createPaymentPreOrder(PaymentReq dto) {
        User user = userRepo.findById(dto.getUserId()).get();
        if (user != null) {
            Payment payment = new Payment();
            PreOrder preOrder = preOrderRepo.findById(dto.getPayforId()).get();
            if (preOrder != null) {
                payment.setPreOrder(preOrder);
            }
            payment.setUser(user);
            if (!dto.getMethod().isEmpty()){
                payment.setMethod(dto.getMethod());
            }
            paymentRepo.save(payment);
        }
    }

    public void createPaymentInsPayment(PaymentReq dto) {
        User user = userRepo.findById(dto.getUserId()).get();
        if (user != null) {
            Payment payment = new Payment();
            InstallmentPayment installmentPayment = insPaymentRepo.findById(dto.getPayforId()).get();
            if (installmentPayment != null) {
                payment.setInstallmentPayment(installmentPayment);
            }
            payment.setUser(user);
            if (!dto.getMethod().isEmpty()){
                payment.setMethod(dto.getMethod());
            }
            paymentRepo.save(payment);
        }
    }

    public void markAsPaid(int id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaidStatus(true);
        payment.setPaidAt(new Date());

        paymentRepo.save(payment);
    }

    public void removePayment(int id) {
        Optional<Payment> payment = paymentRepo.findById(id);
        if (payment.isPresent()) {
            paymentRepo.clearUserFromPayments(id);
            paymentRepo.delete(payment.get());
        }
    }
}
