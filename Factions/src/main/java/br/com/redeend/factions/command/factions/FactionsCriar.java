package br.com.redeend.factions.command.factions;

import br.com.redeend.factions.command.Subcommand;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import org.bukkit.entity.Player;

public class FactionsCriar extends Subcommand {
    @Override
    public String getName() {
        return "criar";
    }

    @Override
    public String getDescription() {
        return "Criar uma facção";
    }

    @Override
    public String getSyntax() {
        return "/f criar <tag> <nome>";
    }

    @Override
    public int getArgsSize() {
        return 2;
    }

    @Override
    public boolean execute(Player player, String[] args, FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager) {
        String tag = args[1];
        String name = args[2];
        try {
            factionManager.createFaction(tag, name, player.getName());
            player.sendMessage("Facção " + tag + " " + name + " criada com sucesso!");
            playerScore.scoreWithFaction(player, tag);
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            return false;
        }
    }
}
