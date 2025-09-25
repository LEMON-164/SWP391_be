package com.lemon.supershop.swp391fa25evdm.contract.repository;

import com.lemon.supershop.swp391fa25evdm.contract.model.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Integer> {
}
