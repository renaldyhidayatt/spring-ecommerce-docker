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
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant date;

    private Float total_ex_taxes;
    private Float delivery_fees;
    private Float tax_rate;
    private Float taxes;
    private Float total;

    @Column(insertable = false, updatable = false)
    private Long command_id;

    @Column(insertable = false, updatable = false)
    private Long customer_id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "command_id")
    private Command command;
}
