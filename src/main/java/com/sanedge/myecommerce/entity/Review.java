package com.sanedge.myecommerce.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

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
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant date;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private String status;
    private Integer rating;

    @Column(insertable = false, updatable = false)
    private Long customer_id;

    @Column(insertable = false, updatable = false)
    private Long product_id;

    @Column(insertable = false, updatable = false)
    private Long command_id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "command_id")
    private Command command;
}
