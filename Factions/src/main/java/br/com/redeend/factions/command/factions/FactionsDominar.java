package br.com.redeend.factions.command.factions;

import br.com.redeend.factions.command.Subcommand;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.entity.Player;

public class FactionsDominar extends Subcommand {
    @Override
    public String getName() {
        return "dominar";
    }

    @Override
    public String getDescription() {
        return "domina uma zona";
    }

    @Override
    public String getSyntax() {
        return "/f dominar";
    }

    @Override
    public int getArgsSize() {
        return 0;
    }

    @Override
    public boolean execute(Player player, String[] args, FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager) {
        if (factionManager.getPlayerFaction(player.getName()) == null) {
            player.sendMessage("Você precisa estar em uma facção para dominar uma área.");
            return false;
        }

        zoneManager.setZone(player.getLocation().getChunk(), ZoneType.INIMIGA, factionManager.getPlayerFaction(player.getName()));
        player.sendMessage("Você dominou esta área!");
        return true;
    }
}
