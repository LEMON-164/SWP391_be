package com.lemon.supershop.swp391fa25evdm.contract.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.contract.Repository.ContractRepo;
import com.lemon.supershop.swp391fa25evdm.contract.model.dto.ContractReq;
import com.lemon.supershop.swp391fa25evdm.contract.model.dto.ContractRes;
import com.lemon.supershop.swp391fa25evdm.contract.model.entity.Contract;

@Service
public class ContractService {

    @Autowired
    private ContractRepo contractRepo;

    public List<ContractRes> getAllContracts() {
        List<Contract> contracts = contractRepo.findAll();
        return contracts.stream().map(this::convertToRes).toList();
    }

    public ContractRes getContractById(int id) {
        return contractRepo.findById(id)
                .map(this::convertToRes)
                .orElse(null);
    }

    public List<ContractRes> getContractsByUserId(int userId) {
        List<Contract> contracts = contractRepo.findContractByUserId(userId);
        return contracts.stream().map(this::convertToRes).toList();
    }

    public List<ContractRes> getContractsByOrderId(int orderId) {
        List<Contract> contracts = contractRepo.findContractByOrderId(orderId);
        return contracts.stream().map(this::convertToRes).toList();
    }

    public List<ContractRes> getContractsByStatus(String status) {
        List<Contract> contracts = contractRepo.findContractByStatus(status);
        return contracts.stream().map(this::convertToRes).toList();
    }

    public void createContract (ContractReq dto) {
        Contract contract = new Contract();
        contract.setSignedDate(dto.getSignedDate());
        contract.setFileUrl(dto.getFileUrl());
        contract.setOrder(contractRepo.findById(dto.getOrderId()).orElse(null).getOrder());
        contract.setUser(contractRepo.findById(dto.getUserId()).orElse(null).getUser());
        contract.setStatus(dto.getStatus());
        contractRepo.save(contract);
    }
    public void deleteContract(int id) {
        if (contractRepo.existsById(id) && id != 0){
            contractRepo.deleteById(id);
        }
    }
    public void updateContract(int id, ContractReq dto) throws Exception {
        Contract existingContract = contractRepo.findById(id)
                .orElseThrow(() -> new Exception("Contract not found with id: " + id));
        existingContract.setSignedDate(dto.getSignedDate());
        existingContract.setFileUrl(dto.getFileUrl());
        existingContract.setOrder(contractRepo.findById(dto.getOrderId()).orElse(null).getOrder());
        existingContract.setUser(contractRepo.findById(dto.getUserId()).orElse(null).getUser());
        existingContract.setStatus(dto.getStatus());
        contractRepo.save(existingContract);
    }

    private ContractRes convertToRes(Contract contract) {
        ContractRes dto = new ContractRes();
        dto.setId(contract.getId());
        dto.setSignedDate(contract.getSignedDate());
        dto.setFileUrl(contract.getFileUrl());
        dto.setOrderId(contract.getOrder() != null ? contract.getOrder().getId() : null);
        dto.setUserId(contract.getUser() != null ? contract.getUser().getId() : null);
        dto.setStatus(contract.getStatus());
        return dto;
    }

}