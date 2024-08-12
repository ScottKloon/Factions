package br.com.redeend.factions.command.factions;

import br.com.redeend.factions.command.Subcommand;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import org.bukkit.entity.Player;

public class FactionsDesfazer extends Subcommand {
    @Override
    public String getName() {
        return "desfazer";
    }

    @Override
    public String getDescription() {
        return "desfaz a facção";
    }

    @Override
    public String getSyntax() {
        return "/f desfazer";
    }

    @Override
    public int getArgsSize() {
        return 0;
    }

    @Override
    public boolean execute(Player player, String[] args, FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager) {
        try {
            factionManager.disbandFaction(player.getName());
            player.sendMessage("Facção desfeita com sucesso!");
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            return false;
        }
    }
}
