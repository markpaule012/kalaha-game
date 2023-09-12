package com.bol.kalaha.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameConstantsTest {

    @Test
    public void testConstants() {
        assertEquals(13, GameConstants.INDEX_NORTH_PIT);
        assertEquals(6, GameConstants.INDEX_SOUTH_PIT);
        assertEquals(0, GameConstants.INDEX_SOUTH_PIT_START);
        assertEquals(7, GameConstants.INDEX_NORTH_PIT_START);
    }

}
