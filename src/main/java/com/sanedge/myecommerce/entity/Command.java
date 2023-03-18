package com.sanedge.myecommerce.entity;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant date;

    private String status;
    private String reference;
    private Float total_ex_taxes;
    private Float delivery_fees;
    private Float tax_rate;
    private Float taxes;
    private Float total;
    private Boolean returned;

    @Column(insertable = false, updatable = false)
    private Long customer_id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Basket.class, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("command")
    private List<Basket> basket = new ArrayList<>();
}
