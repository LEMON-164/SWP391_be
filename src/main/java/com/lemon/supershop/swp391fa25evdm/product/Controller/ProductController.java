package com.lemon.supershop.swp391fa25evdm.product.controller;

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

import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductReq;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductRes;
import com.lemon.supershop.swp391fa25evdm.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/listProducts")
    public ResponseEntity<List<ProductRes>> getAllProducts() {
        List<ProductRes> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ProductRes> getProductById(@PathVariable int id) {
        ProductRes product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<ProductRes> getProductByName(@PathVariable String name) {
        ProductRes product = productService.getProductByName(name);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search/vin/{vinNum}")
    public ResponseEntity<ProductRes> getProductByVinNum(@PathVariable String vinNum) {
        ProductRes product = productService.getProductByVinNum(vinNum);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search/engine/{engineNum}")
    public ResponseEntity<ProductRes> getProductByEngineNum(@PathVariable String engineNum) {
        ProductRes product = productService.getProductByEngineNum(engineNum);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search/category/{categoryId}")
    public ResponseEntity<List<ProductRes>> getProductsByCategoryId(@PathVariable Integer categoryId) {
        List<ProductRes> products = productService.getProductByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody ProductReq productReq) {
        productService.addProduct(productReq);
        return ResponseEntity.ok("Product added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody ProductReq productReq) {
        productService.updateProduct(id, productReq);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
