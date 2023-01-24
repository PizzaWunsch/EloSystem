package dev.pizzawunsch.elosystem.listeners;

import dev.pizzawunsch.elosystem.EloSystem;
import dev.pizzawunsch.elosystem.utils.EloPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This class handles the player quit listener that caches every value into the database.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.11.2022
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        EloPlayer eloPlayer = EloSystem.getEloAPI().getEloPlayer(player);
        // if the elo player is not null.
        if(eloPlayer != null) {
            EloSystem.getInstance().getEloDatabase().setElo(eloPlayer.getUniqueId(), player.getName(), eloPlayer.getElo());
            EloSystem.getInstance().getEloPlayers().remove(eloPlayer);
        }
    }
}