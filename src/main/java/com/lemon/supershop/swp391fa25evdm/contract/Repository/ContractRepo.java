package com.lemon.supershop.swp391fa25evdm.contract.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lemon.supershop.swp391fa25evdm.contract.model.entity.Contract;


@Repository
public interface ContractRepo extends JpaRepository<Contract, Integer> {
    List<Contract> findContractById(int id);
    List<Contract> findContractByUserId(int userId);
    List<Contract> findContractByOrderId(int orderId);
    List<Contract> findContractByStatus(String status);
}
