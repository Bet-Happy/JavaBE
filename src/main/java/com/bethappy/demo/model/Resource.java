package com.bethappy.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@Table(name="RESOURCE")
public class Resource {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  //****one resource type can belong to many inventories(named as inventory)
  @OneToMany(mappedBy = "resource")
  @JsonManagedReference("resource_inventories")
  private Set<Inventory> inventories;
  private String name;
  private Integer xp;
  private Integer tpa;

  public Resource() {

  }

  public Resource(String name, Integer xp, Integer tpa) {
    this.name = name;
    this.xp = xp;
    this.tpa = tpa;
  }
  public Resource(Long id, String name, Integer xp, Integer tpa) {
    this.id = id;
    this.name = name;
    this.xp = xp;
    this.tpa = tpa;
  }

  public Resource(Long id, String name, Integer xp, Integer tpa, Set<Inventory> inventories) {
    this.id = id;
    this.inventories = inventories;
    this.name = name;
    this.xp = xp;
    this.tpa = tpa;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setXP(Integer xp) {
    this.xp = xp;
  }

  public Integer getXP() {
    return xp;
  }

  public void setTPA(Integer tpa) {
    this.tpa = tpa;
  }

  public Integer getTPA() {
    return tpa;
  }

  public Set<Inventory> getInventories() {
    return inventories;
  }

  public void setInventories(Set<Inventory> inventories) {
    this.inventories = inventories;
  }
}
