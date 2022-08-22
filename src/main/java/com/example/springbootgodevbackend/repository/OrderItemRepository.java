package com.example.springbootgodevbackend.repository;

import com.example.springbootgodevbackend.model.Item;
import com.example.springbootgodevbackend.model.Order;
import com.example.springbootgodevbackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrder(Order order);

    OrderItem findByOrderAndItem(Order order, Item item);
}
