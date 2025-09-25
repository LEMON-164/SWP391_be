package com.lemon.supershop.swp391fa25evdm.product.repository;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    // Efficient query methods instead of findAll() + filtering
    Optional<Product> findByVinNumIgnoreCase(String vinNum);
    Optional<Product> findByEngineNumIgnoreCase(String engineNum);
    Optional<Product> findByNameIgnoreCase(String name);
    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Integer categoryId);
}
