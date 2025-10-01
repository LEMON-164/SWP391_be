package com.lemon.supershop.swp391fa25evdm.preorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.preorder.model.dto.PreOrderRes;
import com.lemon.supershop.swp391fa25evdm.preorder.model.entity.PreOrder;
import com.lemon.supershop.swp391fa25evdm.preorder.repository.PreOrderRepo;
import com.lemon.supershop.swp391fa25evdm.product.repository.ProductRepo;
import com.lemon.supershop.swp391fa25evdm.user.repository.UserRepo;

@Service
public class PreOrderService {
    
    @Autowired
    private PreOrderRepo preOrderRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;

    public List<PreOrderRes> getAllPreOrders() {
        List<PreOrder> preOrders = preOrderRepo.findAll();
        return preOrders.stream().map(this::convertToRes).toList();
    }

    public List<PreOrderRes> getPreOrdersByUserId(int userId) {
        List<PreOrder> preOrders = preOrderRepo.findByUserId(userId);
        return preOrders.stream().map(this::convertToRes).toList();
    }

    public List<PreOrderRes> getPreOrdersByStatus(String status) {
        List<PreOrder> preOrders = preOrderRepo.findByStatus(status);
        return preOrders.stream().map(this::convertToRes).toList();
    }

    public List<PreOrderRes> getPreOrdersByProductId(int productId) {
        List<PreOrder> preOrders = preOrderRepo.findByProductId(productId);
        return preOrders.stream().map(this::convertToRes).toList();
    }

    public void createPreOrder (PreOrderRes preOrderRes) {
        PreOrder preOrder = convertToEntity(preOrderRes);
        preOrderRepo.save(preOrder);
    }

    public void updatePreOrder (int id, PreOrderRes preOrderRes) {
        PreOrder existingPreOrder = preOrderRepo.findById(id).orElseThrow( () -> new RuntimeException("PreOrder not found"));
        existingPreOrder.setOrderDate(preOrderRes.getOrderDate());
        existingPreOrder.setStatus(preOrderRes.getStatus());
        existingPreOrder.setDeposit(preOrderRes.getDeposit());
        
        if (preOrderRes.getUserId() != 0) {
            existingPreOrder.setUser(userRepo.findById(preOrderRes.getUserId()).orElseThrow( () -> new RuntimeException("User not found")));
        }
        if (preOrderRes.getProductId() != 0) {
            existingPreOrder.setProduct(productRepo.findById(preOrderRes.getProductId()).orElseThrow( () -> new RuntimeException("Product not found")));
        }

        preOrderRepo.save(existingPreOrder);

    }

    public void deletePreOrder (int id) {
        PreOrder existingPreOrder = preOrderRepo.findById(id).orElseThrow( () -> new RuntimeException("PreOrder not found"));
        preOrderRepo.delete(existingPreOrder);
    }



    private PreOrder convertToEntity (PreOrderRes preOrderRes) {
        PreOrder preOrder = new PreOrder();
        preOrder.setOrderDate(preOrderRes.getOrderDate());
        preOrder.setStatus(preOrderRes.getStatus());
        preOrder.setDeposit(preOrderRes.getDeposit());

        if (preOrderRes.getUserId() != 0) {
            preOrder.setUser(userRepo.findById(preOrderRes.getUserId()).orElseThrow( () -> new RuntimeException("User not found")));
        }
        if (preOrderRes.getProductId() != 0) {
            preOrder.setProduct(productRepo.findById(preOrderRes.getProductId()).orElseThrow( () -> new RuntimeException("Product not found")));
        }

        return preOrder;
    }

    private PreOrderRes convertToRes (PreOrder preOrder) {
        PreOrderRes preOrderRes = new PreOrderRes();
        preOrderRes.setOrderDate(preOrder.getOrderDate());
        preOrderRes.setStatus(preOrder.getStatus());
        preOrderRes.setDeposit(preOrder.getDeposit());

        if (preOrder.getUser() != null) {
            preOrderRes.setUserId(preOrder.getUser().getId());
        }

        if (preOrder.getProduct() != null) {
            preOrderRes.setProductId(preOrder.getProduct().getId());
        }

        return preOrderRes;
    }
}
