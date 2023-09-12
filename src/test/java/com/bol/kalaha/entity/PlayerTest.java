package com.bol.kalaha.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void should() {
        assertEquals("NORTH", Player.NORTH.name());
        assertEquals("SOUTH", Player.SOUTH.name());
    }

}
