package com.lemon.supershop.swp391fa25evdm.distribution.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lemon.supershop.swp391fa25evdm.contract.model.entity.Contract;
import com.lemon.supershop.swp391fa25evdm.dealer.model.dto.DealerRes;
import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;
import com.lemon.supershop.swp391fa25evdm.dealer.service.DealerService;
import com.lemon.supershop.swp391fa25evdm.distribution.model.dto.request.*;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductRes;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import com.lemon.supershop.swp391fa25evdm.product.repository.ProductRepo;
import com.lemon.supershop.swp391fa25evdm.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.contract.repository.ContractRepo;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;
import com.lemon.supershop.swp391fa25evdm.distribution.model.dto.DistributionRes;
import com.lemon.supershop.swp391fa25evdm.distribution.model.entity.Distribution;
import com.lemon.supershop.swp391fa25evdm.distribution.repository.DistributionRepo;

@Service
public class DistributionService {

    @Autowired
    private DistributionRepo distributionRepo;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private DealerRepo dealerRepo;
    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductService productService;


    public List<DistributionRes> getAllDistributions() {
        List<Distribution> distributions = distributionRepo.findAll();
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

    public DistributionRes sendInvitation(DistributionInvitationReq req) {
        Distribution distribution = new Distribution();

        // Set dealer
        Optional<Dealer> dealer = dealerRepo.findById(req.getDealerId());
        if (!dealer.isPresent()) {
            throw new RuntimeException("Dealer not found with id: " + req.getDealerId());
        }
        distribution.setDealer(dealer.get());

        distribution.setInvitationMessage(req.getInvitationMessage());
        distribution.setDeadline(req.getDeadline());
        distribution.setStatus("INVITED");
        distribution.setInvitedAt(LocalDateTime.now());

        distributionRepo.save(distribution);
        return convertToRes(distribution);
    }

    public DistributionRes respondToInvitation(int id, DistributionResponseReq req) {
        Optional<Distribution> opt = distributionRepo.findById(id);
        if (opt.isPresent()) {
            Distribution distribution = opt.get();

            // Validate status
            if (!"INVITED".equals(distribution.getStatus())) {
                throw new RuntimeException("Invalid status. Expected INVITED, got: " + distribution.getStatus());
            }

            distribution.setStatus(req.getResponse()); // "ACCEPTED" or "DECLINED"
            distribution.setDealerNotes(req.getDealerNotes());

            distributionRepo.save(distribution);
            return convertToRes(distribution);
        }
        return null;
    }

    public DistributionRes submitOrder(int id, DistributionOrderReq req) {
        Optional<Distribution> opt = distributionRepo.findById(id);
        if (opt.isPresent()) {
            Distribution distribution = opt.get();

            if (!"ACCEPTED".equals(distribution.getStatus())) {
                throw new RuntimeException("Invalid status. Expected ACCEPTED, got: " + distribution.getStatus());
            }

            if (req.getProductIds() != null && !req.getProductIds().isEmpty()) {
                List<Product> products = new ArrayList<>();
                for (Integer productId : req.getProductIds()) {
                    Optional<Product> product = productRepo.findById(productId);
                    if (product.isPresent()) {
                        products.add(product.get());
                    }
                }
                distribution.setProducts(products);
            }

            distribution.setRequestedQuantity(req.getRequestedQuantity());
            distribution.setRequestedDeliveryDate(req.getRequestedDeliveryDate());
            if (req.getDealerNotes() != null) {
                distribution.setDealerNotes(req.getDealerNotes());
            }
            distribution.setStatus("PENDING");

            distributionRepo.save(distribution);
            return convertToRes(distribution);
        }
        return null;
    }

    public DistributionRes approveOrder(int id, DistributionApprovalReq req) {
        Optional<Distribution> opt = distributionRepo.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Distribution not found with id: " + id);
        }

        Distribution distribution = opt.get();

        if (!"PENDING".equals(distribution.getStatus())) {
            throw new RuntimeException("Invalid status. Expected PENDING, got: " + distribution.getStatus());
        }

        // Update based on decision
        distribution.setStatus(req.getDecision()); // "CONFIRMED" or "CANCELED"
        distribution.setEvmNotes(req.getEvmNotes());

        distributionRepo.save(distribution);
        return convertToRes(distribution);
    }

    public DistributionRes planDelivery(int id, DistributionPlanningReq req) {
        Optional<Distribution> opt = distributionRepo.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Distribution not found with id: " + id);
        }

        Distribution distribution = opt.get();

        if (!"CONFIRMED".equals(distribution.getStatus())) {
            throw new RuntimeException("Invalid status. Expected CONFIRMED, got: " + distribution.getStatus());
        }

