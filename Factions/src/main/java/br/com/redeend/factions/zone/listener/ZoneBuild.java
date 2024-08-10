package br.com.redeend.factions.zone.listener;

import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.Player;

public class ZoneBuild implements Listener {

    private final ZoneManager zoneManager;

    public ZoneBuild(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ZoneType zoneType = zoneManager.getZone(player.getLocation().getChunk());

        if (zoneType != ZoneType.LIVRE) {
           // player.sendMessage("Você só pode colocar blocos em zonas livres.");
            event.setCancelled(true);
        }
    }

}