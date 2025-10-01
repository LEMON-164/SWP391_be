package com.lemon.supershop.swp391fa25evdm.product.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;
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
        return productRepo.findAll().stream().map(product -> {
            return convertToRes(product);
        }).collect(Collectors.toList());
    }

    public ProductRes findProductById(int id){
        Optional<Product> productOpt = productRepo.findById(id);
        if(productOpt.isPresent()){
            return convertToRes(productOpt.get());
        } else {
            return null;
        }
    }

    public boolean deleteProductById(int id){
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public ProductRes updateProduct(int id, ProductReq productReq){
        Optional<Product> productOpt = productRepo.findById(id);
        if(productOpt.isPresent()){
            Product product = productOpt.get();
            if (productReq.getName() != null){
                product.setName(productReq.getName());
            }
            if (productReq.getVinNum() != null){
                product.setVinNum(productReq.getVinNum());
            }
            if (productReq.getEngineNum() != null){
                product.setEngineNum(productReq.getEngineNum());
            }
            if (productReq.getManufacture_date() != null){
                product.setManufacture_date(productReq.getManufacture_date());
            }
            if (productReq.getDealerPrice() > 0){
                product.setDealerPrice(productReq.getDealerPrice());
            }
            if (productReq.getImage() != null){
                product.setImage(productReq.getImage());
            }
            if (productReq.getDescription() != null){
                product.setDescription(productReq.getDescription());
            }
            if (productReq.getStatus() != null){
                product.setStatus(productReq.getStatus());
            }
            if (productReq.getCategoryId() > 0){
                Optional<Category> category = categoryRepository.findById(productReq.getCategoryId());
                if (category.isPresent()){
                    product.setCategory(category.get());
                }
            }
            if (productReq.getDealerCategoryId() > 0){
                Optional<DealerCategory> dealerCategory = dealerCategoryRepository.findById(productReq.getDealerCategoryId());
                if (dealerCategory.isPresent()){
                    product.setDealerCategory(dealerCategory.get());
                }
            }
            productRepo.save(product);
            return convertToRes(product);
        } else {
            return null;
        }
    }

    // New methods accepting ProductReq
    public ProductRes createProduct(ProductReq productReq) {
        Product product = new Product();
        if (productReq != null) {
            if (productReq.getName() != null){
                product.setName(productReq.getName());
            }
            if (productReq.getVinNum() != null){
                product.setVinNum(productReq.getVinNum());
            }
            if (productReq.getEngineNum() != null){
                product.setEngineNum(productReq.getEngineNum());
            }
            if (productReq.getManufacture_date() != null){
                product.setManufacture_date(productReq.getManufacture_date());
            }
            if (productReq.getDealerPrice() > 0){
                product.setDealerPrice(productReq.getDealerPrice());
            }
            if (productReq.getImage() != null){
                product.setImage(productReq.getImage());
            }
            if (productReq.getDescription() != null){
                product.setDescription(productReq.getDescription());
            }
            if (productReq.getStatus() != null){
                product.setStatus(productReq.getStatus());
            }
            if (productReq.getCategoryId() > 0){
                Optional<Category> category = categoryRepository.findById(productReq.getCategoryId());
                if (category.isPresent()){
                    product.setCategory(category.get());
                }
            }
            if (productReq.getDealerCategoryId() > 0){
                Optional<DealerCategory> dealerCategory = dealerCategoryRepository.findById(productReq.getDealerCategoryId());
                if (dealerCategory.isPresent()){
                    product.setDealerCategory(dealerCategory.get());
                }
            }
        }
        return convertToRes(productRepo.save(product));
    }

    public List<ProductRes> getProductByCategory(int id){
        List<Product> products = productRepo.findByCategoryId(id);
        return products.stream().map(this::convertToRes).toList();
    }

    public List<ProductRes> getProductByCategoryId(int categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            return productRepo.findByCategoryId(categoryId).stream().map(product -> {
                return convertToRes(product);
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public List<ProductRes> getProductByVinNum(String vinNum){
        return productRepo.findByVinNumContainingIgnoreCase(vinNum).stream().map(product -> {
            return convertToRes(product);
        }).collect(Collectors.toList());
    }

    public List<ProductRes> getProductByName(String name){
        return productRepo.findByNameContainingIgnoreCase(name).stream().map(product -> {
            return convertToRes(product);
        }).collect(Collectors.toList());
    }

    public List<ProductRes> getProductByEngineNum(String engineNum){
        return productRepo.findByEngineNumContainingIgnoreCase(engineNum).stream().map(product -> {
            return convertToRes(product);
        }).collect(Collectors.toList());
    }

    private ProductRes convertToRes(Product product) {
        ProductRes productRes = new ProductRes();
        if (product != null) {
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
            if (product.getManufacture_date() != null){
                productRes.setManufacture_date(product.getManufacture_date());
            }
            if (product.getDealerPrice() > 0){
                productRes.setPrice(product.getDealerPrice());
            }
            if (product.getImage() != null) {
                productRes.setImage(product.getImage());
            }
            if (product.getDescription() != null) {
                productRes.setDescription(product.getDescription());
            }
            if (product.getStatus() != null){
                productRes.setStatus(product.getStatus());
            }
            if (product.getCategory() != null) {
                productRes.setCategoryId(product.getCategory().getId());
            }
            if (product.getDealerCategory() != null) {
                productRes.setDealerCategoryId(product.getDealerCategory().getId());
            }
        }
        return productRes;
    }
}
