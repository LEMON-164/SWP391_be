package com.lemon.supershop.swp391fa25evdm.dealer.service;

import com.lemon.supershop.swp391fa25evdm.dealer.model.dto.DealerReq;
import com.lemon.supershop.swp391fa25evdm.dealer.model.dto.DealerRes;
import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;
import com.lemon.supershop.swp391fa25evdm.dealer.model.enums.DealerStatus;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DealerService {

    @Autowired
    private DealerRepo dealerRepo;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // Hợp lệ cho VN: 10-digit bắt đầu 03|05|07|08|09 OR old 11-digit 01(2|6|8|9)
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(?:(?:03|05|07|08|09)\\d{8}|01(?:2|6|8|9)\\d{8})$");

    public List<DealerRes> getAllDealers() {
        return dealerRepo.findAll().stream().map(dealer -> {
            return convertDealertoDealerRes(dealer);
        }).collect(Collectors.toList());
    }

    public DealerRes getDealer(int id) {
        Optional<Dealer> dealer = dealerRepo.findById(id);
        if (dealer.isPresent()) {
            return  convertDealertoDealerRes(dealer.get());
        } else {
            return null;
        }
    }

    public List<DealerRes> searchDealerbyName(String name) {
        return dealerRepo.findByNameContainingIgnoreCase(name).stream().map(dealer -> {
            return convertDealertoDealerRes(dealer);
        }).collect(Collectors.toList());
    }

    public List<DealerRes> searchDealerbyAddress(String address) {
        return dealerRepo.findByAddressContainingIgnoreCase(address).stream().map(dealer -> {
            return convertDealertoDealerRes(dealer);
        }).collect(Collectors.toList());
    }

    public DealerRes registerDealer(DealerReq dto) {
        Dealer dealer = new Dealer();
        if (dto.getName() !=  null){
            dealer.setName(dto.getName());
        }
        if (dto.getAddress() !=  null){
            dealer.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null && PHONE_PATTERN.matcher(dto.getPhone()).matches()) {
            dealer.setPhone(dto.getPhone());
        }
        if (dto.getEmail() != null && EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            dealer.setEmail(dto.getEmail());
        }
        if (dto.getTaxcode() != null){
            dealer.setTaxcode(dto.getTaxcode());
        }
        dealer.setStatus(DealerStatus.ACTIVE);
        dealerRepo.save(dealer);
        return  convertDealertoDealerRes(dealer);
    }

    public DealerRes updateDealer(int id, DealerReq dto) {
        Dealer dealer = dealerRepo.findById(id).get();
        if (dealer != null) {
            if (dto.getName() !=  null){
                dealer.setName(dto.getName());
            }
            if (dto.getAddress() !=  null){
                dealer.setAddress(dto.getAddress());
            }
            if (dto.getPhone() != null && PHONE_PATTERN.matcher(dto.getPhone()).matches()) {
                dealer.setPhone(dto.getPhone());
            }
            if (dto.getEmail() != null && EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
                dealer.setEmail(dto.getEmail());
            }

            dealerRepo.save(dealer);
            return  convertDealertoDealerRes(dealer);
        }
        return null;
    }
    @Transactional
    public void removeDealer(int id) {
        Optional<Dealer> dealer = dealerRepo.findById(id);
        if (dealer.isPresent()) {
            dealerRepo.clearDealerFromUsers(dealer.get().getId());
            dealerRepo.delete(dealer.get());
        }
    }

    public DealerRes convertDealertoDealerRes(Dealer dealer){
        DealerRes dto = new DealerRes();
        if (dealer != null) {
            dto.setId(dealer.getId());
            if (dealer.getName() != null) {
                dto.setName(dealer.getName());
            }
            if (dealer.getAddress() != null) {
                dto.setAddress(dealer.getAddress());
            }
            if (dealer.getPhone() != null) {
                dto.setPhone(dealer.getPhone());
            }
            if (dealer.getEmail() != null) {
                dto.setEmail(dealer.getEmail());
            }
            if (dealer.getTaxcode() != null) {
                dto.setTaxcode(dealer.getTaxcode());
            }
            if (dealer.getStatus() != null) {
                dto.setStatus(dealer.getStatus());
            }
            if (dealer.getCreateAt() != null) {
                dto.setCreationDate(dealer.getCreateAt());
            }
        }
        return dto;
    }
}
