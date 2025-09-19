package com.lemon.supershop.swp391fa25evdm.product.repository;

import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
