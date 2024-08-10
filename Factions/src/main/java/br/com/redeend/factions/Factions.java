package br.com.redeend.factions;

import br.com.redeend.factions.command.*;
import br.com.redeend.factions.command.PlayerGlobalChat;
import br.com.redeend.factions.inventory.FactionsInventory;
import br.com.redeend.factions.listeners.*;
import br.com.redeend.factions.manager.FactionManager;
import br.com.redeend.factions.mysql.MySQL;
import br.com.redeend.factions.zone.listener.*;
import br.com.redeend.factions.zone.ZoneManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Factions extends JavaPlugin {

    private ZoneManager zoneManager;
    private FactionManager factionManager;
    private MySQL mysql;
    private FactionsInventory factionsInventory;
    private PlayerScore playerScore;
    private br.com.redeend.core.mysql.MySQL mysqlCore;

    @Override
    public void onEnable() {
        openMySQL();
        saveDefaultConfig();

        factionManager = new FactionManager(mysql);
        zoneManager = new ZoneManager();

        new ServerItemClean().runTaskTimer(this, 0, 3600 * 20);

        registerCommand();
        registerListener();


    }

    @Override
    public void onDisable() {
        closeMySQL();
    }

    public void registerCommand() {
        FileConfiguration config = getConfig();
        getCommand("f").setExecutor(new FactionsCommand(factionManager, playerScore, zoneManager, mysqlCore));
        getCommand("zona").setExecutor(new ZoneCommand(zoneManager));
        getCommand("g").setExecutor(new PlayerGlobalChat(factionManager, mysqlCore));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this, mysqlCore));
        getCommand("spawn").setExecutor(new SpawnCommand(config));

        getCommand("bancada").setExecutor(new WorkCommand());
        getCommand("compactar").setExecutor(new CompactCommand());
        getCommand("derreter").setExecutor(new SmeltCommand());
        getCommand("descompactar").setExecutor(new Descompactar());
        getCommand("luz").setExecutor(new LightCommand());
        getCommand("vida").setExecutor(new VidaCommand());
        getCommand("fome").setExecutor(new FomeCommand());
    }

    public void registerListener() {
        FileConfiguration config = getConfig();
        getServer().getPluginManager().registerEvents(new PlayerBlockEvents(), this);
        getServer().getPluginManager().registerEvents(new ZoneMove(zoneManager), this);
        getServer().getPluginManager().registerEvents(new ZonePvP(zoneManager), this);
        getServer().getPluginManager().registerEvents(new ZoneBuild(zoneManager), this);
        getServer().getPluginManager().registerEvents(new ZoneFallDamage(zoneManager), this);
        getServer().getPluginManager().registerEvents(new ZoneBlockBreak(zoneManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLocalChat(factionManager, mysqlCore), this);
        getServer().getPluginManager().registerEvents(new PlayerFactionsTag(mysqlCore, factionManager), this);
        getServer().getPluginManager().registerEvents(new PlayerScore(factionManager, zoneManager, mysqlCore), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinSpawn(config), this);
    }

    public ZoneManager getZoneManager() {
        return zoneManager;
    }

    public void openMySQL() {
        String host = "localhost";
        int port = 3306;
        String database = "redeend";
        String username = "root";
        String password = "";


        mysql = new MySQL(host, port, database, username, password);
        mysqlCore = new br.com.redeend.core.mysql.MySQL(host, port, database, username, password);
        try {
            mysql.connect();
            mysqlCore.connect();
            mysql.createTables();
            getLogger().info("O servidor foi conectado ao banco de dados.");
        } catch (Exception e) {
            getLogger().severe("Erro ao conectar ao MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeMySQL() {
        if (mysql != null) {
            try {
                mysql.disconnect();
                getLogger().info("Desconectado do MySQL com sucesso.");
            } catch (Exception e) {
                getLogger().severe("Erro ao desconectar do MySQL: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
