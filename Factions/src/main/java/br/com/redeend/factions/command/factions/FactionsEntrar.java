package br.com.redeend.factions.command.factions;

import br.com.redeend.factions.command.Subcommand;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import org.bukkit.entity.Player;

public class FactionsEntrar extends Subcommand {
    @Override
    public String getName() {
        return "entrar";
    }

    @Override
    public String getDescription() {
        return "entra em uma facção";
    }

    @Override
    public String getSyntax() {
        return "/f entrar <tag>";
    }

    @Override
    public int getArgsSize() {
        return 1;
    }

    @Override
    public boolean execute(Player player, String[] args, FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager) {
        String joinTag = args[1];
        try {
            factionManager.joinFaction(player.getName(), joinTag);
            player.sendMessage("Você entrou na facção " + joinTag + "!");
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            return false;
        }
    }
}
