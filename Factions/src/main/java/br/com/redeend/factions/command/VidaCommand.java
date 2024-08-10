package br.com.redeend.factions.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VidaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Regenera a vida do jogador
            double maxHealth = player.getMaxHealth();
            player.setHealth(maxHealth);
            player.sendMessage("Sua vida foi regenerada!");
            return true;
        } else {
            sender.sendMessage("Este comando só pode ser usado por jogadores!");
            return false;
        }
    }
}
