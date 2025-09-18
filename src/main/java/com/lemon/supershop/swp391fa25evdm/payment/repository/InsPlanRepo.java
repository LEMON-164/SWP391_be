package com.lemon.supershop.swp391fa25evdm.payment.repository;

import com.lemon.supershop.swp391fa25evdm.payment.model.entity.InstallmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsPlanRepo extends JpaRepository<InstallmentPlan, Long> {
}
