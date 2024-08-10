package br.com.redeend.factions.zone.listener;

import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ZoneMove implements Listener {

    private final ZoneManager zoneManager;

    public ZoneMove(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getChunk() != event.getTo().getChunk()) {
            ZoneType type = zoneManager.getZone(event.getTo().getChunk());

        }
    }
}