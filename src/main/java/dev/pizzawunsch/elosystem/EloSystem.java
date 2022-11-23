package dev.pizzawunsch.elosystem;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.ClassPath;
import dev.pizzawunsch.elosystem.api.EloAPI;
import dev.pizzawunsch.elosystem.configuration.MessageConfiguration;
import dev.pizzawunsch.elosystem.database.EloDatabase;
import dev.pizzawunsch.elosystem.database.mysql.MySQL;
import dev.pizzawunsch.elosystem.configuration.MainConfiguration;
import dev.pizzawunsch.elosystem.utils.EloImplementation;
import dev.pizzawunsch.elosystem.utils.EloPlayer;
import dev.pizzawunsch.elosystem.utils.EloRank;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class EloSystem extends JavaPlugin {

    // instance variables.
    @Getter
    private static EloSystem instance;
    @Getter
    private static EloAPI eloAPI;
    private MainConfiguration mainConfiguration;
    private MessageConfiguration messageConfiguration;
    private MySQL mySQL;
    private ExecutorService executorService;
    private EloDatabase eloDatabase;
    private List<EloPlayer> eloPlayers;
    private List<EloRank> eloRanks;

    @Override
    public void onEnable() {
        // Initializing the instance variables.
        instance = this;
        eloAPI = new EloImplementation();
        this.mainConfiguration = new MainConfiguration();
        this.messageConfiguration = new MessageConfiguration();
        this.eloDatabase = new EloDatabase();
        this.executorService = Executors.newCachedThreadPool();
        this.eloPlayers = Lists.newArrayList();
        this.eloRanks = Lists.newArrayList();
        // Establishing the mysql database connection.
        this.mySQL = new MySQL(
                this.mainConfiguration.getConfig().getString("database.host"),
                this.mainConfiguration.getConfig().getString("database.port"),
                this.mainConfiguration.getConfig().getString("database.database"),
                this.mainConfiguration.getConfig().getString("database.username"),
                this.mainConfiguration.getConfig().getString("database.password"));
        this.mySQL.connect();
        this.mySQL.update("CREATE TABLE IF NOT EXISTS elosystem_elo(player varchar(64), name varchar(16), elo INT NOT NULL, UNIQUE(player), PRIMARY KEY(player))");
        this.register("dev.pizzawunsch.elosystem.commands", "dev.pizzawunsch.elosystem.listeners");
        // Reads the elo rank entire.
        this.mainConfiguration.read();
        this.reconnect();
    }

    /**
     * This method will start a new task to reconnect the database in a given interval.
     */
    void reconnect() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            // reconnecting.
            this.mySQL.disconnect();
            this.mySQL.connect();
        }, (20 * 60) * 5, (20 * 60) * 5);
    }

    /**
     * This method allows you to send a message with the given replacements.
     * @param player the player who should receive this message.
     * @param key the message key.
     * @param replacement the replacements that should be applied.
     */
    public void sendMessage(Player player, String key, Map<String, String> replacement) {
        this.messageConfiguration.sendMessage(player, key, replacement);
    }

    /**
     * This method allows you to translate a raw string to all relevant variables that needed to replace.
     * @param message the message who should replace.
     * @return the final string.
     */
    public String translate(String message) {
        return this.messageConfiguration.translate(message);
    }

    /**
     * This method allows you to send a message with the given message key.
     * @param player the player who should receive this message.
     * @param key the message key.
     */
    public void sendMessage(Player player, String key) {
        this.messageConfiguration.sendMessage(player, key, Maps.newHashMap());
    }

    /**
     * Registers a command and listener package.
     *
     * @param commandPackageName      the name of the command package
     * @param listenerPackageNameList the name of the listener package
     */
    void register(String commandPackageName, String... listenerPackageNameList) {
        try {
            for (String listenerPackageName : listenerPackageNameList) {
                for (ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader())
                        .getTopLevelClasses(listenerPackageName)) {
                    Class<?> currentClass = Class.forName(classInfo.getName());
                    if (Listener.class.isAssignableFrom(currentClass))
                        Bukkit.getPluginManager().registerEvents((Listener) currentClass.newInstance(), this);
                }
            }
            for (ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader())
                    .getTopLevelClasses(commandPackageName)) {
                Class<?> currentClass = Class.forName(classInfo.getName());
                currentClass.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    /**
     * @param amountString the amount cast as a string
     * @return if given string is a integer.
     */
    public boolean isInteger(String amountString) {
        try {
            Integer.parseInt(amountString);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}