package com.lemon.supershop.swp391fa25evdm.payment.service;

import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;
import com.lemon.supershop.swp391fa25evdm.payment.model.dto.request.InsPlanReq;
import com.lemon.supershop.swp391fa25evdm.payment.model.dto.response.InsPlanRes;
import com.lemon.supershop.swp391fa25evdm.payment.model.entity.InstallmentPlan;
import com.lemon.supershop.swp391fa25evdm.payment.repository.InsPlanRepo;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import com.lemon.supershop.swp391fa25evdm.product.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsplanService {
    @Autowired
    private InsPlanRepo insPlanRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private DealerRepo dealerRepo;

    public List<InsPlanRes> getAllInstallmentPlans() {
        return insPlanRepo.findAll().stream().map(installmentPlan -> {
            InsPlanRes dto = new InsPlanRes(installmentPlan.getMonths(), installmentPlan.getInterestRate(), installmentPlan.getProduct().getName());
            dto.setMonthPrice((installmentPlan.getProduct().getDealerPrice()/installmentPlan.getMonths() * installmentPlan.getInterestRate()) + installmentPlan.getProduct().getDealerPrice()/installmentPlan.getMonths());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<InsPlanRes> getInstallmentPlanByProductId(int id) {
        Product product = productRepo.findById(id).get();
        if (product != null) {
            return insPlanRepo.findByProductId(id).stream().map(installmentPlan -> {
                InsPlanRes dto = new InsPlanRes(installmentPlan.getMonths(), installmentPlan.getInterestRate(), product.getName());
                dto.setMonthPrice((product.getDealerPrice()/installmentPlan.getMonths() * installmentPlan.getInterestRate())+product.getDealerPrice()/installmentPlan.getMonths());
                return dto;
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public void addInstallmentPlan(InsPlanReq dto) {
        InstallmentPlan installmentPlan = new InstallmentPlan();
        if (dto.getProductId() > 0){
            Product product = productRepo.findById(dto.getProductId()).get();
            if (product != null) {
                installmentPlan.setProduct(product);
            }
        }
        if (dto.getMonths() > 0){
            installmentPlan.setMonths(dto.getMonths());
        }
        if (dto.getInterestRate() > 0){
            installmentPlan.setInterestRate(dto.getInterestRate());
        }
        if (dto.getDealerId() > 0){
            Dealer dealer = dealerRepo.findById(dto.getDealerId()).get();
            if (dealer != null) {
                installmentPlan.setDealer(dealer);
            }
        }
        insPlanRepo.save(installmentPlan);
    }

    public void updateInstallmentPlan(int id, InsPlanReq dto) {
        InstallmentPlan installmentPlan = insPlanRepo.findById(id).get();
        if (installmentPlan != null) {
            if (dto.getProductId() >= 0){
                Product product = productRepo.findById(dto.getProductId()).get();
                if (product != null) {
                    installmentPlan.setProduct(product);
                }
            }
            if (dto.getMonths() > 0){
                installmentPlan.setMonths(dto.getMonths());
            }
            if (dto.getInterestRate() > 0){
                installmentPlan.setInterestRate(dto.getInterestRate());
            }
            insPlanRepo.save(installmentPlan);
        }
    }
    public void deleteInstallmentPlan(int id) {
        InstallmentPlan installmentPlan = insPlanRepo.findById(id).get();
        if (installmentPlan != null) {
            insPlanRepo.delete(installmentPlan);
        }
    }

}
