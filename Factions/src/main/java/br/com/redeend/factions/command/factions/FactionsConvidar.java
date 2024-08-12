package br.com.redeend.factions.command.factions;

import br.com.redeend.factions.command.Subcommand;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import org.bukkit.entity.Player;

public class FactionsConvidar extends Subcommand {
    @Override
    public String getName() {
        return "convidar";
    }

    @Override
    public String getDescription() {
        return "convida um jogador a facção";
    }

    @Override
    public String getSyntax() {
        return "/f convidar <jogador>";
    }

    @Override
    public int getArgsSize() {
        return 1;
    }

    @Override
    public boolean execute(Player player, String[] args, FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager) {
        String invitee = args[1];
        try {
            factionManager.invitePlayer(player.getName(), invitee);
            player.sendMessage("Jogador " + invitee + " convidado com sucesso!");
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            return false;
        }
    }
}
