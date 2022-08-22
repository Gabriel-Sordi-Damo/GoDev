package com.example.springbootgodevbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(targetEntity = Item.class)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "total_value")
    private Double totalValue;


    public OrderItem(Double quantity, Double totalValue) {
        super();
        this.quantity = quantity;
        this.totalValue = totalValue;
    }

    public OrderItem(Double quantity, Item item) {
        super();
        this.quantity = quantity;
        this.item = item;
    }
}