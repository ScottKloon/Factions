package br.com.redeend.factions.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bancada")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.openWorkbench(player.getLocation(), true);
                player.sendMessage("Bancada de trabalho aberta.");
                return true;
            } else {
                sender.sendMessage("Apenas jogadores podem usar esse comando.");
            }
        }
        return false;
    }
}
