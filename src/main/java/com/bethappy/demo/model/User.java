package com.bethappy.demo.model;


import java.util.Set;
import static java.lang.Boolean.TRUE;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name="USERS")

public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;  
  private String username;
  private String password;
  private Boolean enabled;
  
  @OneToOne
  @JoinColumn(name = "characters_id")
  private Characters character;

  public User() {
    this.enabled = TRUE;

  }
  
  public User(String username, String password){
    this.username = username;
    this.password = password;
    this.enabled = TRUE;
  }
  
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername(){
    return username;
  }

  public void setUsername(String username){
    this.username = username;
  }

}
