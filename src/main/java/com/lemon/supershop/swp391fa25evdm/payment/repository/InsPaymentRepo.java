package com.lemon.supershop.swp391fa25evdm.payment.repository;

import com.lemon.supershop.swp391fa25evdm.payment.model.entity.InstallmentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsPaymentRepo extends JpaRepository<InstallmentPayment, Integer> {
    Optional<InstallmentPayment> findById(int id);
}
