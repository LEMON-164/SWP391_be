package com.lemon.supershop.swp391fa25evdm.payment.repository;

import com.lemon.supershop.swp391fa25evdm.payment.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
