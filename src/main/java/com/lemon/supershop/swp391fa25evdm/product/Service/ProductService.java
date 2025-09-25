package com.lemon.supershop.swp391fa25evdm.product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.Repository.CategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.Repository.DealerCategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductReq;
import com.lemon.supershop.swp391fa25evdm.product.model.dto.ProductRes;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import com.lemon.supershop.swp391fa25evdm.product.repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DealerCategoryRepository dealerCategoryRepository;

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

    public ProductRes updateProduct(Product product){
        if (!productRepo.existsById(product.getId())) {
            return null; // Product not found
        }
        Product updatedProduct = productRepo.save(product);
        return convertToRes(updatedProduct);
    }

    // New methods accepting ProductReq
    public ProductRes createProduct(ProductReq productReq) {
        Product product = convertReqToEntity(productReq);
        if (product.getId() != 0 && productRepo.existsById(product.getId())) {
            return null; // Product already exists
        }
        Product savedProduct = productRepo.save(product);
        return convertToRes(savedProduct);
    }

    public ProductRes updateProduct(int id, ProductReq productReq) {
        if (!productRepo.existsById(id)) {
            return null; // Product not found
        }
        Product product = convertReqToEntity(productReq);
        product.setId(id);
        return updateProduct(product); // Reuse existing method
    }

    public List<ProductRes> getProductByCategory(Category category){
        List<Product> products = productRepo.findByCategory(category);
        return products.stream().map(this::convertToRes).toList();
    }

    public List<ProductRes> getProductByCategoryId(Integer categoryId){
        List<Product> products = productRepo.findByCategoryId(categoryId);
        return products.stream().map(this::convertToRes).toList();
    }

    public ProductRes getProductByVinNum(String vinNum){
        Optional<Product> productOpt = productRepo.findByVinNumIgnoreCase(vinNum);
        return productOpt.map(this::convertToRes).orElse(null);
    }

    public ProductRes getProductByName(String name){
        Optional<Product> productOpt = productRepo.findByNameIgnoreCase(name);
        return productOpt.map(this::convertToRes).orElse(null);
    }

    public ProductRes getProductByEngineNum(String engineNum){
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
                product.getCategory(),
                product.getDealerCategory() != null ? String.valueOf(product.getDealerCategory().getId()) : null
        );
    }

    // Convert ProductReq to Product entity using Repository
    private Product convertReqToEntity(ProductReq productReq) {
        Product product = new Product();
        product.setName(productReq.getName());
        product.setVinNum(productReq.getVinNum());
        product.setEngineNum(productReq.getEngineNum());
        product.setDescription(productReq.getDescription());
        product.setStatus(productReq.getStatus());
        product.setImage(productReq.getImage());
        product.setDealerPrice(productReq.getDealerPrice());
        product.setManufacture_date(productReq.getManufacture_date());
        categoryRepository.findById(productReq.getCategoryId()).ifPresent(product::setCategory);
        dealerCategoryRepository.findById(productReq.getDealerCategoryId()).ifPresent(product::setDealerCategory);
        
        return product;
    }
}
