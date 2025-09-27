package com.lemon.supershop.swp391fa25evdm.contract.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.contract.Service.ContractService;
import com.lemon.supershop.swp391fa25evdm.contract.model.dto.ContractReq;
import com.lemon.supershop.swp391fa25evdm.contract.model.dto.ContractRes;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping ("/listContracts")
    public ResponseEntity<List<ContractRes>> getAllContracts() {
        List<ContractRes> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ContractRes> getContractById(@PathVariable int id) {
        ContractRes contract = contractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }

    @GetMapping("/search/userId/{userId}")
    public ResponseEntity<List<ContractRes>> getContractsByUserId(@PathVariable int userId) {
        List<ContractRes> contracts = contractService.getContractsByUserId(userId);
        return ResponseEntity.ok(contracts);
    }  

    @GetMapping("/search/orderId/{orderId}")
    public ResponseEntity<List<ContractRes>> getContractsByOrderId(@PathVariable int orderId) {
        List<ContractRes> contracts = contractService.getContractsByOrderId(orderId);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<ContractRes>> getContractsByStatus(@PathVariable String status) {
        List<ContractRes> contracts = contractService.getContractsByStatus(status);
        return ResponseEntity.ok(contracts);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createContract(@RequestBody ContractReq contractReq) {
        contractService.createContract(contractReq);
        return ResponseEntity.ok("Contract created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateContract(@PathVariable int id, @RequestBody ContractReq contractReq) throws Exception {
        contractService.updateContract(id, contractReq);
        return ResponseEntity.ok("Contract updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContract(@PathVariable int id) {
        contractService.deleteContract(id);
        return ResponseEntity.ok("Contract deleted successfully");
    }

    

}
