package com.codegym.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private long price;

   @OneToMany
    private Set<Product> products;

    public Promotion() {
    }

    public Promotion(Long id, String name, long price, Set<Product> products) {
        this.id = id    ;
        this.name = name;
        this.price = price;
        this.products = products;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
