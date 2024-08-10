package br.com.redeend.factions.command;

import br.com.redeend.core.mysql.MySQL;
import br.com.redeend.core.rank.PlayerGroup;
import br.com.redeend.factions.manager.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerGlobalChat implements CommandExecutor {

    private final FactionManager factionManager;
    private final MySQL mysqlCore;

    public PlayerGlobalChat(FactionManager factionManager, MySQL mysqlCore) {
        this.factionManager = factionManager;
        this.mysqlCore = mysqlCore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("g")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Apenas jogadores podem usar este comando.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage("Uso: /g <mensagem>");
                return true;
            }

            String message = String.join(" ", args);
            String factionTag = null;

            try {
                factionTag = factionManager.getFactionTag(player.getName());
            } catch (Exception e) {
                player.sendMessage("Erro ao obter a tag da facção.");
                e.printStackTrace();
                return true;
            }

            ChatColor yellow = ChatColor.YELLOW;
            ChatColor gold = ChatColor.GOLD;
            ChatColor gray = ChatColor.GRAY;
            ChatColor white = ChatColor.WHITE;
            String ftag = "[" + factionTag + "] ";
            PlayerGroup group = mysqlCore.getPrimaryRank(player.getUniqueId());
            String groupTag = group.getTag() + " ";

            String format = (factionTag != null) ?
                    gray + "[G] " + gray  + ftag + groupTag + white + player.getName() + gray + ": " + white + message :
                    gray + "[G] " + white +        groupTag + white + player.getName() + gray + ": " + white + message;

            for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
                onlinePlayer.sendMessage(format);
            }

            return true;
        }
        return false;
    }
}
