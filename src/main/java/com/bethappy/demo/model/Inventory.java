package com.bethappy.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "characters_id",nullable = false)
    @JsonBackReference("characters_inventories")
    private Characters characters;

    @ManyToOne
    @JoinColumn(name = "resource_id",nullable = false)
    @JsonBackReference("resource_inventories")
    private Resource resource;

    @Column(name="amount")
    private Integer amount;

    public Inventory() {
    }

    public Inventory(Characters characters, Resource resource, Integer amount){
        this.characters= characters;
        this.resource = resource;
        this.amount= amount;
    }

}
