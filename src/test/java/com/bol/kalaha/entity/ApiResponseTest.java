package com.bol.kalaha.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ApiResponseTest {

    @Test
    public void testApiResponse_VALID() {
        Board board = new Board();
        ApiResponse apiResponse = ApiResponse.builder()
                .board(board.toMap())
                .gameId("TEST_GAMEID")
                .build();
        assertNull(apiResponse.getError());
        assertEquals("TEST_GAMEID", apiResponse.getGameId());
        assertNotNull(apiResponse.getBoard());
    }

    @Test
    public void testApiResponse_ERROR() {
        ApiResponse apiResponse = ApiResponse.builder()
                .error("TEST_ERROR").build();
        assertNull(apiResponse.getBoard());
    }

}
