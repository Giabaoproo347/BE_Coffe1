package com.codegym.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String date;

    @OneToMany(targetEntity = Order.class)
    private Set<Order> orders;


    public Payment() {
    }

    public Payment(Long id, String name, String date, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
