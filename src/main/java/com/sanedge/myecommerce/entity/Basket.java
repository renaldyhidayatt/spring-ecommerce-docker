package com.sanedge.myecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private Long product_id;

    @Column(updatable = false, insertable = false, nullable = false)
    private Long command_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "command_id")
    private Command command;

    public Basket() {
    }

    private Basket(Integer quantity, Long product_id, Command command) {
        this.command = command;
        this.quantity = quantity;
        this.product_id = product_id;
    }

    public static Basket of(Integer quantity, Long product_id, Command command) {
        return new Basket(quantity, product_id, command);
    }
}
