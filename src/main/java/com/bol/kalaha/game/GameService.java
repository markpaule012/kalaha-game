package com.bol.kalaha.game;

import com.bol.kalaha.entity.ApiResponse;
import com.bol.kalaha.entity.Player;

/**
 * Inteface of GameService to control all Game operations
 */
public interface GameService {

    /**
     * Creates or retrieves an existing Game instance
     *
     * @param gameID the game ID
     * @return an ApiResponse object containing the game details
     */
    ApiResponse getOrCreateGame(String gameID);

    /**
     * Perform a move operation on the provided game ID
     *
     * @param gameID the game ID
     * @param index  the moving pit index
     * @param player the player performing move
     * @return an ApiResponse object containing the game details
     */
    ApiResponse makeMove(String gameID, Integer index, Player player);

}
