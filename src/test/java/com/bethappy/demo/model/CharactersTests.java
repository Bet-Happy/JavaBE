package com.bethappy.demo.model;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CharactersTests {
    @Test
    public void createCharacterShouldReturnCharacterInfo(){
        Characters newChar = new Characters("Bobby");
        assertThat(newChar.getName()).isEqualTo("Bobby");
        assertThat(newChar.getMining()).isEqualTo(Integer.valueOf(0));
        assertThat(newChar.getId()).isNull();
    }

}
