package br.com.redeend.factions.listeners;

import br.com.redeend.core.mysql.MySQL;
import br.com.redeend.core.rank.PlayerGroup;
import br.com.redeend.factions.manager.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.SQLException;

public class PlayerLocalChat implements Listener {

    private final FactionManager factionManager;
    private final MySQL mysqlCore;

    public PlayerLocalChat(FactionManager factionManager, MySQL mysqlCore) {
        this.factionManager = factionManager;
        this.mysqlCore = mysqlCore;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String factionTag = null;
        try {
            factionTag = factionManager.getFactionTag(player.getName());
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }
        ChatColor yellow = ChatColor.YELLOW;
        ChatColor gray = ChatColor.GRAY;
        ChatColor gold = ChatColor.GOLD;
        ChatColor white = ChatColor.WHITE;
        String ftag = "[" + factionTag + "] ";
        PlayerGroup group = mysqlCore.getPrimaryRank(player.getUniqueId());
        String groupTag = group.getTag() + " ";

        // Format for local chat
        if (factionTag != null) {
            event.setFormat(yellow + "[L] " + gray + ftag + white + groupTag + player.getName() + gray + ": " + message);
        } else {
            event.setFormat(yellow + "[L] " + white +               groupTag + player.getName() + gray + ": " + message);
        }


    }
}
