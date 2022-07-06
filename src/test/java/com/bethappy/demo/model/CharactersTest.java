package com.bethappy.demo.model;

import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class CharactersTest {
  private Characters character = new Characters("TestCharacter");

  @Test
  public void characterValuesTest(){
    assertEquals("TestCharacter", character.getName());
    assertEquals(Integer.valueOf(0), character.getMining());
  }
}
