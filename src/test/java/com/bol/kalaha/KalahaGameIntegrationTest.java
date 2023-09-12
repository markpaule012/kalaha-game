package com.bol.kalaha;

import com.bol.kalaha.entity.ApiResponse;
import com.bol.kalaha.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = KalahaGameApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KalahaGameIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetOrCreateGame() {
        String url = "http://localhost:" + this.port;
        URI uri = UriComponentsBuilder.fromHttpUrl(url).path("/api/get")
                .queryParam("gameID", "TEST").build().toUri();
        ApiResponse response = this.restTemplate
                .getForObject(uri, ApiResponse.class);

        assertTrue(response.getBoard().get("PIT_0").equals(6));
        assertTrue(response.getBoard().get("PIT_1").equals(6));
        assertTrue(response.getBoard().get("PIT_2").equals(6));
        assertTrue(response.getBoard().get("PIT_3").equals(6));
        assertTrue(response.getBoard().get("PIT_4").equals(6));
        assertTrue(response.getBoard().get("PIT_5").equals(6));
        assertTrue(response.getBoard().get("PIT_7").equals(6));
        assertTrue(response.getBoard().get("PIT_8").equals(6));
        assertTrue(response.getBoard().get("PIT_9").equals(6));
        assertTrue(response.getBoard().get("PIT_10").equals(6));
        assertTrue(response.getBoard().get("PIT_11").equals(6));
        assertTrue(response.getBoard().get("PIT_12").equals(6));

        // Big Pits are 0
        assertTrue(response.getBoard().get("PIT_6").equals(0));
        assertTrue(response.getBoard().get("PIT_13").equals(0));

        // Big Pits are 0
        assertTrue(response.getCurrentTurn().equals(Player.SOUTH.name()));
    }

    @Test
    public void testFullGame() {
        String url = "http://localhost:" + this.port;
        String gameId = "TESTFULL";

        // Create Game
        URI uri = UriComponentsBuilder.fromHttpUrl(url).path("/api/get")
                .queryParam("gameID", gameId).build().toUri();
        AtomicReference<ApiResponse> response = new AtomicReference<>(this.restTemplate
                .getForObject(uri, ApiResponse.class));

        List<Integer> moves = Arrays.asList(0, 1, 7, 4, 7, 0, 12, 2, 7, 1, 9, 4, 7, 1, 12, 11, 12, 10, 2, 11);
        moves.stream().forEach(e -> {
                    URI moveUri = UriComponentsBuilder.fromHttpUrl(url).path("/api/move")
                            .queryParam("gameID", gameId)
                            .queryParam("player", e < 6 ? "SOUTH" : "NORTH")
                            .queryParam("move", e).build().toUri();

                    response.set(this.restTemplate
                            .getForObject(moveUri, ApiResponse.class));
                    System.out.println(response.get().getBoard() != null ? response.get().getBoard().toString() : response.get().getError().toString());
                }
        );
        assertTrue(response.get().getError().contains("Game Finished, winner is SOUTH, Please refresh then start a new game with different Game ID"));
    }

}
