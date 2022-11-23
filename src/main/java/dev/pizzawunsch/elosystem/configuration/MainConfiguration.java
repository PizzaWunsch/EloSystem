package dev.pizzawunsch.elosystem.configuration;

import dev.pizzawunsch.elosystem.EloSystem;
import dev.pizzawunsch.elosystem.utils.EloRank;

public class MainConfiguration extends AbstractConfiguration {

    /**
     * This is the constructor of the abstract configuration file.
     * It will initialize all corresponding variables.
     */
    public MainConfiguration() {
        super("plugins/EloSystem/", "config.yml");
    }

    /**
     * Reads all elo ranks of this plugin/api.
     */
    public void read() {
        if(this.getConfig().getConfigurationSection("ranks") != null) {
            for(String key : this.getConfig().getConfigurationSection("ranks").getKeys(false)) {
                String name = this.getConfig().getString("ranks." + key + ".name");
                int requiredElo = this.getConfig().getInt("ranks." + key + ".requiredElo");
                int maxElo = this.getConfig().getInt("ranks." + key + ".maxElo");
                EloSystem.getInstance().getEloRanks().add(new EloRank(key, name, requiredElo, maxElo));
            }
        }
    }

}