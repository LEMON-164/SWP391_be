package com.lemon.supershop.swp391fa25evdm.product.repository;

import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
}
