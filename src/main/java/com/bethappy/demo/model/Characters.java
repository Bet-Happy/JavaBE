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
//import org.apache.commons.validator.routines.EmailValidator;


import static java.lang.Boolean.TRUE;

//import java.math.BigInteger;

import lombok.Data;

@Data
@Entity
@Table(name="CHARACTERS")
public class Characters {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Integer mining;
  

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
    this.mining = 123456;
  }

  public Integer getMining(){
    return mining;
  }
}
