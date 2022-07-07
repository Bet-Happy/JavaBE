package com.bethappy.demo.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
//import org.apache.commons.validator.routines.EmailValidator;


import static java.lang.Boolean.TRUE;

//import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="CHARACTERS")
@NoArgsConstructor
public class Characters {
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
