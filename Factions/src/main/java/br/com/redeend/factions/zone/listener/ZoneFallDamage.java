package br.com.redeend.factions.zone.listener;

import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Player;

public class ZoneFallDamage implements Listener {

    private final ZoneManager zoneManager;

    public ZoneFallDamage(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                ZoneType zoneType = zoneManager.getZone(player.getLocation().getChunk());
                if (zoneType == ZoneType.PROTEGIDA) {
                    event.setCancelled(true);
                }
            }
        }
    }
}