package com.codegym.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private long price;
    private String description;
    private String image;
    private String image2;
    private String image3;


    @ManyToOne
    private Promotion promotion;

    @ManyToOne
    private Category category;

    @OneToMany(targetEntity = Commenter.class)
    private List<Commenter> commenters;

    public Product() {
    }
    public Product( String name, long price, String description, String image,String image2, String image3 , Promotion promotion, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.promotion = promotion;
        this.category = category;
        this.image2 = image2;
        this.image3 = image3;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public List<Commenter> getCommenters() {
        return commenters;
    }

    public void setCommenters(List<Commenter> commenters) {
        this.commenters = commenters;
    }
}
