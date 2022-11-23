package dev.pizzawunsch.elosystem.listeners;

import dev.pizzawunsch.elosystem.EloSystem;
import dev.pizzawunsch.elosystem.utils.EloPlayer;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    @SneakyThrows
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Caching all relevant variables of the elo player from the database.
        int elo = EloSystem.getInstance().getEloDatabase().getElo(player.getUniqueId()).get();
        EloPlayer eloPlayer = new EloPlayer(player.getUniqueId(), elo);
        EloSystem.getInstance().getEloPlayers().add(eloPlayer);
    }

}