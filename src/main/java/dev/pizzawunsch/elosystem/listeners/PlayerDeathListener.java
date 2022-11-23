package dev.pizzawunsch.elosystem.listeners;

import dev.pizzawunsch.elosystem.EloSystem;
import dev.pizzawunsch.elosystem.utils.EloPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * This class handles the death of a player and will give and remove the elo of the killer and the dead player.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.11.2022
 */
public class PlayerDeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        // the player who died.
        Player player = event.getEntity();
        // the elo player object of this player.
        EloPlayer eloPlayer = EloSystem.getEloAPI().getEloPlayer(player);
        // if the elo player is not null.
        if(eloPlayer != null) {
            // the killer of the player.
            Player killer = player.getKiller();
            // if the killer is not null
            if(killer != null) {
                EloPlayer eloKiller = EloSystem.getEloAPI().getEloPlayer(killer);
                // if the elo killer is not null.
                if(eloKiller != null) {
                    // Adds the elo to the killer.
                    double eloToAdd = eloPlayer.getElo() * (EloSystem.getInstance().getMainConfiguration().getConfig().getInt("percentage.attacker")/100d);
                    eloKiller.addElo((int) Math.round(eloToAdd));
                }
            }
            // Removes the elo from the player.
            double eloToRemove = eloPlayer.getElo() * (EloSystem.getInstance().getMainConfiguration().getConfig().getInt("percentage.victim")/100d);
            eloPlayer.removeElo((int) Math.round(eloToRemove));
        }

    }
}