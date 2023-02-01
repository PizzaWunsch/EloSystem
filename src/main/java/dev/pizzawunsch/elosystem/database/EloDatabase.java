package dev.pizzawunsch.elosystem.database;

import dev.pizzawunsch.elosystem.EloSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class EloDatabase {

    private final String table = "elosystem_elo";

    public Future<Integer> getElo(UUID uuid) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        EloSystem.getInstance().getExecutorService().submit(()-> {
            try {
                if(isRegistered(uuid).get()) {
                    final PreparedStatement preparedStatement = EloSystem.getInstance().getMySQL()
                            .prepare("SELECT * FROM " + table + " WHERE `player` = '" + uuid + "'");
                    final ResultSet result = preparedStatement.executeQuery();
                    if(result.next())
                        completableFuture.complete(result.getInt("elo"));
                    completableFuture.complete(-1);
                } else {
                    setElo(uuid, "-", EloSystem.getInstance().getMainConfiguration().getConfig().getInt("default_elo"));
                    completableFuture.complete(EloSystem.getInstance().getMainConfiguration().getConfig().getInt("default_elo"));
                }
            } catch(Exception exception) {
                completableFuture.complete(-1);
                exception.printStackTrace();
            }
        });
        return completableFuture;
    }

    public Future<String> getName(UUID uuid) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        EloSystem.getInstance().getExecutorService().submit(()-> {
            try {
                if(isRegistered(uuid).get()) {
                    final PreparedStatement preparedStatement = EloSystem.getInstance().getMySQL()
                            .prepare("SELECT * FROM " + table + " WHERE `player` = '" + uuid + "'");
                    final ResultSet result = preparedStatement.executeQuery();
                    if(result.next())
                        completableFuture.complete(result.getString("name"));
                    completableFuture.complete("-");
                } else {
                    setElo(uuid, "-", 0);
                    completableFuture.complete("-");
                }
            } catch(Exception exception) {
                completableFuture.complete("-");
                exception.printStackTrace();
            }
        });
        return completableFuture;
    }

    public void setElo(UUID uuid, String name, int elo) {
        EloSystem.getInstance().getExecutorService().submit(()-> {
            try {
                if (this.isRegistered(uuid).get()) {
                    EloSystem.getInstance().getMySQL().update("UPDATE " + table + " SET `elo` = '" + elo + "', `name` = '" + name +  "' WHERE `player` = '" + uuid + "'");
                } else {
                    EloSystem.getInstance().getMySQL().update("INSERT INTO " + table + "(player, name, elo) VALUES ('" + uuid + "', '" + name + "', '" + elo + "')");
                }
            } catch(Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public Future<Boolean> isRegistered(UUID uuid) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        EloSystem.getInstance().getExecutorService().submit(()-> {
            try {
                final PreparedStatement preparedStatement = EloSystem.getInstance().getMySQL()
                        .prepare("SELECT * FROM " + table + " WHERE `player` = '" + uuid + "'");
                final ResultSet result = preparedStatement.executeQuery();
                completableFuture.complete(result.next());
            } catch(Exception exception) {
                completableFuture.complete(false);
                exception.printStackTrace();
            }
        });
        return completableFuture;
    }

    /**
     * @param id the ranking of which the uuid should be determined of
     * @return the uuid of the player at the given ranking
     */
    public Future<String> getRank(int id) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        EloSystem.getInstance().getExecutorService().submit(() -> {
            int current = 1;
            try {
                PreparedStatement preparedStatement = EloSystem.getInstance().getMySQL()
                        .prepare("SELECT player FROM " + table + " ORDER BY elo DESC");
                ResultSet result = preparedStatement.executeQuery();
                while (current <= id) {
                    if (current == id && result.next()) {
                        completableFuture.complete(result.getString("player"));
                        return null;
                    }
                    result.next();
                    current++;
                }
                result.close();
                preparedStatement.close();
            } catch (Exception exception) {
                completableFuture.complete("-");
                return null;
            }
            completableFuture.complete("-");
            return null;
        });
        return completableFuture;
    }

}