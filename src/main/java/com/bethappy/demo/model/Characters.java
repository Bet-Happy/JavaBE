package com.bethappy.demo.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.GenerationType;
import lombok.Data;

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
