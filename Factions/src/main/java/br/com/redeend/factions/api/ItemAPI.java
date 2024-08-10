package br.com.redeend.factions.api;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ItemAPI {

    public static void addItemToInventory(Inventory inventory, int slot, Material material, String name, String lore) {
        addItemToInventory(inventory, slot, material, name, lore, (short) 0);
    }

    public static void addItemToInventory(Inventory inventory, int slot, Material material, String name, String lore, int data) {
        ItemStack item = new ItemStack(material, 1, (short) data);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(Collections.singletonList(ChatColor.GRAY + lore));
            item.setItemMeta(meta);
        }
        inventory.setItem(slot, item);
    }
}
