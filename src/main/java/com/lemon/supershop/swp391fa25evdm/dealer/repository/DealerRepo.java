package com.lemon.supershop.swp391fa25evdm.dealer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;
@Repository
public interface DealerRepo extends JpaRepository<Dealer, Integer> {

    Optional<Dealer> findByNameContainingIgnoreCase(String name);
    List<Dealer> findByAddressContainingIgnoreCase(String address);
}
