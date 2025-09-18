package com.lemon.supershop.swp391fa25evdm.payment.repository;

import com.lemon.supershop.swp391fa25evdm.payment.model.entity.InstallmentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsPaymentRepo extends JpaRepository<InstallmentPayment, Long> {
}
