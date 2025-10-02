package com.lemon.supershop.swp391fa25evdm.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.category.repository.CategoryRepository;
import com.lemon.supershop.swp391fa25evdm.category.repository.DealerCategoryRepository;
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

    public void deleteProductById(int id){
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
        }
    }

    public ProductRes createProduct (ProductReq productReq) {
        Product product = convertReqToEntity(productReq);
        productRepo.save(product);
        return convertToRes(product);
    }

    public ProductRes updateProduct (int id, ProductReq productReq) {
        Optional<Product> existingProductOpt = productRepo.findById(id);
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            existingProduct.setName(productReq.getName());
            existingProduct.setVinNum(productReq.getVinNum());
            existingProduct.setEngineNum(productReq.getEngineNum());
            existingProduct.setDescription(productReq.getDescription());
            existingProduct.setStatus(productReq.getStatus());
            existingProduct.setImage(productReq.getImage());
            existingProduct.setDealerPrice(productReq.getDealerPrice());
            existingProduct.setManufacture_date(productReq.getManufacture_date());
            categoryRepository.findById(productReq.getCategoryId()).ifPresent(existingProduct::setCategory);
            dealerCategoryRepository.findById(productReq.getDealerCategoryId()).ifPresent(existingProduct::setDealerCategory);
            productRepo.save(existingProduct);
            return convertToRes(existingProduct);
        }
        return null;
    }

    public List<ProductRes> getProductByCategoryId(Integer categoryId){
        List<Product> products = productRepo.findByCategoryId(categoryId);
        return products.stream().map(this::convertToRes).toList();
    }

    public List<ProductRes> getProductByVinNum(String vinNum){
        List<Product> productOpt = productRepo.findByVinNumContainingIgnoreCase(vinNum);
        return productOpt.isEmpty() ? null : productOpt.stream().map(this::convertToRes).toList();
    }

    public List<ProductRes> getProductByName(String name){
        List<Product> productOpt = productRepo.findByNameContainingIgnoreCase(name);
        return productOpt.isEmpty() ? null : productOpt.stream().map(this::convertToRes).toList();
    }

    public List<ProductRes> getProductByEngineNum(String engineNum){
        List<Product> productOpt = productRepo.findByEngineNumContainingIgnoreCase(engineNum);
        return productOpt.isEmpty() ? null : productOpt.stream().map(this::convertToRes).toList();
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
                product.getDealerPrice(),
                product.getStatus(),
                product.getCategory() != null ? product.getCategory().getId() : 0,
                product.getDealerCategory() != null ? product.getDealerCategory().getId() : 0
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
