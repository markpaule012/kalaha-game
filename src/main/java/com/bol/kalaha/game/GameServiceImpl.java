package com.bol.kalaha.game;

import com.bol.kalaha.entity.ApiResponse;
import com.bol.kalaha.entity.Player;
import com.bol.kalaha.exceptions.GameException;
import com.bol.kalaha.utils.GameValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class to control all Game operations
 */
@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final Map<String, GameInstance> gameMap = new HashMap<>();

    /**
     * Creates or retrieves an existing Game instance
     *
     * @param gameID the game ID
     * @return an ApiResponse object containing the game details
     */
    public ApiResponse getOrCreateGame(String gameID) {
        GameInstance g = gameMap.get(gameID);
        if (g == null) {
            log.info("[Game ID: {}] Game not found, creating a new game", gameID);
            gameMap.put(gameID, new GameInstance(gameID));
        }
        return printGame(gameMap.get(gameID));
    }

    /**
     * Perform a move operation on the provided game ID
     *
     * @param gameID the game ID
     * @param index  the moving pit index
     * @param player the player performing move
     * @return an ApiResponse object containing the game details
     */
    public ApiResponse makeMove(String gameID, Integer index, Player player) {
        GameInstance g = getGame(gameID);

        // Validate Move
        GameValidator.checkGamePlayer(g, player);
        GameValidator.checkPit(g, index);

        // Perform Move
        log.info("[Game ID: {}] Move Index: {}", gameID, index);
        g.move(index);
        return printGame(g);
    }

    /**
     * (Private) Retrieves an existing game. Throws an exception if game does not exists.
     * @param gameID the Game ID
     * @return the game instance
     */
    private GameInstance getGame(String gameID) {
        GameInstance g = gameMap.get(gameID);
        if (g == null)
            throw new GameException("Invalid Game ID");
        return g;
    }

    /**
     * (Private) Prints the game instance to API readable response
     * @param gameInstance the Game
     * @return an ApiResponse object containing the game details
     */
    private ApiResponse printGame(GameInstance gameInstance) {
        return ApiResponse.builder()
                .board(gameInstance.getBoard().toMap())
                .currentTurn(gameInstance.getCurrentTurn().name())
                .gameId(gameInstance.getGameId()).build();
    }

}
