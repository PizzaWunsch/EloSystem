package dev.pizzawunsch.elosystem.utils;

import dev.pizzawunsch.elosystem.EloSystem;
import dev.pizzawunsch.elosystem.api.events.EloRankChangeEvent;
import dev.pizzawunsch.elosystem.api.events.PlayerEloUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This class handles the elo player object that will handle the elo amount of the player.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 23.11.2022
 */
@Getter
@Setter
@AllArgsConstructor
public class EloPlayer {

    // instance variables.
    private UUID uniqueId;
    private int elo;

    /**
     * This method allows you to add elo to the player.
     * @param eloToAdd the amount of elo that should add.
     */
    public void addElo(int eloToAdd) {
        // the bukkit player got by the unique id.
        Player player = Bukkit.getPlayer(uniqueId);
        // the old elo rank of the player.
        EloRank oldEloRank = EloSystem.getEloAPI().getEloRank(this.elo);
        this.elo += eloToAdd;
        // the new elo rank of the player.
        EloRank newEloRank = EloSystem.getEloAPI().getEloRank(this.elo);
        // Calling the elo update event.
        Bukkit.getPluginManager().callEvent(new PlayerEloUpdateEvent(player, this.elo, PlayerEloUpdateEvent.ChangeType.ADDED));
        // Calling the elo rank change event, if the elo rank has been changed.
        if(!oldEloRank.equals(newEloRank))
            Bukkit.getPluginManager().callEvent(new EloRankChangeEvent(player, oldEloRank, newEloRank, EloRankChangeEvent.ChangeType.UPRANK));
    }

    /**
     * This method allows you to remove elo from the player.
     * @param eloToRemove the amount of elo that should remove.
     */
    public void removeElo(int eloToRemove) {
        // the bukkit player got by the unique id.
        Player player = Bukkit.getPlayer(uniqueId);
        // the old elo rank of the player.
        EloRank oldEloRank = EloSystem.getEloAPI().getEloRank(this.elo);
        this.elo -= eloToRemove;
        // the new elo rank of the player.
        EloRank newEloRank = EloSystem.getEloAPI().getEloRank(this.elo);
        // Calling the elo update event.
        Bukkit.getPluginManager().callEvent(new PlayerEloUpdateEvent(player, this.elo, PlayerEloUpdateEvent.ChangeType.REMOVED));
        // Calling the elo rank change event, if the elo rank has been changed.
        if(!oldEloRank.equals(newEloRank))
            Bukkit.getPluginManager().callEvent(new EloRankChangeEvent(player, oldEloRank, newEloRank, EloRankChangeEvent.ChangeType.DOWNRANK));
    }
}