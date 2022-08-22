package com.example.springbootgodevbackend.controller;

import com.example.springbootgodevbackend.controller.OrderItemEndPointModel.PostModel;
import com.example.springbootgodevbackend.controller.OrderItemEndPointModel.PutCloseModel;
import com.example.springbootgodevbackend.controller.OrderItemEndPointModel.PutModel;
import com.example.springbootgodevbackend.controller.OrderItemResponseModel.ClosedOrderModel;
import com.example.springbootgodevbackend.controller.OrderItemResponseModel.ItemClosedOrderModel;
import com.example.springbootgodevbackend.exception.ResourceNotFoundException;
import com.example.springbootgodevbackend.model.Item;
import com.example.springbootgodevbackend.model.Order;
import com.example.springbootgodevbackend.model.OrderItem;
import com.example.springbootgodevbackend.repository.ItemRepository;
import com.example.springbootgodevbackend.repository.OrderItemRepository;
import com.example.springbootgodevbackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;


    @GetMapping("/orders/{idOrder}/items")
    public List<OrderItem> getAllOrdersItems(@PathVariable Long idOrder) {
        Order order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not exist with id :" + idOrder));
        return orderItemRepository.findAllByOrder(order);
    }

    @GetMapping("/orders/{idOrder}/items/{idItem}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long idOrder, @PathVariable Long idItem) {
        Order order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not exist with id :" + idOrder));
        Item item = itemRepository.findById(idItem)
                .orElseThrow(() -> new ResourceNotFoundException(" Item not exist with id :" + idItem));

        OrderItem orderItem = orderItemRepository.findByOrderAndItem(order, item);

        if (orderItem == null)
            throw new ResourceNotFoundException("Order Item not exists with order id : " + idOrder
                    + "and" + " with item id : " + idItem);

        return ResponseEntity.ok(orderItem);
    }

    @PostMapping("/orders/{idOrder}/items")
    public OrderItem createOrderItem(@RequestBody PostModel orderItemDetails,
                                     @PathVariable Long idOrder) {
        Order order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not exist with id :" + idOrder));
        Item item = itemRepository.findById(orderItemDetails.itemId)
                .orElseThrow(() -> new ResourceNotFoundException(" Item not exist with id :" + orderItemDetails.itemId));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setQuantity(orderItemDetails.quantity);
        orderItem.setTotalValue(orderItemDetails.quantity * item.getValue());

        order.setTotalValue(order.getTotalValue() + orderItem.getTotalValue());

        orderRepository.save(order);

        return orderItemRepository.save(orderItem);
    }

    @PutMapping("/orders/{idOrder}/items/{idItem}")
    public ResponseEntity<OrderItem> updateOrderItem(@RequestBody PutModel orderItemDetails, @PathVariable Long idOrder, @PathVariable Long idItem) {

        Order oldOrder = orderRepository.findById(idOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not exist with id :" + idOrder));

        Item oldItem = itemRepository.findById(idItem)
                .orElseThrow(() -> new ResourceNotFoundException(" Item not exist with id :" + idItem));

        OrderItem oldOrderItem = orderItemRepository.findByOrderAndItem(oldOrder, oldItem);

        if (oldOrderItem == null)
            throw new ResourceNotFoundException("Order Item not exists with order id : " + idOrder
                    + "and" + " with item id : " + idItem);

        oldOrder.setTotalValue(oldOrder.getTotalValue() - oldOrderItem.getTotalValue());

        Item newItem = itemRepository.findById(orderItemDetails.itemId)
                .orElseThrow(() -> new ResourceNotFoundException(" Item not exist with id :" + orderItemDetails.itemId));

        oldOrderItem.setItem(newItem);
        oldOrderItem.setTotalValue(orderItemDetails.quantity * newItem.getValue());
        oldOrderItem.setQuantity(orderItemDetails.quantity);
        oldOrder.setTotalValue(oldOrder.getTotalValue() + oldOrderItem.getTotalValue());

        orderRepository.save(oldOrder);
        OrderItem updatedOrderItem = orderItemRepository.save(oldOrderItem);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/orders/{idOrder}/items/{idItem}")
    public ResponseEntity<Map<String, Boolean>> deleteOrderItem(@PathVariable Long idOrder, @PathVariable Long idItem) {
        Order oldOrder = orderRepository.findById(idOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not exist with id :" + idOrder));
        Item oldItem = itemRepository.findById(idItem)
                .orElseThrow(() -> new ResourceNotFoundException(" Item not exist with id :" + idItem));

        OrderItem oldOrderItem = orderItemRepository.findByOrderAndItem(oldOrder, oldItem);

        if (oldOrderItem == null)
            throw new ResourceNotFoundException("Order Item not exists with order id : " + idOrder
                    + "and" + " with item id : " + idItem);

        oldOrder.setTotalValue(oldOrder.getTotalValue() - oldOrderItem.getTotalValue());

        orderRepository.save(oldOrder);

        orderItemRepository.delete(oldOrderItem);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/orders/{idOrder}/close")
    public ResponseEntity<ClosedOrderModel> closeOrderItem(@RequestBody PutCloseModel orderItemDetails, @PathVariable Long idOrder) {

        Order oldOrder = orderRepository.findById(idOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not exist with id :" + idOrder));

        List<OrderItem> orderItems = orderItemRepository.findAllByOrder(oldOrder);

        if (orderItems == null || orderItems.isEmpty())
            throw new ResourceNotFoundException("Order Item not exists with order id : " + idOrder);

        Double totalDiscount = 0.0;
        ArrayList<ItemClosedOrderModel> items = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getItem().getType().equals('P'))
                totalDiscount += orderItem.getTotalValue() - ((orderItem.getTotalValue() * orderItemDetails.percentualDiscount * 100) % 101);
            ItemClosedOrderModel item = new ItemClosedOrderModel(orderItem.getItem(), orderItem);
            items.add(item);
        }

        oldOrder.setPercentualDiscount(orderItemDetails.percentualDiscount);
        oldOrder.setTotalValue(oldOrder.getTotalValue() - totalDiscount);

        Order updatedOrder = orderRepository.save(oldOrder);

        ClosedOrderModel closedOrder = new ClosedOrderModel(updatedOrder, items);

        return ResponseEntity.ok(closedOrder);
    }

}
