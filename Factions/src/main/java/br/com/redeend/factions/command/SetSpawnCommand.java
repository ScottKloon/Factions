package br.com.redeend.factions.command;

import br.com.redeend.core.mysql.MySQL;
import br.com.redeend.core.rank.PlayerGroup;
import br.com.redeend.factions.Factions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final Factions plugin;
    private final MySQL mysql;
    public SetSpawnCommand(Factions plugin, MySQL mysql) {
        this.plugin = plugin;
        this.mysql = mysql;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {


            Player player = (Player) sender;
            Location loc = player.getLocation();
            PlayerGroup playerGroup = mysql.getPrimaryRank(player.getUniqueId());
            if (playerGroup.MaiorOuIgual(PlayerGroup.MANAGER)) {
                FileConfiguration config = plugin.getConfig();
                config.set("spawn.world", loc.getWorld().getName());
                config.set("spawn.x", loc.getX());
                config.set("spawn.y", loc.getY());
                config.set("spawn.z", loc.getZ());
                config.set("spawn.yaw", loc.getYaw());
                config.set("spawn.pitch", loc.getPitch());
                plugin.saveConfig();

                player.sendMessage(ChatColor.GREEN + "O spawn foi definido.");
                return true;
            }

        }
        return false;
    }
}