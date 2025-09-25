package com.lemon.supershop.swp391fa25evdm.category.Repository;

import com.lemon.supershop.swp391fa25evdm.category.model.entity.DealerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerCategoryRepository extends JpaRepository<DealerCategory,Integer> {

}
