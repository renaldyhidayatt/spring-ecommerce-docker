package com.sanedge.myecommerce.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    private String city;
    private String zipcode;
    private String address;

    private String avatar;
    private Instant birthday;
    private String stateAbbr;
    private Instant last_seen;
    private Instant first_seen;
    private Instant latest_purchase;

    private Float total_spent;
    private Integer nb_commands;
    private Boolean has_ordered;
    private Boolean has_newsletter;

    @ManyToMany
    @JoinTable(name = "customer_group", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "group_type_name"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "customer_id", "group_type_name" }))
    private List<Group> groups = new ArrayList<>();
}
