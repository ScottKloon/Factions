package br.com.redeend.factions.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SkullAPI {

    public static void addPlayerSkull(Inventory inventory, int slot, String name, String textureURL, List<String> lore) {
        ItemStack skull = createCustomSkull(name, textureURL, lore);
        inventory.setItem(slot, skull);
    }

    public static ItemStack createCustomSkull(String name, String textureURL, List<String> lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setDisplayName(name);

        // Encode the texture URL in Base64
        String encodedTexture = Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + textureURL + "\"}}}").getBytes());

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", encodedTexture));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
            Bukkit.getLogger().info("Set custom skull for " + name + " with texture " + textureURL);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            Bukkit.getLogger().severe("Failed to set custom skull for " + name);
        }

        // Add the lore in light gray
        skullMeta.setLore(lore);

        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static void addPlayerHead(Inventory inventory, int slot, OfflinePlayer player, String name, List<String> lore) {
        ItemStack skull = createPlayerHead(player, name, lore);
        inventory.setItem(slot, skull);
    }

    public static ItemStack createPlayerHead(OfflinePlayer player, String name, List<String> lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setDisplayName(name);

        // Set the skull owner to the player
        skullMeta.setOwner(player.getName());

        // Add the lore in light gray
        skullMeta.setLore(lore);

        skull.setItemMeta(skullMeta);
        return skull;
    }
}
