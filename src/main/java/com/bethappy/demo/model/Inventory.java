package com.bethappy.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Inventory")
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "characters_id",nullable = false)
    private Characters characters;

    @Column(name="slot_number")
    private Integer slot_number;

    @Column(name="amount")
    private Integer amount;

    public Inventory(Characters characters,Integer slot_number, Integer amount){
        this.characters = characters;
        this.slot_number= slot_number;
        this.amount= amount;
    }
}
