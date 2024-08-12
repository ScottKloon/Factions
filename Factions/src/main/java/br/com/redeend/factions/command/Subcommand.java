package br.com.redeend.factions.command;

import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import org.bukkit.entity.Player;

public abstract class Subcommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract int getArgsSize();
    public abstract boolean execute(Player player, String[] args, FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager);
}
