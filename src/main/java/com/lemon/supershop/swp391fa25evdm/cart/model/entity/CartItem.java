package com.lemon.supershop.swp391fa25evdm.cart.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemon.supershop.swp391fa25evdm.product.model.entity.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "cartitem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private int id;

    @ManyToOne
    @JoinColumn(name = "CartId")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    @JsonIgnore
    private Product product;

    @Column(name = "Quantity", nullable = false, columnDefinition = "INT")
    private int quantity;

    @Column(name = "Price", columnDefinition = "DECIMAL(18,2)")
    private double price;

    public CartItem() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
