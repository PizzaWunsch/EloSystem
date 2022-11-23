package dev.pizzawunsch.elosystem.api.events;

import dev.pizzawunsch.elosystem.utils.EloRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This class handles the elo rank event that will be executed whenever a player's elo rank got changed.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.11.2022
 */
@AllArgsConstructor
@Getter
public class EloRankChangeEvent extends Event {

    // instance variables.
    private Player player;
    private EloRank oldEloRank;
    private EloRank newEloRank;
    private ChangeType changeType;

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * This class handles the different types how the elo rank can be changed.
     *
     * @author Lucas | PizzaWunsch
     * @version 1.0
     * @since 21.11.2022
     */
    public enum ChangeType {
        UPRANK, DOWNRANK;
    }
}