package com.bol.kalaha.game;

import com.bol.kalaha.entity.Board;
import com.bol.kalaha.entity.Player;
import com.bol.kalaha.exceptions.GameException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

import static com.bol.kalaha.utils.GameConstants.INDEX_NORTH_PIT;
import static com.bol.kalaha.utils.GameConstants.INDEX_NORTH_PIT_START;
import static com.bol.kalaha.utils.GameConstants.INDEX_SOUTH_PIT;
import static com.bol.kalaha.utils.GameConstants.INDEX_SOUTH_PIT_START;
import static com.bol.kalaha.utils.GameConstants.STARTING_PLAYER;

/**
 * This class will hold the stateful value of a single Kalaha game.
 */
@Slf4j
@Getter
public class GameInstance {

    private Player currentTurn;
    private final Board board;
    private final String gameId;

    /**
     * Constructor for a new Kalaha Game
     * @param gameId the game key
     */
    public GameInstance(String gameId) {
        this.gameId = gameId;
        this.board = new Board();
        this.currentTurn = STARTING_PLAYER;
    }

    /**
     * Perform a move operation for this game instance
     * @param index the pit index
     */
    public void move(Integer index) {
        // Perform Action
        Integer lastIndex = distributeStones(index);

        // Check if player can capture
        checkCaptureStoneAction(INDEX_SOUTH_PIT_START, INDEX_SOUTH_PIT, lastIndex, Player.SOUTH);
        checkCaptureStoneAction(INDEX_NORTH_PIT_START, INDEX_NORTH_PIT, lastIndex, Player.NORTH);

        // Check if game is finished
        checkIfGameFinished();

        //Set Next Turn
        currentTurn = getNextTurn(lastIndex);
    }

    /**
     * (Private) Checks if player is finished with the provided index
     */
    private boolean isPlayerFinished(Integer startIndex, Integer endIndex) {
        return IntStream.range(startIndex, endIndex).allMatch(board::isEmptyPit);
    }

    /**
     * (Private) Checks if Game is finished. If yes, move the remaining stones to its corresponding players pit
     */
    private void checkIfGameFinished() {
        if (isPlayerFinished(INDEX_SOUTH_PIT_START, INDEX_SOUTH_PIT) || isPlayerFinished(INDEX_NORTH_PIT_START, INDEX_NORTH_PIT)) {
            moveRemainingStones(INDEX_SOUTH_PIT_START, INDEX_SOUTH_PIT);
            moveRemainingStones(INDEX_NORTH_PIT_START, INDEX_NORTH_PIT);
            String winner = board.getPit(INDEX_SOUTH_PIT) > board.getPit(INDEX_NORTH_PIT) ? Player.SOUTH.name() : Player.NORTH.name();
            throw new GameException(String.format("Game Finished, winner is %s, Please refresh then start a new game with different Game ID", winner));
        }
    }

    private void moveRemainingStones(Integer startIndex, Integer endIndex) {
        int totalStones = IntStream.range(startIndex, endIndex).boxed().mapToInt(e -> {
            int x = board.getPit(e);
            board.clearPit(e);
            return x;
        }).sum();
        board.addStonesToPit(endIndex, totalStones);
    }

    /**
     * (Private) Evaluates the next turn based on the last index of the move operation
     */
    private Player getNextTurn(Integer lastIndex) {
        if ((lastIndex.equals(INDEX_NORTH_PIT) || lastIndex.equals(INDEX_SOUTH_PIT)) &&
                (currentTurn == Player.SOUTH && lastIndex.equals(INDEX_SOUTH_PIT)) ||
                (currentTurn == Player.NORTH && lastIndex.equals(INDEX_NORTH_PIT))) {
            log.info("[Game ID: {}] Last Index is players pit, player can pick a pit again!", gameId);
            return currentTurn;
        }
        return currentTurn == Player.SOUTH ? Player.NORTH : Player.SOUTH;
    }

    /**
     * (Private) Checks if move operation is eligible for capture action
     */
    private void checkCaptureStoneAction(Integer startIndex, Integer endIndex, Integer lastIndex, Player player) {
        // If pit is exactly 1, check if player can capture opposing side
        if (board.getPit(lastIndex) == 1 && currentTurn == player && (lastIndex >= startIndex && lastIndex < endIndex)) {
            int oppositeIndex = 12 - lastIndex;
            if (board.getPit(oppositeIndex) == 0) // Do not capture if opposite is 0
                return;
            int totalStones = board.getPit(lastIndex);
            totalStones = totalStones + board.getPit(oppositeIndex);

            // Clear both pits
            board.clearPit(lastIndex);
            board.clearPit(oppositeIndex);

            // Add to player(endIndex) pit
            board.addStonesToPit(endIndex, totalStones);
        }
    }

    /**
     * (Private) Distributes all the stones from the provided index
     */
    private Integer distributeStones(Integer index) {
        // Get Stones
        int currentStones = board.getPit(index);

        // Clear Pit
        board.clearPit(index);

        // Distribute Stones
        int currentIndex = index;
        while (currentStones > 0) {
            currentIndex = getNextIndex(currentIndex);
            if (isAllowedStonePit(currentIndex)) {
                board.addStonesToPit(currentIndex, 1);
                currentStones--;
            }
        }
        return currentIndex;
    }

    /**
     * (Private) Returns an integer value of the next index provided the current index.
     * Resets to 0 of current index is 13
     */
    private int getNextIndex(Integer currentIndex) {
        return currentIndex.equals(INDEX_NORTH_PIT) ? INDEX_SOUTH_PIT_START : currentIndex + 1;
    }

    /**
     * (Private) Checks if stone can be distributed on the current index.
     * Note: Do not distribute stones to the opponents big pit
     */
    private boolean isAllowedStonePit(Integer index) {
        return (!index.equals(INDEX_NORTH_PIT) && !index.equals(INDEX_SOUTH_PIT)) ||
                ((currentTurn == Player.NORTH || !index.equals(INDEX_NORTH_PIT)) &&
                        (currentTurn == Player.SOUTH || !index.equals(INDEX_SOUTH_PIT)));
    }
}
