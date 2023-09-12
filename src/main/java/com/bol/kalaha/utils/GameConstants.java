package com.bol.kalaha.utils;

import com.bol.kalaha.entity.Player;

/**
 * Class containing all constants for the Kalaha game
 */
public class GameConstants {

    public static final Integer INDEX_NORTH_PIT = 13;
    public static final Integer INDEX_SOUTH_PIT = 6;
    public static final Integer INDEX_SOUTH_PIT_START = 0;
    public static final Integer INDEX_NORTH_PIT_START = 7;
    public static final Player STARTING_PLAYER = Player.SOUTH;

    public static final String ERROR_EMPTY_GAMEID = "Invalid Game ID: Game ID is empty";
    public static final String ERROR_INVALID_GAMEID = "Invalid Game ID: Must only contains alphanumeric characters";

}
