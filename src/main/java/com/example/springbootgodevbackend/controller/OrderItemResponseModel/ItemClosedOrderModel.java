package com.example.springbootgodevbackend.controller.OrderItemResponseModel;

import com.example.springbootgodevbackend.model.Item;
import com.example.springbootgodevbackend.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemClosedOrderModel {

    private long id;

    private long itemId;

    private String description;

    private Double value;

    private Double quantity;

    public ItemClosedOrderModel(Item item, OrderItem orderItem) {
        this.description = item.getDescription();
        this.value = item.getValue();
        this.itemId = item.getId();
        this.quantity = orderItem.getQuantity();
        this.id = orderItem.getId();
    }
}
