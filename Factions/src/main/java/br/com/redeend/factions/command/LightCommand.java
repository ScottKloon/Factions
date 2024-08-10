package br.com.redeend.factions.command;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class LightCommand implements CommandExecutor {
    private final HashMap<UUID, Boolean> nightVisionState = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("luz")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                UUID playerUUID = player.getUniqueId();

                boolean isNightVisionActive = nightVisionState.getOrDefault(playerUUID, false);

                TextComponent ligar = new TextComponent("Ligar (verde) - Adiciona efeito Visão noturna\n");
                ligar.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                ligar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/luz ligar"));

                TextComponent desligar = new TextComponent("Desligar (vermelho) - Remove efeito Visão noturna\n");
                desligar.setColor(net.md_5.bungee.api.ChatColor.RED);
                desligar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/luz desligar"));

                player.spigot().sendMessage(ligar);
                player.spigot().sendMessage(desligar);

                if (isNightVisionActive) {
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    player.sendMessage("Visão noturna desativada.");
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, true, false));
                    player.sendMessage("Visão noturna ativada.");
                }

                nightVisionState.put(playerUUID, !isNightVisionActive);
                return true;
            } else {
                sender.sendMessage("Apenas jogadores podem usar esse comando.");
            }
        }
        return false;
    }
}
