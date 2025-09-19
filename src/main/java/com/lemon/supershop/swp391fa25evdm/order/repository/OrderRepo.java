package com.lemon.supershop.swp391fa25evdm.order.repository;

import com.lemon.supershop.swp391fa25evdm.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
