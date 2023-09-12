package com.bol.kalaha.utils;

import com.bol.kalaha.entity.Player;
import com.bol.kalaha.exceptions.GameException;
import com.bol.kalaha.game.GameInstance;

import static com.bol.kalaha.utils.GameConstants.INDEX_NORTH_PIT;
import static com.bol.kalaha.utils.GameConstants.INDEX_NORTH_PIT_START;
import static com.bol.kalaha.utils.GameConstants.INDEX_SOUTH_PIT;
import static com.bol.kalaha.utils.GameConstants.INDEX_SOUTH_PIT_START;

/**
 * Utility static validator class for all validation operations
 */
public class GameValidator {

    /**
     * Checks of the player is allowed to perform a move operation on the provided game instance
     *
     * @param gameInstance the game instance
     * @param player       the player performing move operation
     */
    public static void checkGamePlayer(GameInstance gameInstance, Player player) {
        if (gameInstance.getCurrentTurn() != player)
            throw new GameException(String.format("Invalid move, current turn is %s", gameInstance.getCurrentTurn()));
    }

    /**
     * Checks if the pit is eligible for a move operation
     *
     * @param gameInstance the game instance
     * @param index        the pit index
     */
    public static void checkPit(GameInstance gameInstance, Integer index) {
        if (index.equals(INDEX_NORTH_PIT) || index.equals(INDEX_SOUTH_PIT)) {
            throw new GameException("Illegal move, left and right pits not allowed");
        } else if (gameInstance.getCurrentTurn() == Player.SOUTH && !(index >= INDEX_SOUTH_PIT_START && index < INDEX_SOUTH_PIT)) {
            throw new GameException("Illegal move, South player allowed pits are 0-5");
        } else if (gameInstance.getCurrentTurn() == Player.NORTH && !(index >= INDEX_NORTH_PIT_START && index < INDEX_NORTH_PIT)) {
            throw new GameException("Illegal move, North player allowed pits are 7-12");
        } else if (gameInstance.getBoard().isEmptyPit(index)) {
            throw new GameException("Illegal move, pit has 0 stones");
        }
    }

}
