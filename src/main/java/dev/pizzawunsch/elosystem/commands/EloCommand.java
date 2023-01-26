package dev.pizzawunsch.elosystem.commands;

import com.google.common.collect.Maps;
import dev.pizzawunsch.elosystem.EloSystem;
import dev.pizzawunsch.elosystem.utils.AbstractCommand;
import dev.pizzawunsch.elosystem.utils.EloPlayer;
import dev.pizzawunsch.elosystem.utils.EloRank;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class handles the elo command that allows you to configure the different variables of some players.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.11.2022
 */
public class EloCommand extends AbstractCommand {

    /**
     * Creates and registers a new abstract command
     */
    public EloCommand() {
        super("elo");
    }

    @Override
    @SneakyThrows
    public boolean command(CommandSender commandSender, String[] args) {
        // if the console sender is a player.
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            EloPlayer eloPlayer = EloSystem.getEloAPI().getEloPlayer(player);
            // if the elo player is not null.
            if(eloPlayer != null) {
                // handles different argument lengths.
                if(args.length == 0) {
                    EloRank eloRank = EloSystem.getEloAPI().getEloRank(eloPlayer.getElo());
                    Map<String, String> replacement = Maps.newHashMap();
                    replacement.put("%elo%", eloPlayer.getElo() + "");
                    replacement.put("%rank%", EloSystem.getInstance().translate(eloRank.getName()));
                    EloSystem.getInstance().sendMessage(player, "elo_information", replacement);
                } else if(args.length == 3) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if(!player.hasPermission("elosystem.add")) {
                            EloSystem.getInstance().sendMessage(player, "no_permission");
                            return false;
                        }
                        String targetName = args[1];
                        Player target = Bukkit.getPlayer(targetName);
                        String amountString = args[2];
                        // if the second argument is an integer.
                        if (EloSystem.getInstance().isInteger(amountString)) {
                            int amount = Integer.parseInt(amountString);
                            // if the player is online.
                            if (target != null) {
                                EloPlayer eloTarget = EloSystem.getEloAPI().getEloPlayer(player);
                                if (eloTarget != null) {
                                    // Add elo to the target player.
                                    eloTarget.addElo(amount);
                                    Map<String, String> replacement = Maps.newHashMap();
                                    replacement.put("%elo%", eloTarget.getElo() + "");
                                    replacement.put("%player%", targetName);
                                    EloSystem.getInstance().sendMessage(player, "elo_edited", replacement);
                                } else // player does not exist.
                                    EloSystem.getInstance().sendMessage(player, "elo_player_does_not_exists");
                            } else
                                EloSystem.getInstance().sendMessage(player, "elo_player_is_not_online");
                        } else
                            EloSystem.getInstance().sendMessage(player, "elo_not_a_int");
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if(!player.hasPermission("elosystem.remove")) {
                            EloSystem.getInstance().sendMessage(player, "no_permission");
                            return false;
                        }
                        String targetName = args[1];
                        Player target = Bukkit.getPlayer(targetName);
                        String amountString = args[2];
                        // if the second argument is an integer.
                        if(EloSystem.getInstance().isInteger(amountString)) {
                            int amount = Integer.parseInt(amountString);
                            // if the player is online.
                            if(target != null) {
                                EloPlayer eloTarget = EloSystem.getEloAPI().getEloPlayer(player);
                                if(eloTarget != null) {
                                    // Add elo to the target player.
                                    eloTarget.removeElo(amount);
                                    Map<String, String> replacement = Maps.newHashMap();
                                    replacement.put("%elo%", eloTarget.getElo() + "");
                                    replacement.put("%player%", targetName);
                                    EloSystem.getInstance().sendMessage(player, "elo_edited", replacement);
                                } else // player does not exist.
                                    EloSystem.getInstance().sendMessage(player, "elo_player_does_not_exists");
                            } else
                                EloSystem.getInstance().sendMessage(player, "elo_player_is_not_online");
                        } else
                            EloSystem.getInstance().sendMessage(player, "elo_not_a_int");
                    } else // Wrong syntax.
                        EloSystem.getInstance().sendMessage(player, "elo_syntax");
                } else if(args.length == 1) {
                    if (args[0].equalsIgnoreCase("top")) {
                        // sends the top list of the players ranked by the elo.
                        List<String> messageList = EloSystem.getInstance().getMessageConfiguration().getMessageList("elo_top");
                        int rank = 0;
                        for(String message : messageList) {
                            if(message.contains("%player%")) {
                                rank++;
                                String name = "-";
                                String uuid = EloSystem.getInstance().getEloDatabase().getRank(rank).get();
                                EloPlayer eloTarget = null;
                                if(!uuid.equals("-")) {
                                    name = EloSystem.getInstance().getEloDatabase().getName(UUID.fromString(uuid)).get();
                                    eloTarget = EloSystem.getEloAPI().getEloPlayer(UUID.fromString(uuid));
                                }
                                if(name != null) {
                                    assert eloTarget != null;
                                    player.sendMessage(EloSystem.getInstance().translate(message).replace("%player%", name).replace("%elo%", eloTarget.getElo() + "").replace("%rank%", EloSystem.getEloAPI().getEloRank(eloTarget.getElo()) + ""));
                                }
                                else
                                    player.sendMessage(EloSystem.getInstance().translate(message).replace("%player%", "-"));
                            } else
                                player.sendMessage(EloSystem.getInstance().translate(message));
                        }
                    } else // Wrong syntax.
                        EloSystem.getInstance().sendMessage(player, "elo_syntax");
                } else // Wrong syntax.
                    EloSystem.getInstance().sendMessage(player, "elo_syntax");
            }
        }
        return false;
    }
}