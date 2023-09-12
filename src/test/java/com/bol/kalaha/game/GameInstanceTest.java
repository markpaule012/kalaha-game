package com.bol.kalaha.game;

import com.bol.kalaha.entity.Player;
import com.bol.kalaha.exceptions.GameException;
import com.bol.kalaha.utils.GameConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameInstanceTest {

    private static final List<Integer> MOVES_FULL_GAME = Arrays.asList(0, 1, 7, 4, 7, 0, 12, 2, 7, 1, 9, 4, 7, 1, 12, 11, 12, 10, 2, 11);

    @Test
    public void testConstruction() {
        GameInstance gameInstance = new GameInstance("TEST_ID");
        assertEquals("TEST_ID", gameInstance.getGameId());
        assertEquals(GameConstants.STARTING_PLAYER, gameInstance.getCurrentTurn());
        assertNotNull(gameInstance.getBoard());
    }

    @Test
    public void testMoveMoveLastIndexIsPlayerPit() {
        GameInstance gameInstance = new GameInstance("TEST_ID");
        assertEquals(6, gameInstance.getBoard().getPit(0)); // 0 index should be 6 initially

        // Perform Move
        gameInstance.move(0);
        assertEquals(0, gameInstance.getBoard().getPit(0)); // 0 index should now be 0
        assertEquals(1, gameInstance.getBoard().getPit(GameConstants.INDEX_SOUTH_PIT)); // South Big Pit should now be 1
        assertEquals(Player.SOUTH, gameInstance.getCurrentTurn()); // Current turn is still SOUTH
    }

    @Test
    public void testMove() {
        GameInstance gameInstance = new GameInstance("TEST_ID");
        assertEquals(6, gameInstance.getBoard().getPit(1)); // 1 index should be 6 initially

        // Perform Move
        gameInstance.move(1);
        assertEquals(0, gameInstance.getBoard().getPit(1)); // 1 index should now be 0
        assertEquals(7, gameInstance.getBoard().getPit(7)); // North 7 index pit should now be 7
        assertEquals(Player.NORTH, gameInstance.getCurrentTurn()); // Current turn is now NORTH
    }

    @Test
    public void testFullGame() {
        GameInstance gameInstance = new GameInstance("TEST_ID");
        assertEquals(6, gameInstance.getBoard().getPit(0)); // 0 index should be 6 initially

        // Perform Move
        GameException thrown = Assertions.assertThrows(GameException.class, () -> {
            MOVES_FULL_GAME.forEach(gameInstance::move);
        });
        Assertions.assertTrue(thrown.getMessage().contains("Game Finished"));

        // Assert Pits
        assertEquals(20, gameInstance.getBoard().getPit(GameConstants.INDEX_NORTH_PIT));
        assertEquals(52, gameInstance.getBoard().getPit(GameConstants.INDEX_SOUTH_PIT));
    }

}