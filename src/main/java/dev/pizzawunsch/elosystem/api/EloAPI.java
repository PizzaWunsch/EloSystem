package dev.pizzawunsch.elosystem.api;

import dev.pizzawunsch.elosystem.utils.EloPlayer;
import dev.pizzawunsch.elosystem.utils.EloRank;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * This class handles every method that is available in the elosystem api.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 23.11.2022
 */
public interface EloAPI {

    /**
     * Returns a collection with all elo players who are currently online.
     * @return a collection with all elo players who are currently online.
     */
    List<EloPlayer> getEloPlayers();

    /**
     * Returns a list with all available elo ranks.
     * @return a list with all available elo ranks.
     */
    List<EloRank> getEloRanks();

    /**
     * Allows you to get an elo player by the player's unique id.
     * @param uuid the player's unique id.
     * @return the elo player object by the player's unique id.
     */
    EloPlayer getEloPlayer(UUID uuid);

    /**
     * Allows you to get an elo player by the player's spigot object.
     * @param player the player's spigot object.
     * @return the elo player object by the player's spigot object.
     */
    default EloPlayer getEloPlayer(Player player) {
        return getEloPlayer(player.getUniqueId());
    }

    /**
     * Allows you to get an elo rank by the given elo amount.
     * @param elo the elo amount.
     * @return the elo rank object by the given elo amount.
     */
    EloRank getEloRank(int elo);
}