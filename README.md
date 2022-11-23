# EloSystem

Das Elo-System ist ein Plugin, welches dir mit Hilfe seiner API die Möglichkeit gibt, jegliche Funktionen dieses Plugins auszuschöpfen.
Jegliche Nachrichten sind **einstellbar** und eine Datenbankverbindung wird **vorrausgesetzt**.

## EloPlayer/-EloRank Objekte
```java
@EventHandler
public void onJoin(PlayerJoinEvent event) {
  Player player = event.getPlayer();
  EloPlayer eloPlayer = EloSystem.getEloAPI().getEloPlayer(player);
  EloRank eloRank = EloSystem.getEloAPI().getEloRank(eloPlayer.getElo());
  player.sendMessage("Dein Rank: " + eloRank.getName());
  eloPlayer.addElo(100);
}
```

## Events
Das EloRankChangeEvent wird ausgeführt, wenn sich der Rang eines Spieler verändert, währendessen das PlayerEloUpdateEvent ausgeführt wird,
wenn sich das Elo eines Spielers aktualisiert. 

```java
public class TestListener implements Listener {

  @EventHandler
  public void onEloRankChange(EloRankChangeEvent event) {
  ...
  }

  @EventHandler
  public void onPlayerEloUpdate(PlayerEloUpdateEvent event) {
  ...
  }
}
```
