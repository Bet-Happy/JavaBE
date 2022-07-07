package com.bethappy.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="CHARACTERS")
@NoArgsConstructor
public class Characters {
  //might want to add timestamp to mark character creation time
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;
  private String name;
  @Column(columnDefinition = "Integer default 0")
  private Integer mining = 0;

  @OneToMany(mappedBy="characters")
  private List<Inventory> inventoryList;

  public Characters(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public Integer getMining(){
    return mining;
  }
}
