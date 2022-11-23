package dev.pizzawunsch.elosystem.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This class handles the elo update event that will be executed whenever a player's elo got updated.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.11.2022
 */
@AllArgsConstructor
@Getter
public class PlayerEloUpdateEvent extends Event {

    // instance variables.
    private Player player;
    private int elo;
    private ChangeType changeType;

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * This class handles the different types if the elo got added or removed.
     *
     * @author Lucas | PizzaWunsch
     * @version 1.0
     * @since 21.11.2022
     */
    public enum ChangeType {
        ADDED, REMOVED;
    }
}