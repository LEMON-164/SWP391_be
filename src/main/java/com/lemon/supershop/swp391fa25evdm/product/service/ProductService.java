package com.lemon.supershop.swp391fa25evdm.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;
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
        Product newProduct = new Product();
        if (productReq.getName() != null){
                newProduct.setName(productReq.getName());
            }
            if (productReq.getVinNum() != null){
                newProduct.setVinNum(productReq.getVinNum());
            }
            if (productReq.getEngineNum() != null){
                newProduct.setEngineNum(productReq.getEngineNum());
            }
            if (productReq.getDescription() != null){
                newProduct.setDescription(productReq.getDescription());
            }
            if (productReq.getStatus() != null){
                newProduct.setStatus(productReq.getStatus());
            }
            if (productReq.getImage() != null){
                newProduct.setImage(productReq.getImage());
            }
            if (productReq.getManufacture_date() != null){
                newProduct.setManufacture_date(productReq.getManufacture_date());
            }
            if (productReq.getDealerPrice() > 0){
                newProduct.setDealerPrice(productReq.getDealerPrice());
            }
            if (productReq.getCategoryId() > 0){
                categoryRepository.findById(productReq.getCategoryId()).ifPresent(newProduct::setCategory);
            }
            if (productReq.getDealerCategoryId() > 0){
                dealerCategoryRepository.findById(productReq.getDealerCategoryId()).ifPresent(newProduct::setDealerCategory);
            }
        productRepo.save(newProduct);
        return convertToRes(newProduct);
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
        if (product != null) {
            ProductRes productRes = new ProductRes();
            productRes.setId(product.getId());
            if (product.getName() != null) {
                productRes.setName(product.getName());
            }
            if (product.getVinNum() != null) {
                productRes.setVinNum(product.getVinNum());
            }
            if (product.getEngineNum() != null) {
                productRes.setEngineNum(product.getEngineNum());
            }
            if (product.getDescription() != null) {
                productRes.setDescription(product.getDescription());
            }
            if (product.getStatus() != null) {
                productRes.setStatus(product.getStatus());
            }
            if (product.getImage() != null) {
                productRes.setImage(product.getImage());
            }
            if (product.getManufacture_date() != null){
                productRes.setManufacture_date(product.getManufacture_date());
            }
            if (product.getCategory() != null) {
                Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
                if (category.isPresent()) {
                    productRes.setCategoryId(category.get().getId());
                }
            }
            if (product.getDealerCategory() != null) {
                Optional<DealerCategory> category = dealerCategoryRepository.findById(product.getDealerCategory().getId());
                if (category.isPresent()) {
                    productRes.setDealerCategoryId(category.get().getId());
                }
            }
            return productRes;
        }
        return null;
    }

    // Convert ProductReq to Product entity using Repository
    private Product convertReqToEntity(Product product, ProductReq productReq) {
        if (product != null || productReq != null){
            if (productReq.getName() != null){
                product.setName(productReq.getName());
            }
            if (productReq.getVinNum() != null){
                product.setVinNum(productReq.getVinNum());
            }
            if (productReq.getEngineNum() != null){
                product.setEngineNum(productReq.getEngineNum());
            }
            if (productReq.getDescription() != null){
                product.setDescription(productReq.getDescription());
            }
            if (productReq.getStatus() != null){
                product.setStatus(productReq.getStatus());
            }
            if (productReq.getImage() != null){
                product.setImage(productReq.getImage());
            }
            if (productReq.getManufacture_date() != null){
                product.setManufacture_date(productReq.getManufacture_date());
            }
            if (productReq.getDealerPrice() > 0){
                product.setDealerPrice(productReq.getDealerPrice());
            }
            if (productReq.getCategoryId() > 0){
                categoryRepository.findById(productReq.getCategoryId()).ifPresent(product::setCategory);
            }
            if (productReq.getDealerCategoryId() > 0){
                dealerCategoryRepository.findById(productReq.getDealerCategoryId()).ifPresent(product::setDealerCategory);
            }
            return product;
        }
        return null;
    }
}
