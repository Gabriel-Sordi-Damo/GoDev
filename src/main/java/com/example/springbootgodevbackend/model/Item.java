package com.example.springbootgodevbackend.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity()
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Double value;

    @Column(name = "type")
    private Character type;

    public Item(String description, Double value, Character type) {
        super();
        this.description = description;
        this.value = value;
        this.type = type;
    }

}
