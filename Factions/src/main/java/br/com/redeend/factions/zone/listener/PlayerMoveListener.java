package br.com.redeend.factions.zone.listener;

import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final ZoneManager zoneManager;

    public PlayerMoveListener(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Chunk fromChunk = event.getFrom().getChunk();
        Chunk toChunk = event.getTo().getChunk();

        if (fromChunk.equals(toChunk)) {
            return; // Jogador não mudou de chunk
        }

        ZoneType fromZone = zoneManager.getZone(fromChunk);
        ZoneType toZone = zoneManager.getZone(toChunk);

        // Notificar o jogador se ele entrou em uma chunk dominada
        if (zoneManager.isChunkOwned(toChunk)) {
            String owner = zoneManager.getChunkOwner(toChunk);
            player.sendMessage(ChatColor.RED + "Você entrou na zona inimiga de " + owner + "!");

            // Notificar o dono da chunk
            Player ownerPlayer = event.getPlayer().getServer().getPlayer(owner);
            if (ownerPlayer != null) {
                ownerPlayer.sendMessage(ChatColor.RED + player.getName() + " entrou na sua zona dominada!");
            }
        }

        // Mensagem de zona ao jogador
        player.sendMessage(ChatColor.YELLOW + "Você entrou em " + toZone.getName());
    }
}
