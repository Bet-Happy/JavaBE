package com.bethappy.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import javax.persistence.*;

import java.io.Serial;
import java.util.Set;

@Data
@Entity
@Table(name="CHARACTERS")
public class Characters {
  //might want to add timestamp to mark character creation time
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Integer mining = 0;

  private Integer smithing = 0;

  //****one character can have many items(named as inventory)
  @OneToMany(mappedBy = "characters")
  //****this prevents all the nested display of Json body that includes Set<Inventory> displaying character which contains Set<Inventory>
  @JsonManagedReference("characters_inventories")
  private Set<Inventory> inventories;

  public Characters(){
    this.mining = 0;
    this.smithing = 0;
  }

  public Characters(String name){
    this.name = name;
    this.mining = 0;
    this.smithing = 0;
  }

  public Characters(Long id, String name){
    this.id = id;
    this.name = name;
    this.mining = 0;
    this.smithing = 0;
  }

  public String getName(){
    return name;
  }

  public void setMining(Integer mining){
    this.mining = mining;
  }

  public Integer getMining(){
    return mining;
  }

  public void setSmithing(Integer smithing) {
    this.smithing = smithing;
  }
  
  public Integer getSmithing(){
    return smithing;
  }
}
