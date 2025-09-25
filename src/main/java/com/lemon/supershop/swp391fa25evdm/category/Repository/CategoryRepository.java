package com.lemon.supershop.swp391fa25evdm.category.Repository;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Find by name (exact match)
    Optional<Category> findByNameIgnoreCase(String name);

    // Find by name containing (partial match)
    List<Category> findByNameContainingIgnoreCase(String name);

    // Find by brand
    List<Category> findByBrandIgnoreCase(String brand);

    // Find by status
    List<Category> findByStatus(String status);

    // Find special categories
    List<Category> findByIsSpecialTrue();

    // Find by warranty greater than
    List<Category> findByWarrantyGreaterThan(Integer warranty);

    // Find by price range
    List<Category> findByBasePriceBetween(Double minPrice, Double maxPrice);

    // Find active categories
    @Query("SELECT c FROM Category c WHERE c.status = 'ACTIVE'")
    List<Category> findActiveCategories();

    // Complex filter query with proper field names
    @Query("SELECT c FROM Category c WHERE " +
            "(:id IS NULL OR c.id = :id) AND " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:brand IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) AND " +
            "(:version IS NULL OR LOWER(c.version) LIKE LOWER(CONCAT('%', :version, '%'))) AND " +
            "(:type IS NULL OR LOWER(c.type) LIKE LOWER(CONCAT('%', :type, '%'))) AND " +
            "(:minBattery IS NULL OR c.battery >= :minBattery) AND " +
            "(:maxBattery IS NULL OR c.battery <= :maxBattery) AND " +
            "(:minRange IS NULL OR c.range >= :minRange) AND " +
            "(:maxRange IS NULL OR c.range <= :maxRange) AND " +
            "(:minHp IS NULL OR c.hp >= :minHp) AND " +
            "(:maxHp IS NULL OR c.hp <= :maxHp) AND " +
            "(:minTorque IS NULL OR c.torque >= :minTorque) AND " +
            "(:maxTorque IS NULL OR c.torque <= :maxTorque) AND " +
            "(:minBasePrice IS NULL OR c.basePrice >= :minBasePrice) AND " +
            "(:maxBasePrice IS NULL OR c.basePrice <= :maxBasePrice) AND " +
            "(:minWarranty IS NULL OR c.warranty >= :minWarranty) AND " +
            "(:maxWarranty IS NULL OR c.warranty <= :maxWarranty) AND " +
            "(:isSpecial IS NULL OR c.isSpecial = :isSpecial) AND " +
            "(:status IS NULL OR c.status = :status)")
    List<Category> filterCategories(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("version") String version,
            @Param("type") String type,
            @Param("minBattery") Double minBattery,
            @Param("maxBattery") Double maxBattery,
            @Param("minRange") Integer minRange,
            @Param("maxRange") Integer maxRange,
            @Param("minHp") Integer minHp,
            @Param("maxHp") Integer maxHp,
            @Param("minTorque") Integer minTorque,
            @Param("maxTorque") Integer maxTorque,
            @Param("minBasePrice") Double minBasePrice,
            @Param("maxBasePrice") Double maxBasePrice,
            @Param("minWarranty") Integer minWarranty,
            @Param("maxWarranty") Integer maxWarranty,
            @Param("isSpecial") Boolean isSpecial,
            @Param("status") String status
    );
}
