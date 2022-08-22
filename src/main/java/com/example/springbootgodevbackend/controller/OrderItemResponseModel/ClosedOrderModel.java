package com.example.springbootgodevbackend.controller.OrderItemResponseModel;

import com.example.springbootgodevbackend.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ClosedOrderModel {
    private long id;

    private Integer number;

    private java.util.Date date;

    private Double percentualDiscount = 0.0;

    private Double totalValue = 0.0;

    private ArrayList<ItemClosedOrderModel> items;

    public ClosedOrderModel(Order order, ArrayList<ItemClosedOrderModel> items) {
        super();
        this.id = order.getId();
        this.number = order.getNumber();
        this.percentualDiscount = order.getPercentualDiscount();
        this.totalValue = order.getTotalValue();
        this.items = items;
    }
}
