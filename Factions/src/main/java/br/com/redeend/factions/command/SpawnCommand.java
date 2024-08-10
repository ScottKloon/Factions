package br.com.redeend.factions.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final FileConfiguration config;

    public SpawnCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("setspawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location location = player.getLocation();
                config.set("spawn.world", location.getWorld().getName());
                config.set("spawn.x", location.getX());
                config.set("spawn.y", location.getY());
                config.set("spawn.z", location.getZ());
                config.set("spawn.yaw", location.getYaw());
                config.set("spawn.pitch", location.getPitch());
                Bukkit.getPluginManager().getPlugin("YourPluginName").saveConfig();
                player.sendMessage(ChatColor.GREEN + "Spawn definido para sua localização atual.");
            } else {
                sender.sendMessage(ChatColor.RED + "Este comando só pode ser executado por um jogador.");
            }
            return true;
        } else if (label.equalsIgnoreCase("spawn")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String worldName = config.getString("spawn.world");
                double x = config.getDouble("spawn.x");
                double y = config.getDouble("spawn.y");
                double z = config.getDouble("spawn.z");
                float yaw = (float) config.getDouble("spawn.yaw");
                float pitch = (float) config.getDouble("spawn.pitch");

                Location spawnLocation = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
                player.teleport(spawnLocation);
                player.sendMessage(ChatColor.GREEN + "Você foi teletransportado para o spawn.");
            } else {
                sender.sendMessage(ChatColor.RED + "Este comando só pode ser executado por um jogador.");
            }
            return true;
        }
        return false;
    }
}
