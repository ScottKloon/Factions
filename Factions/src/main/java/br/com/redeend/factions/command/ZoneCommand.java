package br.com.redeend.factions.command;

import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZoneCommand implements CommandExecutor {

    private final ZoneManager zoneManager;

    public ZoneCommand(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("Uso: /zona definir <livre/guerra/neutra> | /zona ver");
            return true;
        }

        if (args[0].equalsIgnoreCase("definir") && args.length == 2) {
           ZoneType type;
            try {
                type = ZoneType.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage("Tipo de zona inválido. Use: livre, guerra ou neutra.");
                return true;
            }

            Chunk chunk = player.getLocation().getChunk();
            zoneManager.setZone(chunk, type, player.getName());
            player.sendMessage("Zona definida para " + type.name().toLowerCase() + ".");
            return true;
        } else if (args[0].equalsIgnoreCase("ver")) {
            Chunk chunk = player.getLocation().getChunk();
            ZoneType type = zoneManager.getZone(chunk);
            player.sendMessage("Você está em uma zona " + type.name().toLowerCase() + ".");
            return true;
        }

        player.sendMessage("Uso: /zona definir <livre/guerra/neutra> | /zona ver");
        return true;
    }
}
