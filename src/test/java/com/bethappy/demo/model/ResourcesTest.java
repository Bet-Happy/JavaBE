package com.bethappy.demo.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class ResourcesTest {
  private Resource copper = new Resource("Copper", 5, 5);
  
  @Test
  public void resourceHasName(){
    assertThat(copper.getName()).isEqualTo("Copper");
  }
} 