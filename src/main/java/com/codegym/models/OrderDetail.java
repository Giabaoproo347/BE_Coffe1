package com.codegym.models;

import javax.persistence.*;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private long salePrice;
    private long quantity;
    private String description;

    @ManyToOne
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(Long id, String name, long salePrice, long quantity, String description, Order order) {
        this.id = id;
        this.name = name;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.description = description;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

