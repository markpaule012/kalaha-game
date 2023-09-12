package com.bol.kalaha.exceptions;

/**
 * Custom un-checked exception for Game Application
 */
public class GameException extends RuntimeException {

    public GameException(String message){
        super(message);
    }

}
