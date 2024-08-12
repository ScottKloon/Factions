package br.com.redeend.factions.command;

import br.com.redeend.core.mysql.MySQL;
import br.com.redeend.factions.command.factions.*;
import br.com.redeend.factions.inventory.FactionsInventory;
import br.com.redeend.factions.listeners.PlayerScore;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.zone.ZoneManager;
import br.com.redeend.factions.zone.ZoneType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionsCommand implements CommandExecutor {

    private final FactionManager factionManager;
    private final PlayerScore playerScore;
    private final ZoneManager zoneManager;
    private ArrayList<Subcommand> subcommands = new ArrayList<>();

    private final MySQL mysqlCore;
    public FactionsCommand(FactionManager factionManager, PlayerScore playerScore, ZoneManager zoneManager, MySQL mysqlCore) {
        this.factionManager = factionManager;
        this.playerScore = playerScore;
        this.zoneManager = zoneManager;
        this.mysqlCore = mysqlCore;
        addSubcommands();
    }
    private void addSubcommands(){
        this.subcommands.add(new FactionsConvidar());
        this.subcommands.add(new FactionsCriar());
        this.subcommands.add(new FactionsDominar());
        this.subcommands.add(new FactionsEntrar());
        this.subcommands.add(new FactionsSair());
        this.subcommands.add(new FactionsDesfazer());
        this.subcommands.add(new FactionsRemover());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando.");
            return false;
        }

        Player player = (Player) sender;

        if (factionManager == null) {
            player.sendMessage("Gerenciador de facções não está disponível.");
            return false;
        }

        if (args.length == 0) {
            openFactionsInventory(player);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        if(subCommand.equals("help")){
            for(Subcommand cmd : this.subcommands){
                player.sendMessage(cmd.getSyntax() + " - " + cmd.getDescription());
            }
            return true;
        }


        for(Subcommand cmd : this.subcommands){
            if(cmd.getName().equals(subCommand)){
                if(args.length-1 != cmd.getArgsSize()){
                    player.sendMessage(ChatColor.RED + "usa-se " + cmd.getSyntax());
                    return false;
                }
                return cmd.execute(player,args,factionManager,playerScore,zoneManager);
            }
        }


        return true;
    }

    private void openFactionsInventory(Player player) {
        FactionsInventory factionsInventory = new FactionsInventory(factionManager, player, mysqlCore);
        factionsInventory.openFactionsInventory();
    }
}
