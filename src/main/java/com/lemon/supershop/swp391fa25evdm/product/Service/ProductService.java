package com.lemon.supershop.swp391fa25evdm.product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductRes;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import com.lemon.supershop.swp391fa25evdm.product.repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductRes> findAllProducts(){
        List<Product> products = productRepo.findAll();
        return products.stream().map(this::convertToRes).toList();
    }

    public ProductRes findProductById(int id){
        Optional<Product> productOpt = productRepo.findById(id);
        return productOpt.map(this::convertToRes).orElse(null);
    }

    public boolean deleteProductById(int id){
        if (!productRepo.existsById(id)) {
            return false;
        }
        productRepo.deleteById(id);
        return true;
    }

    public ProductRes createProduct(Product product){
        if (product.getId() != 0 && productRepo.existsById(product.getId())) {
            return null; // Product already exists
        }   
        Product savedProduct = productRepo.save(product);
        return convertToRes(savedProduct);
    }

    public ProductRes updateProduct(Product product){
        if (!productRepo.existsById(product.getId())) {
            return null; // Product not found
        }
        Product updatedProduct = productRepo.save(product);
        return convertToRes(updatedProduct);
    }

    public List<ProductRes> getProductByCategory(Category category){
        // Use efficient repository method instead of findAll + filtering
        List<Product> products = productRepo.findByCategory(category);
        return products.stream().map(this::convertToRes).toList();
    }

    public List<ProductRes> getProductByCategoryId(Integer categoryId){
        // Alternative method using category ID for better performance
        List<Product> products = productRepo.findByCategoryId(categoryId);
        return products.stream().map(this::convertToRes).toList();
    }

    public ProductRes getProductByVinNum(String vinNum){
        // Use efficient repository method instead of findAll + filtering
        Optional<Product> productOpt = productRepo.findByVinNumIgnoreCase(vinNum);
        return productOpt.map(this::convertToRes).orElse(null);
    }

    public ProductRes getProductByName(String name){
        // Use efficient repository method instead of findAll + filtering
        Optional<Product> productOpt = productRepo.findByNameIgnoreCase(name);
        return productOpt.map(this::convertToRes).orElse(null);
    }

    public ProductRes getProductByEngineNum(String engineNum){
        // Use efficient repository method instead of findAll + filtering
        Optional<Product> productOpt = productRepo.findByEngineNumIgnoreCase(engineNum);
        return productOpt.map(this::convertToRes).orElse(null);
    }

    private ProductRes convertToRes(Product product) {
        return new ProductRes(
                product.getId(),
                product.getName(),
                product.getVinNum(),
                product.getEngineNum(),
                product.getManufacture_date(),
                product.getImage(),
                product.getDescription(),
                product.getStatus(),
                product.getCategory() != null ? String.valueOf(product.getCategory().getId()) : null,
                product.getDealerCategory() != null ? String.valueOf(product.getDealerCategory().getId()) : null
        );
    }
}
