package com.example.springbootgodevbackend.controller;

import com.example.springbootgodevbackend.exception.InvalidValueException;
import com.example.springbootgodevbackend.exception.ResourceNotFoundException;
import com.example.springbootgodevbackend.model.Item;
import com.example.springbootgodevbackend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id :" + id));
        return ResponseEntity.ok(item);
    }

    @PostMapping("/items")
    public Item createItem(@RequestBody Item item) {
        if (item.getType() != 'S' && item.getType() != 'P')
            throw new InvalidValueException("Item type can be S or P");
        return itemRepository.save(item);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id :" + id));
        if (item.getType() != 'S' && item.getType() != 'P')
            throw new InvalidValueException("Item type can be S or P");
        item.setDescription(itemDetails.getDescription());
        item.setType(itemDetails.getType());
        item.setValue(itemDetails.getValue());

        Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteItem(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id :" + id));

        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
