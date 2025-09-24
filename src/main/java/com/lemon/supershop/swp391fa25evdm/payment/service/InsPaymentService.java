package com.lemon.supershop.swp391fa25evdm.payment.service;

import com.lemon.supershop.swp391fa25evdm.payment.model.dto.request.InsPaymentReq;
import com.lemon.supershop.swp391fa25evdm.payment.model.dto.response.InsPaymentRes;
import com.lemon.supershop.swp391fa25evdm.payment.model.entity.InstallmentPayment;
import com.lemon.supershop.swp391fa25evdm.payment.model.entity.InstallmentPlan;
import com.lemon.supershop.swp391fa25evdm.payment.repository.InsPaymentRepo;
import com.lemon.supershop.swp391fa25evdm.payment.repository.InsPlanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsPaymentService {

    @Autowired
    private InsPaymentRepo insPaymentRepo;

    @Autowired
    private InsPlanRepo insPlanRepo;

    public List<InsPaymentRes> getInstallmentPaymentByInsPlanId(int id) {
        InstallmentPlan installmentPlan = insPlanRepo.findById(id).get();
        if (installmentPlan != null) {
            return insPaymentRepo.findByInstallmentPlan_Id(id).stream().map(installmentPayment -> {
                InsPaymentRes dto = new InsPaymentRes(installmentPayment.getInstallmentNumber(), installmentPayment.getDueDate(), installmentPayment.getExpectedAmount(), installmentPayment.isPaid());
                return dto;
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public void createInstallmentPaymentbyPlanId(int id) {
        InstallmentPlan installmentPlan = insPlanRepo.findById(id).orElseThrow(() -> new RuntimeException("Installment plan not found"));;
        if (installmentPlan != null) {
            double expectedAmount = (installmentPlan.getProduct().getDealerPrice()/installmentPlan.getMonths() * installmentPlan.getInterestRate()) + (installmentPlan.getProduct().getDealerPrice()/installmentPlan.getMonths());
            for (int i = 0; i < installmentPlan.getMonths(); i++){
                LocalDateTime dueDate  = LocalDateTime.now().plusMonths(i);
                InstallmentPayment  payment = new InstallmentPayment(i, expectedAmount, dueDate);
                insPaymentRepo.save(payment);
                installmentPlan.getInspayments().add(payment);
            }
        }
    }

    public void deleteInstallmentPayment(int id) {
        InstallmentPayment payment = insPaymentRepo.findById(id).get();
        if (payment != null) {
            insPaymentRepo.delete(payment);
        }
    }
}