        // Set planning details
        distribution.setEstimatedDeliveryDate(req.getEstimatedDeliveryDate());
        if (req.getEvmNotes() != null) {
            distribution.setEvmNotes(req.getEvmNotes());
        }
        distribution.setStatus("PLANNED");

        distributionRepo.save(distribution);
        return convertToRes(distribution);
    }

    public DistributionRes confirmReceived(int id, DistributionCompletionReq req) {
        Optional<Distribution> opt = distributionRepo.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Distribution not found with id: " + id);
        }

        Distribution distribution = opt.get();

        if (!"PLANNED".equals(distribution.getStatus())) {
            throw new RuntimeException("Invalid status. Expected PLANNED, got: " + distribution.getStatus());
        }

        // Set completion details
        distribution.setReceivedQuantity(req.getReceivedQuantity());
        distribution.setActualDeliveryDate(req.getActualDeliveryDate());
        distribution.setFeedback(req.getFeedback());
        distribution.setStatus("COMPLETED");

        distributionRepo.save(distribution);
        return convertToRes(distribution);
    }

    public DistributionRes createDistribution(DistributionReq req) {
        Distribution distribution = new Distribution();
        Distribution distribution1 = convertToEntity(distribution, req);
        distributionRepo.save(distribution1);
        return convertToRes(distribution1);
    }

    public DistributionRes updateDistribution(int id, DistributionReq req) {
        Optional<Distribution> distribution = distributionRepo.findById(id);
        if (distribution.isPresent()) {
            Distribution distribution1 = convertToEntity(distribution.get(), req);
            distributionRepo.save(distribution1);
            return convertToRes(distribution1);
        }
        return null;
    }

    public boolean deleteDistribution(int id) {
        if (distributionRepo.existsById(id)) {
            distributionRepo.deleteById(id);
            return true;
        }
        return false;
    }

    private Distribution convertToEntity(Distribution distribution ,DistributionReq req) {
        if (distribution != null){
            if (!req.getProductId().isEmpty()){
                List<Product> validProducts = new ArrayList<>();
                for (Integer Req : req.getProductId()) {
                    Optional<Product> productOpt = productRepo.findById(Req);
                    if (productOpt.isPresent()) {
                        validProducts.add(productOpt.get());
                    }
                }
                if (!validProducts.isEmpty()){
                    distribution.setProducts(validProducts);
                }
            }
            if (req.getDealerId() > 0){
                Optional<Dealer> dealer = dealerRepo.findById(req.getDealerId());
                if (dealer.isPresent()){
                    distribution.setDealer(dealer.get());
                }
            }
            if (req.getContractId() > 0){
                Optional<Contract> contract = contractRepo.findById(req.getContractId());
                if (contract.isPresent()){
                    distribution.setContract(contract.get());
                }
            }
            return distribution;
        }
        return null;
    }

    private DistributionRes convertToRes(Distribution distribution) {
        DistributionRes res = new DistributionRes();


        res.setId(distribution.getId());
        res.setStatus(distribution.getStatus());
        if (distribution.getDealer() != null) {
            DealerRes dealerRes = dealerService.converttoRes(distribution.getDealer());
            res.setDealer(dealerRes);
        }
        if (distribution.getContract() != null) {
            res.setContractId(distribution.getContract().getId());
        }
        if (!distribution.getProducts().isEmpty()){
            List<ProductRes> validProducts = new ArrayList<>();
            for (Product product : distribution.getProducts()) {
                Optional<Product> productOpt = productRepo.findById(product.getId());
                if (productOpt.isPresent()) {
                    ProductRes productRes = productService.convertToRes(productOpt.get());
                    validProducts.add(productRes);
                }
            }
            if (!validProducts.isEmpty()){
                res.setProducts(validProducts);
            }
        }
        res.setInvitationMessage(distribution.getInvitationMessage());
        res.setDealerNotes(distribution.getDealerNotes());
        res.setEvmNotes(distribution.getEvmNotes());
        res.setFeedback(distribution.getFeedback());

        // Set timeline - CHỈ 2 FIELD ĐANG DÙNG
        res.setCreatedAt(distribution.getCreatedAt());
        res.setInvitedAt(distribution.getInvitedAt());
        res.setDeadline(distribution.getDeadline());
        res.setRequestedDeliveryDate(distribution.getRequestedDeliveryDate());
        res.setEstimatedDeliveryDate(distribution.getEstimatedDeliveryDate());
        res.setActualDeliveryDate(distribution.getActualDeliveryDate());
        res.setRequestedQuantity(distribution.getRequestedQuantity());
        res.setReceivedQuantity(distribution.getReceivedQuantity());
        return res;
    }
}
