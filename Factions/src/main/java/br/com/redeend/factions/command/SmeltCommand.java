package br.com.redeend.factions.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SmeltCommand implements CommandExecutor {
    private final HashMap<Material, Material> smeltingMap = new HashMap<>();

    public SmeltCommand() {
        smeltingMap.put(Material.IRON_ORE, Material.IRON_INGOT);
        smeltingMap.put(Material.GOLD_ORE, Material.GOLD_INGOT);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("derreter")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Inventory inventory = player.getInventory();
                boolean foundOre = false;

                for (int i = 0; i < inventory.getSize(); i++) {
                    ItemStack item = inventory.getItem(i);
                    if (item != null && smeltingMap.containsKey(item.getType())) {
                        Material smeltedMaterial = smeltingMap.get(item.getType());
                        int amount = item.getAmount();
                        inventory.setItem(i, new ItemStack(smeltedMaterial, amount));
                        foundOre = true;
                    }
                }

                if (foundOre) {
                    player.sendMessage("Todos os minérios foram derretidos!");
                } else {
                    player.sendMessage("Nenhum minério encontrado no inventário.");
                }
                return true;
            } else {
                sender.sendMessage("Apenas jogadores podem usar esse comando.");
            }
        }
        return false;
    }
}
