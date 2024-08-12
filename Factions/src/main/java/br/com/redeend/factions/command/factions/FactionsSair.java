package br.com.redeend.factions.command.factions;

import br.com.redeend.factions.command.Subcommand;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import org.bukkit.entity.Player;

public class FactionsSair extends Subcommand {
    @Override
    public String getName() {
        return "sair";
    }

    @Override
    public String getDescription() {
        return "sair da facção";
    }

    @Override
    public String getSyntax() {
        return "/f sair";
    }

    @Override
    public int getArgsSize() {
        return 0;
    }

    @Override
    public boolean execute(Player player, String[] args, FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager) {
        try {
            factionManager.leaveFaction(player.getName());
            player.sendMessage("Você saiu da facção.");
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            return false;
        }
    }
}
