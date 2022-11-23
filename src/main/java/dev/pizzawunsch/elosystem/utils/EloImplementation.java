package dev.pizzawunsch.elosystem.utils;

import dev.pizzawunsch.elosystem.EloSystem;
import dev.pizzawunsch.elosystem.api.EloAPI;

import java.util.List;
import java.util.UUID;

public class EloImplementation implements EloAPI {

    @Override
    public List<EloPlayer> getEloPlayers() {
        return EloSystem.getInstance().getEloPlayers();
    }

    @Override
    public List<EloRank> getEloRanks() {
        return EloSystem.getInstance().getEloRanks();
    }

    @Override
    public EloPlayer getEloPlayer(UUID uuid) {
        return EloSystem.getInstance().getEloPlayers().stream().filter(eloPlayer -> eloPlayer.getUniqueId().equals(uuid)).findAny().orElse(null);
    }

    @Override
    public EloRank getEloRank(int elo) {
        return getEloRanks().stream().filter(eloRank -> eloRank.getRequiredElo() <= elo && eloRank.getMaxElo() >= elo).findAny().orElse(getEloRanks().stream().findFirst().orElse(null));
    }
}