package com.lemon.supershop.swp391fa25evdm.policies.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.repository.CategoryRepository;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;
import com.lemon.supershop.swp391fa25evdm.policies.model.dto.PoliciesReq;
import com.lemon.supershop.swp391fa25evdm.policies.model.dto.PoliciesRes;
import com.lemon.supershop.swp391fa25evdm.policies.model.entity.Policy;
import com.lemon.supershop.swp391fa25evdm.policies.repository.PoliciesRepo;

@Service
public class PoliciesService {

    @Autowired
    private PoliciesRepo policiesRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private DealerRepo dealerRepo;

    public List<PoliciesRes> getAllPolicies() {
        List<Policy> policies = policiesRepo.findAll();
        return policies.stream().map(this::convertToRes).toList();
    }

    public List<PoliciesRes> getPoliciesByCategoryId(int categoryId) {
        List<Policy> policies = policiesRepo.findByCategoryId(categoryId);
        return policies.stream().map(this::convertToRes).toList();
    }

    public List<PoliciesRes> getPoliciesByDealerId(int dealerId) {
        List<Policy> policies = policiesRepo.findByDealerId(dealerId);
        return policies.stream().map(this::convertToRes).toList();
    }

    public PoliciesRes getPolicyById(int id) {
        Policy policy = policiesRepo.findById(id);
        if (policy != null) {
            return convertToRes(policy);
        }
        return null;
    }

    public void createPolicy(PoliciesReq dto) {
        Policy policy = convertToEntity(dto);
        policiesRepo.save(policy);
    }

    public void updatePolicy(int id, PoliciesReq dto) {
        Policy policy = policiesRepo.findById(id);
        if (policy == null) {
            throw new RuntimeException("Policy with ID '" + id + "' not found");
        }
        policy.setName(dto.getName());
        policy.setType(dto.getType());
        policy.setDescription(dto.getDescription());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policiesRepo.save(policy);
    }

    public void deletePolicy(int id) {
        Policy policy = policiesRepo.findById(id);
        if (policy == null) {
            throw new RuntimeException("Policy with ID '" + id + "' not found");
        }
        policiesRepo.delete(policy);
    }

    private Policy convertToEntity(PoliciesReq dto) {
        Policy policy = new Policy();
        policy.setName(dto.getName());
        policy.setType(dto.getType());
        policy.setDescription(dto.getDescription());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());

        if (dto.getCategoryId() != 0) {
            categoryRepo.findById(dto.getCategoryId()).ifPresent(policy::setCategory);
        }

        if (dto.getDealerId() != 0) {
            dealerRepo.findById(dto.getDealerId()).ifPresent(policy::setDealer);
        }

        return policy;
    }

    private PoliciesRes convertToRes(Policy policy) {
        PoliciesRes res = new PoliciesRes();
        res.setId(policy.getId());
        res.setName(policy.getName());
        res.setType(policy.getType());
        res.setDescription(policy.getDescription());
        res.setStartDate(policy.getStartDate());
        res.setEndDate(policy.getEndDate());
        
        if (policy.getCategory() != null) {
            res.setCategoryId(policy.getCategory().getId());
        }

        if (policy.getDealer() != null) {
            res.setDealerId(policy.getDealer().getId());
        }

        return res;
    }
}
