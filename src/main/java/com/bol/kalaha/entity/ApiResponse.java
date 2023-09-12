package com.bol.kalaha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * POJO containing the API Response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private Map<String, Integer> board;
    private String gameId;
    private String currentTurn;
    private String error;

}
