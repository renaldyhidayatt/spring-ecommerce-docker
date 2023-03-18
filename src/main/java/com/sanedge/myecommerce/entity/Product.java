package com.sanedge.myecommerce.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String reference;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Float width;
    private Float height;
    private String image;
    private String thumbnail;

    private Float price;
    private Integer sales;
    private Integer stock;

    @Column(insertable = false, updatable = false)
    private Integer category_id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
    }

    private Product(
            String reference,
            String description,
            Float width,
            Float height,
            String image,
            String thumbnail,
            Float price,
            Integer sales,
            Integer stock,
            Integer category_id) {
        this.reference = reference;
        this.description = description;
        this.width = width;
        this.height = height;
        this.image = image;
        this.thumbnail = thumbnail;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        this.category_id = category_id;
    }

    public static Product of(
            String reference,
            String description,
            Float width,
            Float height,
            String image,
            String thumbnail,
            Float price,
            Integer sales,
            Integer stock,
            Integer category_id) {
        return new Product(
                reference,
                description,
                width,
                height,
                image,
                thumbnail,
                price,
                sales,
                stock,
                category_id);
    }
}
