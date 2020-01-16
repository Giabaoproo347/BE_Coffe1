package com.codegym.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String purchaseDate;
    private String deliveryDate;
    private String description;

    @OneToMany(targetEntity = OrderDetail.class)
    private Set<OrderDetail> orderDetails;

    @ManyToOne
    private Payment payment;

    public Order() {
    }

    public Order(Long id, String purchaseDate, String description, String deliveryDate, Set<OrderDetail> orderDetails, Payment payment) {
        this.id  = id   ;
        this.description =description;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.orderDetails = orderDetails;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
