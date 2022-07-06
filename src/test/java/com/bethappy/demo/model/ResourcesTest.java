package com.bethappy.demo.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class ResourcesTest {
  private Resource copper = new Resource("Copper", 5, 3);
  
  @Test
  public void resourceHasValues(){
    assertThat(copper.getName()).isEqualTo("Copper");
    assertThat(copper.getXP()).isEqualTo(5);
    assertThat(copper.getTPA()).isEqualTo(3);
  }
} 