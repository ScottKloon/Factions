package br.com.redeend.factions.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerBlockEvents implements Listener {

    @EventHandler
    public void entrar(PlayerJoinEvent event){
        event.setJoinMessage(null);
    }

    @EventHandler
    public void sair(PlayerQuitEvent event){
        event.setQuitMessage(null);
    }
    @EventHandler
    public void conquista(PlayerAchievementAwardedEvent event){
        event.setCancelled(true);
    }
}
