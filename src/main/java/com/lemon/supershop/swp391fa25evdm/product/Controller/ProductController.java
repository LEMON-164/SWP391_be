package com.lemon.supershop.swp391fa25evdm.product.Controller;

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

import com.lemon.supershop.swp391fa25evdm.product.Service.ProductService;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductReq;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductRes;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/listProducts")
    public ResponseEntity<List<ProductRes>> getAllProducts() {
        List<ProductRes> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
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

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductRes>> getProductsByCategoryId(@PathVariable Integer categoryId) {
        List<ProductRes> products = productService.getProductByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody ProductReq productReq) {
        Product product = convertReqToEntity(productReq);
        ProductRes createdProduct = productService.createProduct(product);
        if (createdProduct != null) {
            return ResponseEntity.ok("Product created successfully");
        } else {
            return ResponseEntity.badRequest().body("Product already exists or invalid data");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody ProductReq productReq) {
        Product product = convertReqToEntity(productReq);
        product.setId(id);
        ProductRes updatedProduct = productService.updateProduct(product);
        if (updatedProduct != null) {
            return ResponseEntity.ok("Product updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Product not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        boolean deleted = productService.deleteProductById(id);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Product not found");
        }
    }

    // Helper method to convert ProductReq to Product entity
    private Product convertReqToEntity(ProductReq productReq) {
        Product product = new Product();
        product.setName(productReq.getName());
        product.setVinNum(productReq.getVinNum());
        product.setEngineNum(productReq.getEngineNum());
        product.setDescription(productReq.getDescription());
        product.setStatus(productReq.getStatus());
        
        // Set image
        if (productReq.getImage() != null && !productReq.getImage().isEmpty()) {
            product.setImage(productReq.getImage());
        }
        
        // Set dealer price
        if (productReq.getDealerPrice() > 0) {
            product.setDealerPrice(productReq.getDealerPrice());
        }
        
        // Handle date conversion - assuming manufacture_date is in format "MM/dd/yyyy"
        if (productReq.getManufacture_date() != null && !productReq.getManufacture_date().isEmpty()) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("M/d/yyyy");
                java.util.Date date = sdf.parse(productReq.getManufacture_date());
                product.setManufacture_date(date);
            } catch (java.text.ParseException e) {
                System.err.println("Error parsing date: " + productReq.getManufacture_date());
                // Try alternative format
                try {
                    java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("MM/dd/yyyy");
                    java.util.Date date2 = sdf2.parse(productReq.getManufacture_date());
                    product.setManufacture_date(date2);
                } catch (java.text.ParseException e2) {
                    System.err.println("Error parsing date with alternative format: " + productReq.getManufacture_date());
                }
            }
        }
        
        // Handle category ID conversion using EntityManager reference
        if (productReq.getCategoryId() != null && !productReq.getCategoryId().isEmpty()) {
            try {
                int categoryId = Integer.parseInt(productReq.getCategoryId());
                // Use EntityManager to get reference to existing Category
                com.lemon.supershop.swp391fa25evdm.category.model.entity.Category category = 
                    entityManager.getReference(com.lemon.supershop.swp391fa25evdm.category.model.entity.Category.class, categoryId);
                product.setCategory(category);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing categoryId: " + productReq.getCategoryId());
            }
        }
        
        // Handle dealer category ID conversion using EntityManager reference
        if (productReq.getDealerCategoryId() != null && !productReq.getDealerCategoryId().isEmpty()) {
            // Use EntityManager to get reference to existing DealerCategory
            com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory dealerCategory = 
                entityManager.getReference(com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory.class, productReq.getDealerCategoryId());
            product.setDealerCategory(dealerCategory);
        }
        
        return product;
    }
}
