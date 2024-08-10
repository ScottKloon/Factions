package br.com.redeend.factions.api;

import br.com.redeend.core.rank.GroupPrefix;
import br.com.redeend.factions.manager.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public final class TagsAPI {

    private static FactionManager factionManager;

    public static final Map<Player, GroupPrefix> TAGS = new HashMap<>();

    public static void setTag(Player player, String prefix) {
        setTag(player, prefix, "");
    }

    public static void setTag(Player player, String prefix, String suffix) {

        setTag(player, new GroupPrefix(prefix, suffix));
    }

    public static void setTag(Player player, GroupPrefix tag) {
        TAGS.put(player, tag);
    }

    public static GroupPrefix getTag(Player player) {
        GroupPrefix tag = null;
        if ((tag = TAGS.get(player)) == null) {
            tag = new GroupPrefix("", "");
            setTag(player, tag);

        }
        return tag;
    }

    private static boolean active = true;

    public static void update() {
        if (isActive()) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getScoreboard() == null) {
                    p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                } else {
                    update(p.getScoreboard());
                }
            }
            update(Bukkit.getScoreboardManager().getMainScoreboard());
        }

    }

    public static void update(Scoreboard scoreboard) {
        if (isActive()) {
            for (Map.Entry<Player, GroupPrefix> map : TAGS.entrySet()) {
                Player p = map.getKey();
                GroupPrefix tag = map.getValue();

                String factionTag = "";
                try {
                    factionTag = factionManager.getFactionTag(p.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Team team = scoreboard.getTeam(p.getName());
                if (team == null) {
                    team = scoreboard.registerNewTeam(p.getName());
                }
                if (!team.hasPlayer(p)) {
                    team.addPlayer(p);
                }

                // Define o prefixo e sufixo com base na facção
                team.setPrefix(tag.getPrefix());
                team.setSuffix(factionTag != null ? factionTag : tag.getSuffix());
            }
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean active) {
        TagsAPI.active = active;
    }

    static {
        Plugin plugin = Bukkit.getPluginManager().getPlugins()[0];
        new BukkitRunnable() {

            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(plugin, 20, 20);
    }
    public static void setFactionManager(FactionManager manager) {
        factionManager = manager;
    } static {
        Plugin plugin = Bukkit.getPluginManager().getPlugins()[0];
        new BukkitRunnable() {

            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(plugin, 20, 20);
    }

}
