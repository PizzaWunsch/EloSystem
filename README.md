# EloSystem Spigot Plugin

Das EloSystem Spigot Plugin ist ein Plugin, welches ein ELO-System für Minecraft Server implementiert. Das Plugin bietet eine API, die es Entwicklern ermöglicht, auf die Elo-Daten und Ränge der Spieler zuzugreifen und diese zu nutzen.

## Features

ELO-System: Das Plugin implementiert ein ELO-System, welches den Spielern basierend auf ihren Siegen und Niederlagen in PvP-Kämpfen Elo-Punkte zuweist.
Rangsystem: Das Plugin bietet ein Rangsystem, welches die Spieler basierend auf ihren Elo-Punkten in verschiedene Ränge einteilt.
API: Das Plugin bietet eine API, die es Entwicklern ermöglicht, auf die Elo-Daten und Ränge der Spieler zuzugreifen und diese zu nutzen.
Verwendung der API
Die API des Plugins bietet eine Reihe von Methoden, mit denen Entwickler auf die Elo-Daten und Ränge der Spieler zugreifen können. Hier sind einige Beispiele:

```java
// Erhalte eine Liste mit allen Elo-Spielern, die derzeit online sind.
List<EloPlayer> eloPlayers = EloAPI.getEloPlayers();

// Erhalte eine Liste mit allen verfügbaren Elo-Rängen.
List<EloRank> eloRanks = EloAPI.getEloRanks();

// Erhalte den Elo-Spieler anhand seiner UUID.
EloPlayer eloPlayer = EloAPI.getEloPlayer(player.getUniqueId());

// Erhalte den Elo-Rang basierend auf der gegebenen Elo-Punktzahl.
EloRank eloRank = EloAPI.getEloRank(elo);
```
*Weitere Informationen zur Verwendung der API finden Sie in der Dokumentation im Quellcode des Plugins.*

## Installation
Um das EloSystem Spigot Plugin auf Ihrem Minecraft-Server zu installieren, führen Sie die folgenden Schritte aus:

1. Laden Sie das Plugin herunter (https://discord.gg/ackqcVypGb)
2. Kopieren Sie die .jar-Datei in den "plugins" Ordner Ihres Minecraft-Servers.
3. Starten Sie den Server neu oder laden Sie das Plugin über den Plugin-Manager neu.
