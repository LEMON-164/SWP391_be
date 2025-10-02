package com.lemon.supershop.swp391fa25evdm.product.repository;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    // Efficient query methods instead of findAll() + filtering
    List<Product> findByVinNumContainingIgnoreCase(String vinNum);
    List<Product> findByEngineNumContainingIgnoreCase(String engineNum);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryId(int id);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Integer categoryId);
}
