package br.com.redeend.factions.zone.listener;

import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.Player;

public class ZoneBlockBreak implements Listener {

    private final ZoneManager zoneManager;

    public ZoneBlockBreak(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ZoneType zoneType = zoneManager.getZone(player.getLocation().getChunk());

        if (zoneType != ZoneType.LIVRE) {
           // player.sendMessage("Você só pode quebrar blocos em zonas livres.");
            event.setCancelled(true);
        }
    }
}