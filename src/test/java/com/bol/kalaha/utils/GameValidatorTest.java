package com.bol.kalaha.utils;

import com.bol.kalaha.entity.Player;
import com.bol.kalaha.exceptions.GameException;
import com.bol.kalaha.game.GameInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameValidatorTest {

    @Test
    public void testCheckGamePlayerValidNoException() {
        GameInstance gameInstance = new GameInstance("TESTGAME");
        GameValidator.checkGamePlayer(gameInstance, Player.SOUTH);
    }

    @Test
    public void testCheckGamePlayerInvalidThrowsException() {
        GameInstance gameInstance = new GameInstance("TESTGAME");
        GameException thrown = Assertions.assertThrows(GameException.class, () -> GameValidator.checkGamePlayer(gameInstance, Player.NORTH));
        Assertions.assertTrue(thrown.getMessage().contains("Invalid move"));
    }

    @Test
    public void testCheckPitValid() {
        GameInstance gameInstance = new GameInstance("TESTGAME");
        GameValidator.checkPit(gameInstance, 0);
    }

    @Test
    public void testCheckPitsBigPitsThrowsException() {
        GameInstance gameInstance = new GameInstance("TESTGAME");
        GameException thrown = Assertions.assertThrows(GameException.class, () -> GameValidator.checkPit(gameInstance, GameConstants.INDEX_SOUTH_PIT));
        Assertions.assertTrue(thrown.getMessage().contains("Illegal move, left and right pits not allowed"));
    }

    @Test
    public void testCheckPitsOpposingPitsThrowsExceptions() {
        GameInstance gameInstance = new GameInstance("TESTGAME");
        GameException thrown = Assertions.assertThrows(GameException.class, () -> GameValidator.checkPit(gameInstance, 7));
        Assertions.assertTrue(thrown.getMessage().contains("Illegal move"));
    }

    @Test
    public void testCheckPitsZeroPits() {
        GameInstance gameInstance = new GameInstance("TESTGAME");
        gameInstance.getBoard().clearPit(0); // Set pit to zero
        GameException thrown = Assertions.assertThrows(GameException.class, () -> GameValidator.checkPit(gameInstance, 0));
        Assertions.assertTrue(thrown.getMessage().contains("Illegal move, pit has 0 stones"));
    }
}
