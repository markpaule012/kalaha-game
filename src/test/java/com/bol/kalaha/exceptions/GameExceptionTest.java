package com.bol.kalaha.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameExceptionTest {

    @Test
    public void testConstruction() {
        GameException exception = new GameException("TEST_MESSAGE");
        assertEquals("TEST_MESSAGE", exception.getMessage());
    }
}
