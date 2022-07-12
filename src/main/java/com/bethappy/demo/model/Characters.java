package com.bethappy.demo.model;

import lombok.Data;
import javax.persistence.*;

import java.io.Serial;
import java.time.LocalDateTime;
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

  // Add user_id here?


  @OneToMany(mappedBy="characters")
  private Set<Inventory> inventoryList;

  public Characters(){
    // you set the default value here as well...
    this.mining = 0;
    this.smithing = 0;
  }

  public Characters(String name){
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

  public void setSmithing(Integer smithing){
    this.smithing = smithing;
  }
  public Integer getSmithing(){
    return smithing;
  }
}
