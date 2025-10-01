package com.lemon.supershop.swp391fa25evdm.distribution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.repository.CategoryRepository;
import com.lemon.supershop.swp391fa25evdm.contract.repository.ContractRepo;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;
import com.lemon.supershop.swp391fa25evdm.distribution.model.dto.DistributionReq;
import com.lemon.supershop.swp391fa25evdm.distribution.model.dto.DistributionRes;
import com.lemon.supershop.swp391fa25evdm.distribution.model.entity.Distribution;
import com.lemon.supershop.swp391fa25evdm.distribution.repository.DistributionRepo;

@Service
public class DistributionService {

    @Autowired
    private DistributionRepo distributionRepo;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private DealerRepo dealerRepo;
    @Autowired
    private ContractRepo contractRepo;

    public List<DistributionRes> getAllDistributions() {
        List<Distribution> distributions = distributionRepo.findAll();
        return distributions.stream().map(this::convertToRes).toList();
    }

    public List<DistributionRes> getDistributionsByCategoryId(int categoryId) {
        List<Distribution> distributions = distributionRepo.findByCategoryId(categoryId);
        return distributions.stream().map(this::convertToRes).toList();
    }

    public List<DistributionRes> getDistributionsByDealerId(int dealerId) {
        List<Distribution> distributions = distributionRepo.findByDealerId(dealerId);
        return distributions.stream().map(this::convertToRes).toList();
    }

    public List<DistributionRes> getDistributionsByContractId(int contractId) {
        List<Distribution> distributions = distributionRepo.findByContractId(contractId);
        return distributions.stream().map(this::convertToRes).toList();
    }

    public void createDistribution(DistributionReq req) {
        Distribution distribution = convertToEntity(req);
        distributionRepo.save(distribution);
    }

    public void updateDistribution(int id, DistributionReq req) {
        Distribution distribution = distributionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Distribution not found with id: " + id));
        distribution.setCategory(categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + req.getCategoryId())));
        distribution.setDealer(dealerRepo.findById(req.getDealerId())
                .orElseThrow(() -> new RuntimeException("Dealer not found with id: " + req.getDealerId())));
        distribution.setContract(contractRepo.findById(req.getContractId())
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + req.getContractId())));
        distributionRepo.save(distribution);
    }

    public void deleteDistribution(int id) {
        if (!distributionRepo.existsById(id)) {
            throw new RuntimeException("Distribution not found with id: " + id);
        }
        distributionRepo.deleteById(id);
    }

    private Distribution convertToEntity(DistributionReq req) {
        Distribution distribution = new Distribution();
        categoryRepository.findById(req.getCategoryId()).ifPresent(distribution::setCategory);
        dealerRepo.findById(req.getDealerId()).ifPresent(distribution::setDealer);
        contractRepo.findById(req.getContractId()).ifPresent(distribution::setContract);
        return distribution;
    }

    private DistributionRes convertToRes(Distribution distribution) {
        DistributionRes res = new DistributionRes();

        if (distribution.getCategory() != null) {
            res.setCategoryId(distribution.getCategory().getId());
        }
        if (distribution.getDealer() != null) {
            res.setDealerId(distribution.getDealer().getId());
        }
        if (distribution.getContract() != null) {
            res.setContractId(distribution.getContract().getId());
        }
        res.setId(distribution.getId());
        return res;

    }
}
