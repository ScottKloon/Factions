package br.com.redeend.factions.listeners;

import br.com.redeend.core.mysql.MySQL;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.sql.SQLException;

public class PlayerScore implements Listener {

    private final FactionManager factionManager;
    private final ZoneManager zoneManager;
    private final MySQL mysqlCore;

    public PlayerScore(FactionManager factionManager, ZoneManager zoneManager, MySQL mysqlCore) {
        this.factionManager = factionManager;
        this.zoneManager = zoneManager;
        this.mysqlCore = mysqlCore;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String factionTag;

        try {
            factionTag = factionManager.getFactionTag(player.getName());

        } catch (SQLException e) {
            player.sendMessage("Erro ao obter a tag da facção.");
            e.printStackTrace();
            return;
        }

        if (factionTag != null) {
            scoreWithFaction(player, factionTag);
        } else {
            scoreNotInFaction(player);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Chunk fromChunk = event.getFrom().getChunk();
        Chunk toChunk = event.getTo().getChunk();

        if (!fromChunk.equals(toChunk)) {
            updateScoreboard(player);
        }
    }

    private void updateScoreboard(Player player) {
        String factionTag;
        try {
            factionTag = factionManager.getFactionTag(player.getName());
        } catch (SQLException e) {
            player.sendMessage("Erro ao obter a tag da facção.");
            e.printStackTrace();
            return;
        }

        if (factionTag != null) {
            scoreWithFaction(player, factionTag);
        } else {
            scoreNotInFaction(player);
        }
    }

    public void scoreNotInFaction(Player player) {
        int minerLevel;
        String zoneName = getZoneName(player);

        int cash = mysqlCore.getCash(player.getUniqueId());
        int coins = mysqlCore.getCoins(player.getUniqueId());

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Factions", "dummy");
        objective.setDisplayName(zoneName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("§1"                          ).setScore(10);
        objective.getScore(ChatColor.WHITE + " KDR: " + ChatColor.YELLOW + "0").setScore(9);
        objective.getScore(ChatColor.WHITE + " Poder: "  ).setScore(8);
        objective.getScore("§2"                          ).setScore(7);
        objective.getScore(ChatColor.GRAY + " Sem facção").setScore(6);
        objective.getScore("§3"                          ).setScore(5);
        objective.getScore(ChatColor.WHITE + " Moedas: " + ChatColor.GREEN + coins ).setScore(4);
        objective.getScore(ChatColor.WHITE + " Dinheiro: " + ChatColor.GREEN + cash  ).setScore(3);
        objective.getScore("§4"                          ).setScore(2);
        objective.getScore(ChatColor.YELLOW + "www.redeend.com").setScore(1);

        player.setScoreboard(scoreboard);
    }

    public void scoreWithFaction(Player player, String factionTag) {
        int cash = mysqlCore.getCash(player.getUniqueId());
        int coins = mysqlCore.getCoins(player.getUniqueId());
        int minerLevel;
        String zoneName = getZoneName(player);
        String nameFactions;
        try {

            nameFactions = factionManager.getNameFactions(player.getName());
        } catch (SQLException e) {
            player.sendMessage("Erro ao carregar nível da habilidade.");
            e.printStackTrace();
            return;
        }

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        String tag = " [" + factionTag + "]";
        Objective objective = scoreboard.registerNewObjective("Factions", "dummy");
        objective.setDisplayName(zoneName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("§1"                          ).setScore(12);
        objective.getScore(ChatColor.WHITE + " KDR: " + ChatColor.YELLOW +  "0").setScore(11);
        objective.getScore(ChatColor.WHITE + " Poder: " + ChatColor.YELLOW + "0").setScore(10);
        objective.getScore("§2"                          ).setScore(9);
        objective.getScore(ChatColor.GOLD + tag + " " + nameFactions       ).setScore(8);
        objective.getScore(ChatColor.WHITE + "  Poder: "+ ChatColor.GREEN + "0"  ).setScore(7);
        objective.getScore(ChatColor.WHITE + "  Terras: "+ ChatColor.GREEN + "0" ).setScore(6);
        objective.getScore("§3"                          ).setScore(5);
        objective.getScore(ChatColor.WHITE + " Moedas: " + ChatColor.GREEN + coins  ).setScore(4);
        objective.getScore(ChatColor.WHITE + " Dinheiro: " + ChatColor.GREEN + cash   ).setScore(3);
        objective.getScore("§4"                          ).setScore(2);
        objective.getScore(ChatColor.YELLOW + "www.redeend.com").setScore(1);

        player.setScoreboard(scoreboard);
    }

    private String getZoneName(Player player) {
        ZoneType zoneType = zoneManager.getZone(player.getLocation().getChunk());
        return zoneType.getName();
    }
}
