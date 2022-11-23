package dev.pizzawunsch.elosystem.configuration;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * This class handles the message configuration file of the elo system.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 23.11.2022
 */
public class MessageConfiguration extends AbstractConfiguration{

    /**
     * This is the constructor of the abstract configuration file.
     * It will initialize all corresponding variables.
     */
    public MessageConfiguration() {
        super("plugins/EloSystem/", "messages.yml");
    }

    /**
     * This method will send dynamically a single or multiple messages by one message key.
     * @param player the player who should receive the message.
     * @param key the key of the message.
     * @param replace the replacements of the messages.
     */
    public void sendMessage(Player player, String key, Map<String, String> replace) {
        List<String> messagesToSend = Lists.newArrayList();
        if(this.getConfig().isList(key))
            messagesToSend.addAll(getMessageList(key));
        else
            messagesToSend.add(getMessage(key));
        replace.forEach((replaceKey, replacement) -> {
            messagesToSend.replaceAll(s -> s.replace(replaceKey, replacement));
        });
        messagesToSend.forEach(player::sendMessage);
    }

    /**
     * This method gets a message from the message.yml config file.
     * @param key the key of the message.
     * @return the message of the key.
     */
    public String getMessage(String key) {
        return this.translate(this.getConfig().getString(key));
    }

    /**
     * This method translates a message with all relevant variables and chatcolors.
     * @param message the message that should be translated.
     * @return the translated message.
     */
    public String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("%prefix%", this.getConfig().getString("prefix")));
    }

    /**
     * This method returns a list with all messages from the given message key.
     * @param key the key of the messages.
     * @return the messages of the key.
     */
    public List<String> getMessageList(String key) {
        List<String> messages = Lists.newArrayList();
        for(String message : this.getConfig().getStringList(key))
            messages.add(this.translate(message));
        return messages;
    }
}