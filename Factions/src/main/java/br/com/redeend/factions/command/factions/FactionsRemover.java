package br.com.redeend.factions.command.factions;

import br.com.redeend.factions.command.Subcommand;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import org.bukkit.entity.Player;

public class FactionsRemover extends Subcommand {
    @Override
    public String getName() {
        return "remover";
    }

    @Override
    public String getDescription() {
        return "Expulsa um jogador da facção";
    }

    @Override
    public String getSyntax() {
        return "/f remover <jogador>";
    }

    @Override
    public int getArgsSize() {
        return 1;
    }

    @Override
    public boolean execute(Player player, String[] args, FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager) {
        String removee = args[1];
        try {
            factionManager.removePlayer(player.getName(), removee);
            player.sendMessage("Jogador " + removee + " removido com sucesso!");
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            return false;
        }
    }
}
