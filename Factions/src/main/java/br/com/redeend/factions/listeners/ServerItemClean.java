package br.com.redeend.factions.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerItemClean extends BukkitRunnable {

    @Override
    public void run() {
        int removedItemsCount = 0;

        for (org.bukkit.World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                    removedItemsCount++;
                }
            }
        }

        Bukkit.broadcastMessage("§e" + removedItemsCount + " itens no chão foram removidos.");
    }
}