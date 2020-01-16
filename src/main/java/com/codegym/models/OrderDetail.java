package com.codegym.models;

import javax.persistence.*;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long salePrice;
    private long quantity;

    @ManyToOne
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(Long id, long salePrice, long quantity, Order order) {
        this.id  = id   ;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.order = order;
    }

    public Long getId() {
        return id;
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

