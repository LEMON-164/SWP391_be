package com.lemon.supershop.swp391fa25evdm.preorder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.preorder.model.dto.PreOrderRes;
import com.lemon.supershop.swp391fa25evdm.preorder.service.PreOrderService;

@RestController
@RequestMapping("/api/preorders")
@CrossOrigin(origins = "*")
public class PreOrderController {
    
    @Autowired
    private PreOrderService preOrderService;

    @GetMapping ("/listPreOrders")
    public ResponseEntity<List<PreOrderRes>> getAllPreOrders() {
        List<PreOrderRes> preOrders = preOrderService.getAllPreOrders();
        return ResponseEntity.ok(preOrders);
    }

    @GetMapping ("/listPreOrdersByUserId")
    public ResponseEntity<List<PreOrderRes>> getPreOrdersByUserId(@RequestParam int userId) {
        List<PreOrderRes> preOrders = preOrderService.getPreOrdersByUserId(userId);
        return ResponseEntity.ok(preOrders);
    }

    @GetMapping ("/listPreOrdersByStatus")
    public ResponseEntity<List<PreOrderRes>> getPreOrdersByStatus(@RequestParam String status) {
        List<PreOrderRes> preOrders = preOrderService.getPreOrdersByStatus(status);
        return ResponseEntity.ok(preOrders);
    }

    @GetMapping ("/listPreOrdersByProductId")
    public ResponseEntity<List<PreOrderRes>> getPreOrdersByProductId(@RequestParam int productId) {
        List<PreOrderRes> preOrders = preOrderService.getPreOrdersByProductId(productId);
        return ResponseEntity.ok(preOrders);
    }

    @PostMapping ("/createPreOrder")
    public ResponseEntity<String> createPreOrder (@RequestBody PreOrderRes preOrderRes) {
        preOrderService.createPreOrder(preOrderRes);
        return ResponseEntity.ok("PreOrder created successfully");
    }

    @PutMapping ("/updatePreOrder/{id}")
    public ResponseEntity<String> updatePreOrder (@PathVariable int id, @RequestBody PreOrderRes preOrderRes) {
        preOrderService.updatePreOrder(id, preOrderRes);
        return ResponseEntity.ok("PreOrder updated successfully");
    }

    @DeleteMapping ("/deletePreOrder/{id}")
    public ResponseEntity<String> deletePreOrder (@PathVariable int id) {
        preOrderService.deletePreOrder(id);
        return ResponseEntity.ok("PreOrder deleted successfully");
    }

}
