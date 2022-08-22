package com.example.springbootgodevbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_info")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date date;

    @Column(name = "percentual_discount")
    private Double percentualDiscount = 0.0;

    @Column(name = "total_value")
    private Double totalValue = 0.0;

    public Order(Integer number, Double percentualDiscount, Double totalValue) {
        super();
        this.number = number;
        this.percentualDiscount = percentualDiscount;
        this.totalValue = totalValue;
    }
}