package com.bethappy.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="CHARACTERS")
public class Characters {
  //might want to add timestamp to mark character creation time
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Integer mining;
  

  @OneToOne
  @JsonBackReference
  @JoinColumn(name = "users_id")
  private User user;


  public Characters(){
    // you set the default value here as well...
    this.mining = Integer.valueOf(0);
  }

  public Characters(String name){
    this.name = name;
    this.mining = Integer.valueOf(0);
  }

  public void setName(String name){
    this.name = name;
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
