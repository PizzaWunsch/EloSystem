package dev.pizzawunsch.elosystem.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * This class handles an elo rank entry of the elo system.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 23.11.2022
 */
@AllArgsConstructor
@Getter
public class EloRank {

    // instance variables.
    private String key, name;
    private int requiredElo, maxElo;
}