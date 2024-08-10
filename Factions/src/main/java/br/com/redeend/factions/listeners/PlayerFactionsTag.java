package br.com.redeend.factions.listeners;

import br.com.redeend.core.mysql.MySQL;
import br.com.redeend.core.rank.PlayerGroup;
import br.com.redeend.factions.manager.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerFactionsTag implements Listener {

    private final MySQL mysqlCore;
    private final FactionManager factionManager;

    public PlayerFactionsTag(MySQL mysqlCore, FactionManager factionManager) {
        this.mysqlCore = mysqlCore;
        this.factionManager = factionManager;
    }

    @EventHandler
    public void setTag(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();

        String tag = ChatColor.GOLD + " " + factionManager.getFactionTag(player.getName());


        if (tag != null) {


        } else {

            br.com.redeend.factions.api.TagsAPI.setTag(player, tag);
        }
    }
}
