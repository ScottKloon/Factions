package br.com.redeend.factions.zone.listener;

import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;

public class ZonePvP implements Listener {

    private final ZoneManager zoneManager;

    public ZonePvP(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player target = (Player) event.getEntity();

            ZoneType damagerZoneType = zoneManager.getZone(damager.getLocation().getChunk());
            ZoneType targetZoneType = zoneManager.getZone(target.getLocation().getChunk());

            if (damagerZoneType == ZoneType.PROTEGIDA || targetZoneType == ZoneType.PROTEGIDA) {
               // damager.sendMessage("PvP não é permitido em zonas neutras.");
                event.setCancelled(true);
            }
        }
    }
}