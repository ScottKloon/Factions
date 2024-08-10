package br.com.redeend.factions.inventory;

import br.com.redeend.core.mysql.MySQL;
import br.com.redeend.core.rank.PlayerGroup;
import br.com.redeend.factions.api.ItemAPI;
import br.com.redeend.factions.api.SkullAPI;
import br.com.redeend.factions.api.SkullColors;
import br.com.redeend.factions.manager.FactionManager;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

public class FactionsInventory {

    private final Player player;
    private Inventory factionsInv;
    private final FactionManager factions;
    private final MySQL mysqlCore;
    private Inventory factionsInvCreation;

    public FactionsInventory(FactionManager factions, Player player, MySQL mysqlCore) {
        this.factions = factions;
        this.player = player;
        this.mysqlCore = mysqlCore;
    }

    public void openFactionsInventory() {
        String tag;
        String name;
        try {
            tag = factions.getFactionTag(player.getName());
            name = factions.getNameFactions(player.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String tagFactions = "[" + tag + "] ";
        String title = tagFactions + name;

        factionsInv = Bukkit.createInventory(player, 6 * 9, title);

        addItens();
        player.openInventory(factionsInv);
    }

    public void addItens(){
        String rank = mysqlCore.getPrimaryRank(player.getUniqueId()).getTag();


        SkullAPI.addPlayerHead(factionsInv, 10, player, rank + " " + player.getName(), Arrays.asList(""));
        SkullAPI.addPlayerSkull(factionsInv, 14, "§dRanking de Facções", SkullColors.rosa, Arrays.asList(
                "§7Veja o ranking das facções",
                "§7 suas posições atuais.",
                "§7Você pode ver mais detalhes",
                "§7com o comando '/ranking'."
        ));

        SkullAPI.addPlayerSkull(factionsInv, 15, "§6Ranking de Facções", SkullColors.laranja, Arrays.asList(
                "§7Veja o ranking das facções",
                "§7e suas posições atuais.",
                "§7Você pode ver mais detalhes",
                "§7com o comando '/ranking'."
        ));

        SkullAPI.addPlayerSkull(factionsInv, 16, "§eAjuda", SkullColors.amarelo, Arrays.asList(
                "§7Todas as ações disponíveis neste menu",
                "§7também podem ser realizadas por",
                "§7comando. Utilize o comando '/f ajuda'",
                "§7para ver todos os comandos disponíveis."
        ));

        SkullAPI.addPlayerSkull(factionsInv, 28, "§fMembros", SkullColors.branco, Arrays.asList(
                "§7Mostrar membros da facção.",
                "§7Clique aqui para ver",
                "§7os membros atuais da facção."
        ));
    }

    public void openFactionsInventoryCreation(){
        factionsInvCreation = Bukkit.createInventory(player, 6*9, "Sem facção");
    }
    public void addItensCreation(){
        String rank = mysqlCore.getPrimaryRank(player.getUniqueId()).getTag();

        SkullAPI.addPlayerHead(factionsInv, 10, player, rank + " " + player.getName(), Arrays.asList(""));
        SkullAPI.addPlayerSkull(factionsInv, 14, "§dRanking de Facções", SkullColors.rosa, Arrays.asList(
                "§7Veja o ranking das facções",
                "§7e suas posições atuais.",
                "§7Você pode ver mais detalhes",
                "§7com o comando '/ranking'."
        ));

        SkullAPI.addPlayerSkull(factionsInv, 15, "§6Ranking de Facções", SkullColors.laranja, Arrays.asList(
                "§7Veja o ranking das facções",
                "§7e suas posições atuais.",
                "§7Você pode ver mais detalhes",
                "§7com o comando '/ranking'."
        ));

        SkullAPI.addPlayerSkull(factionsInv, 16, "§eAjuda", SkullColors.amarelo, Arrays.asList(
                "§7Todas as ações disponíveis neste menu",
                "§7também podem ser realizadas por",
                "§7comando. Utilize o comando '/f ajuda'",
                "§7para ver todos os comandos disponíveis."
        ));
        ItemAPI.addItemToInventory(factionsInvCreation, 38, Material.BANNER, "§aCriar facção", "Clique para criar a sua\n facção ou se preferir use\n o comando '/f criar <tag> <nome>'.");
        ItemAPI.addItemToInventory(factionsInvCreation, 39, Material.PAPER, "§7Convites de facções", "Verifique se há convites\n para entrar em alguma\n facção.");
        ItemAPI.addItemToInventory(factionsInvCreation, 40, Material.MAP, "§aMapa de territórios", "Mapa de territórios");
    }
}
