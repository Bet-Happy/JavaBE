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

import java.math.BigInteger;

import lombok.Data;

@Data
@Entity
@Table(name="CHARACTER")
public class Character {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private BigInteger mining;
  

  public Character(String name){
    this.name = name;
    this.mining = BigInteger.valueOf(0);
  }
}
