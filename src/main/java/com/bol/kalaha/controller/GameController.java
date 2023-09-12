package com.bol.kalaha.controller;

import com.bol.kalaha.entity.ApiResponse;
import com.bol.kalaha.entity.Player;
import com.bol.kalaha.game.GameService;
import com.bol.kalaha.utils.GameConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller that will be called from UI or via GET request
 */
@RestController
@Validated
@RequestMapping(path = "/api")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Creates or retrieve an existing kalaha game. Application may store multiple sessions with unique game ID
     *
     * @param gameID a String value for game key
     * @return an ApiResponse object which contains all game information
     */
    @GetMapping("/get")
    public @ResponseBody ApiResponse get(@NotNull(message = GameConstants.ERROR_EMPTY_GAMEID)
                                         @Pattern(regexp = "^[a-zA-Z0-9]*$", message = GameConstants.ERROR_INVALID_GAMEID)
                                         String gameID) {
        return gameService.getOrCreateGame(gameID);
    }

    /**
     * Perform a move operation on existing kalaha game.
     *
     * @param gameID the game key
     * @param move   the pit number location
     * @param player the player performing the move
     * @return an ApiResponse object which contains all game information
     */
    @GetMapping("/move")
    public @ResponseBody ApiResponse move(@NotNull(message = GameConstants.ERROR_EMPTY_GAMEID)
                                          @Pattern(regexp = "^[a-zA-Z0-9]*$", message = GameConstants.ERROR_INVALID_GAMEID)
                                          String gameID,
                                          @NotNull(message = "Invalid Move: Move is empty")
                                          Integer move,
                                          @NotNull(message = "Invalid Player: Player is empty")
                                          Player player) {
        return gameService.makeMove(gameID, move, player);
    }

}
