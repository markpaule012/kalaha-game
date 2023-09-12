package com.bol.kalaha.entity;

import com.bol.kalaha.utils.GameConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

    @Test
    public void testBoard_Creation() {
        Board board = new Board();
        assertNotNull(board);
    }

    @Test
    public void testBoardGet_PlayerPitShouldBe6Stones() {
        Board board = new Board();
        assertEquals(6, board.getPit(0));
        assertEquals(6, board.getPit(1));
        assertEquals(6, board.getPit(2));
        assertEquals(6, board.getPit(3));
        assertEquals(6, board.getPit(4));
        assertEquals(6, board.getPit(5));
        assertEquals(6, board.getPit(7));
        assertEquals(6, board.getPit(8));
        assertEquals(6, board.getPit(9));
        assertEquals(6, board.getPit(10));
        assertEquals(6, board.getPit(11));
        assertEquals(6, board.getPit(12));
    }

    @Test
    public void testBoardGet_BigPitShouldBe0Stones() {
        Board board = new Board();
        assertEquals(0, board.getPit(GameConstants.INDEX_NORTH_PIT));
        assertEquals(0, board.getPit(GameConstants.INDEX_SOUTH_PIT));
    }

    @Test
    public void testBoardIsEmpty_BigPitShouldBeEmpty() {
        Board board = new Board();
        assertTrue(board.isEmptyPit(GameConstants.INDEX_NORTH_PIT));
        assertTrue(board.isEmptyPit(GameConstants.INDEX_SOUTH_PIT));
    }

    @Test
    public void testBoardClearPit_PitShouldBeZero() {
        int index = 0;
        Board board = new Board();
        assertEquals(6, board.getPit(index));
        board.clearPit(index);
        assertEquals(0, board.getPit(index));
    }

    @Test
    public void testBoardAddStonesToPit() {
        int index = 0;
        Board board = new Board();
        assertEquals(6, board.getPit(index));
        board.addStonesToPit(0, 3);
        assertEquals(9, board.getPit(index));
    }

    @Test
    public void testToMap() {
        Board board = new Board();

        // Player Pits
        assertEquals(6, board.toMap().get("PIT_1"));
        assertEquals(6, board.toMap().get("PIT_2"));
        assertEquals(6, board.toMap().get("PIT_3"));
        assertEquals(6, board.toMap().get("PIT_4"));
        assertEquals(6, board.toMap().get("PIT_5"));
        assertEquals(6, board.toMap().get("PIT_7"));
        assertEquals(6, board.toMap().get("PIT_8"));
        assertEquals(6, board.toMap().get("PIT_9"));
        assertEquals(6, board.toMap().get("PIT_10"));
        assertEquals(6, board.toMap().get("PIT_11"));
        assertEquals(6, board.toMap().get("PIT_12"));

        // Big Pits
        assertEquals(0, board.toMap().get("PIT_6"));
        assertEquals(0, board.toMap().get("PIT_13"));
    }
}
