package com.lemon.supershop.swp391fa25evdm.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.contract.model.entity.Contract;
import com.lemon.supershop.swp391fa25evdm.contract.repository.ContractRepo;
import com.lemon.supershop.swp391fa25evdm.dealer.model.entity.Dealer;
import com.lemon.supershop.swp391fa25evdm.dealer.repository.DealerRepo;
import com.lemon.supershop.swp391fa25evdm.order.model.dto.request.DeliveryReq;
import com.lemon.supershop.swp391fa25evdm.order.model.dto.request.OrderReq;
import com.lemon.supershop.swp391fa25evdm.order.model.dto.request.UpdateOrderReq;
import com.lemon.supershop.swp391fa25evdm.order.model.dto.response.OrderRes;
import com.lemon.supershop.swp391fa25evdm.order.model.entity.Order;
import com.lemon.supershop.swp391fa25evdm.order.repository.OrderRepo;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import com.lemon.supershop.swp391fa25evdm.product.repository.ProductRepo;
import com.lemon.supershop.swp391fa25evdm.promotion.model.entity.Promotion;
import com.lemon.supershop.swp391fa25evdm.promotion.repository.PromotionRepo;
import com.lemon.supershop.swp391fa25evdm.user.model.entity.User;
import com.lemon.supershop.swp391fa25evdm.user.repository.UserRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private DealerRepo dealerRepo;

    @Autowired
    private ContractRepo contractRepo;

    @Autowired
    private PromotionRepo promotionRepo;

    public List<OrderRes> ListOrderbyUserId(int userId) {
        User user = userRepo.findById(userId).get();
        if (user != null){
            return orderRepo.findByUserId(user.getId()).stream().map(order -> {
                return convertOrderToOrderRes(order);
            }).collect(Collectors.toList());
        }else {
            return null;
        }
    }

    public List<OrderRes> ListAllOrders() {
            return orderRepo.findAll().stream().map(order -> {
                return convertOrderToOrderRes(order);
            }).collect(Collectors.toList());
    }

    public void createOrder(int userId, OrderReq dto) {
        User user = userRepo.findById(userId).get();
        if (user != null){
            Order order = new Order();
            order.setUser(user);
            if (dto.getProductId() > 0 ){
                Product product  = productRepo.findById(dto.getProductId()).get();
                if (product != null){
                    order.setProduct(product);
                    order.setTotal(product.getDealerPrice());
                }
            }
            if (dto.getDealerId() > 0){
                Dealer dealer = dealerRepo.findById(dto.getDealerId()).get();
                if (dealer != null){
                    order.setDealer(dealer);
                    List<Promotion> promotions = promotionRepo.findByDealer_Id(dealer.getId());
                    if (promotions != null){
                        order.setPromotions(promotions);
                    }
                }
            }
            orderRepo.save(order);
        }
    }
    public void createDelivery(int orderId, DeliveryReq dto) {
        Order order = orderRepo.findById(orderId).get();
        if (order != null){
            if (dto.getShip_address() != null){
                order.setShipAddress(dto.getShip_address());
            }
            if (dto.getShip_date() != null){
                order.getShipAt(dto.getShip_date());
            }
            if (dto.getShip_status() != null){
                order.setShipStatus(dto.getShip_status());
            } else {
                order.setShipStatus("Wait for delivery");
            }
        }
    }

    public void updateOrder(int orderId, UpdateOrderReq dto) {
        Order order = orderRepo.findById(orderId).get();
        if (order != null){
            if (dto.getProductId() > 0 ){
                Product product  = productRepo.findById(dto.getProductId()).get();
                if (product != null){
                    order.setProduct(product);
                    order.setTotal(product.getDealerPrice());
                }
            }
            if (dto.getContractId() > 0 ){
                Contract contract = contractRepo.findById(dto.getContractId()).get();
                if (contract != null){
                    order.setContract(contract);
                }
            }
            if (dto.getDealerId() > 0){
                Dealer dealer = dealerRepo.findById(dto.getDealerId()).get();
                if (dealer != null){
                    order.setDealer(dealer);
                    List<Promotion> promotions = promotionRepo.findByDealer_Id(dealer.getId());
                    if (promotions != null){
                        order.setPromotions(promotions);
                    }
                }
            }
            orderRepo.save(order);
        }
    }

    public void updateDelivery(int orderId, DeliveryReq dto) {
        Order order = orderRepo.findById(orderId).get();
        if (order != null){
            if (dto.getShip_address() != null){
                order.setShipAddress(dto.getShip_address());
            }
            if (dto.getShip_date() != null){
                order.getShipAt(dto.getShip_date());
            }
            if (dto.getShip_status() != null){
                order.setShipStatus(dto.getShip_status());
            }
        }
    }

    public void deleteOrder(int orderId) {
        Order order = orderRepo.findById(orderId).get();
        if (order != null){
            contractRepo.delete(order.getContract());
            order.getProduct().getOrders().remove(order);
            productRepo.save(order.getProduct());
            order.getDealer().getOrders().remove(order);
            dealerRepo.save(order.getDealer());
            order.getUser().getOrders().remove(order);
            userRepo.save(order.getUser());
            orderRepo.delete(order);
        }
    }

    public void deleteDelivery(int orderId) {
        Order order = orderRepo.findById(orderId).get();
        if (order != null){
            order.setShipAddress(null);
            order.getShipAt(null);
            order.setShipStatus(null);
        }
    }

    public OrderRes convertOrderToOrderRes(Order order) {
        OrderRes orderRes = new OrderRes();
        if (order != null){
            orderRes.setOrderId(order.getId());
            if (order.getUser() != null){
                orderRes.setCustomerName(order.getUser().getUsername());
            }
            if (order.getContract() != null){
                orderRes.setContractId(order.getContract().getId());
            }
            if (order.getProduct() != null){
                orderRes.setProductName(order.getProduct().getName());
            }
            if (order.getDealer() != null){
                orderRes.setDealerId(order.getDealer().getId());
            }
            if (order.getTotal() >= 0){
                orderRes.setTotalPrice(order.getTotal());
            }
            if (order.getStatus() != null){
                orderRes.setStatus(order.getStatus());
            } else {
                orderRes.setStatus("Processing");
            }
        }
        return orderRes;
    }
}
