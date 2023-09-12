package com.bol.kalaha.game;

import com.bol.kalaha.entity.ApiResponse;
import com.bol.kalaha.entity.Player;
import com.bol.kalaha.exceptions.GameException;
import com.bol.kalaha.utils.GameConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameServiceImplTest {

    @Test
    public void testGetOrCreateGame() {
        GameServiceImpl gameService = new GameServiceImpl();
        ApiResponse response = gameService.getOrCreateGame("TESTGAME");

        assertNull(response.getError());
        assertEquals("TESTGAME", response.getGameId());
        assertEquals(GameConstants.STARTING_PLAYER.name(), response.getCurrentTurn());
        assertNotNull(response.getBoard());
    }

    @Test
    public void testMakeMove() {
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.getOrCreateGame("TESTGAME");

        Integer moveIndex = 0;
        Player player = Player.SOUTH;
        ApiResponse moveResponse = gameService.makeMove("TESTGAME", moveIndex, player);

        assertEquals(0, moveResponse.getBoard().get("PIT_0")); // 0 index should now be 0 from the board map
        assertEquals(1, moveResponse.getBoard().get("PIT_6")); // South Big Pit should now be 1 from the board map
        assertEquals(Player.SOUTH.name(), moveResponse.getCurrentTurn()); // Current turn is still SOUTH
    }

    @Test
    public void testMakeMoveInvalidPlayer() {
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.getOrCreateGame("TESTGAME");

        Integer moveIndex = 0;
        Player player = Player.NORTH;

        GameException thrown = Assertions.assertThrows(GameException.class, () -> gameService.makeMove("TESTGAME", moveIndex, player));
        Assertions.assertTrue(thrown.getMessage().contains("Invalid move"));
    }

    @Test
    public void testMakeMoveInvalidPlayerPit() {
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.getOrCreateGame("TESTGAME");

        Integer moveIndex = 7;
        Player player = Player.SOUTH;

        GameException thrown = Assertions.assertThrows(GameException.class, () -> gameService.makeMove("TESTGAME", moveIndex, player));
        Assertions.assertTrue(thrown.getMessage().contains("Illegal move"));
    }

    @Test
    public void testMakeMoveInvalidBigPit() {
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.getOrCreateGame("TESTGAME");

        Integer moveIndex = 6;
        Player player = Player.SOUTH;

        GameException thrown = Assertions.assertThrows(GameException.class, () -> gameService.makeMove("TESTGAME", moveIndex, player));
        Assertions.assertTrue(thrown.getMessage().contains("Illegal move, left and right pits not allowed"));
    }

    @Test
    public void testMakeMoveInvalidZeroPit() {
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.getOrCreateGame("TESTGAME");

        Player player = Player.SOUTH;
        gameService.makeMove("TESTGAME", 0, player);

        // Play the move again with now 0 stones
        GameException thrown = Assertions.assertThrows(GameException.class, () -> gameService.makeMove("TESTGAME", 0, player));
        Assertions.assertTrue(thrown.getMessage().contains("Illegal move, pit has 0 stones"));
    }

}
