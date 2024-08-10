package br.com.redeend.factions.command;

import br.com.redeend.core.mysql.MySQL;
import br.com.redeend.factions.inventory.FactionsInventory;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionsCommand implements CommandExecutor {

    private final FactionManager factionManager;
    private final PlayerScore playerScore;
    private final ZoneManager zoneManager;

    private final MySQL mysqlCore;
    public FactionsCommand(FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager, MySQL mysqlCore) {
        this.factionManager = factionManager;
        this.playerScore = playerScore;
        this.zoneManager = zoneManager;
        this.mysqlCore = mysqlCore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;

        if (factionManager == null) {
            player.sendMessage("Gerenciador de facções não está disponível.");
            return true;
        }

        if (args.length == 0) {
            openFactionsInventory(player);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        if (subCommand.equals("criar")) {
            if (args.length < 3) {
                player.sendMessage("Use: /f criar <tag> <nome>");
                return true;
            }
            String tag = args[1];
            String name = args[2];
            try {
                factionManager.createFaction(tag, name, player.getName());
                player.sendMessage("Facção " + tag + " " + name + " criada com sucesso!");
                playerScore.scoreWithFaction(player, tag);
            } catch (Exception e) {
                player.sendMessage(e.getMessage());
            }
        } else
            if (subCommand.equals("convidar")) {
            if (args.length < 2) {
                player.sendMessage("Uso: /f convidar <jogador>");
                return true;
            }
            String invitee = args[1];
            try {
                factionManager.invitePlayer(player.getName(), invitee);
                player.sendMessage("Jogador " + invitee + " convidado com sucesso!");
            } catch (Exception e) {
                player.sendMessage(e.getMessage());
            }
        } else
            if (subCommand.equals("entrar")) {
            if (args.length < 2) {
                player.sendMessage("Uso: /f entrar <tag>");
                return true;
            }
            String joinTag = args[1];
            try {
                factionManager.joinFaction(player.getName(), joinTag);
                player.sendMessage("Você entrou na facção " + joinTag + "!");
            } catch (Exception e) {
                player.sendMessage(e.getMessage());
            }
        } else
            if (subCommand.equals("sair")) {
            try {
                factionManager.leaveFaction(player.getName());
                player.sendMessage("Você saiu da facção.");
            } catch (Exception e) {
                player.sendMessage(e.getMessage());
            }
        } else
            if (subCommand.equals("remover")) {
            if (args.length < 2) {
                player.sendMessage("Uso: /f remover <jogador>");
                return true;
            }
            String removee = args[1];
            try {
                factionManager.removePlayer(player.getName(), removee);
                player.sendMessage("Jogador " + removee + " removido com sucesso!");
            } catch (Exception e) {
                player.sendMessage(e.getMessage());
            }
        } else
            if (subCommand.equals("desfazer")) {
            try {
                factionManager.disbandFaction(player.getName());
                player.sendMessage("Facção desfeita com sucesso!");
            } catch (Exception e) {
                player.sendMessage(e.getMessage());
            }
        } else
            if (subCommand.equalsIgnoreCase("dominar")) {
            if (factionManager.getPlayerFaction(player.getName()) == null) {
                player.sendMessage("Você precisa estar em uma facção para dominar uma área.");
                return true;
            }

            zoneManager.setZone(player.getLocation().getChunk(), ZoneType.INIMIGA, factionManager.getPlayerFaction(player.getName()));
            player.sendMessage("Você dominou esta área!");
            return true;
        }




        else {
            player.sendMessage("Comando desconhecido.");
        }
        return true;
    }

    private void openFactionsInventory(Player player) {
        FactionsInventory factionsInventory = new FactionsInventory(factionManager, player, mysqlCore);
        factionsInventory.openFactionsInventory();
    }
}
