package com.bethappy.demo.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="CHARACTERS")
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
  public Characters(){
    // you set the default value here as well...
    this.mining = 0;
  }

  public Characters(String name){
    this.name = name;
    this.mining = 0;
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
}
