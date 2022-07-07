package com.bethappy.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="RESOURCES")
public class Resource {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Integer xp;
  private Integer tpa;
  
  public Resource(){
    
  }

  public Resource(String name, Integer xp, Integer tpa){
    this.name = name;
    this.xp = xp;
    this.tpa = tpa;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public void setXP(Integer xp){
    this.xp = xp;
  }

  public Integer getXP(){
    return xp;
  }

  public void setTPA(Integer tpa){
    this.tpa = tpa;
  }
  
  public Integer getTPA(){
    return tpa;
  }
}
