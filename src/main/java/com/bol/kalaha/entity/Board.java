package com.bol.kalaha.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class contains all kalaha board related information. This uses list as underlying tool to hold all pit stones data.
 * Indexes:
 * South Pits Side - Left to Right (0 - 5)
 * South Pit - Right (6)
 * North Pits Side - Left to Right (12 - 7)
 * North Pit - Left (13)
 *
 */
public class Board {

    private final List<Integer> pits;

    /**
     * Creates a new Board Instance
     */
    public Board() {
        pits = Arrays.asList(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0);
    }

    /**
     * Retrieves the stones on given pit index
     * @param index the pit index
     * @return an Integer value of stones count
     */
    public Integer getPit(Integer index) {
        return pits.get(index);
    }

    /**
     * Checks if the pit has 0 stones
     * @param index the pit index
     * @return true if pit is empty
     */
    public Boolean isEmptyPit(Integer index) {
        return pits.get(index) == 0;
    }

    /**
     * Clears the provided pit index by setting the value to 0
     * @param index the pit index
     */
    public void clearPit(Integer index) {
        pits.set(index, 0);
    }

    /**
     * Adds a specific number of stones to the provided pit index
     * @param index the pit index
     * @param stones stones to be added
     */
    public void addStonesToPit(Integer index, Integer stones) {
        int p = getPit(index);
        setPit(index, p + stones);
    }

    /**
     * Prints the board to a map with key prefixed with "PIT_" and value with stone counts
     * @return the converted Map object
     */
    public Map<String, Integer> toMap() {
        return IntStream.range(0, pits.size()).boxed()
                .collect(Collectors.toMap(
                        value -> "PIT_" + value,
                        pits::get));
    }

    private void setPit(Integer index, Integer stone) {
        pits.set(index, stone);
    }
}
