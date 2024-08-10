package br.com.redeend.factions.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CompactCommand implements CommandExecutor {
    private final HashMap<Material, Material> compactingMap = new HashMap<>();

    public CompactCommand() {
        compactingMap.put(Material.IRON_INGOT, Material.IRON_BLOCK);
        compactingMap.put(Material.GOLD_INGOT, Material.GOLD_BLOCK);
        compactingMap.put(Material.DIAMOND, Material.DIAMOND_BLOCK);
        compactingMap.put(Material.EMERALD, Material.EMERALD_BLOCK);
        // Adicione outros minérios que deseja compactar aqui
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("compactar")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Inventory inventory = player.getInventory();
                boolean foundOre = false;

                for (Material ore : compactingMap.keySet()) {
                    Material block = compactingMap.get(ore);
                    int oreCount = 0;

                    // Conta quantos minérios o jogador tem
                    for (ItemStack item : inventory.getContents()) {
                        if (item != null && item.getType() == ore) {
                            oreCount += item.getAmount();
                            inventory.remove(item);
                        }
                    }

                    // Compacta os minérios em blocos
                    if (oreCount >= 9) {
                        int blockCount = oreCount / 9;
                        int remainingOre = oreCount % 9;

                        inventory.addItem(new ItemStack(block, blockCount));
                        if (remainingOre > 0) {
                            inventory.addItem(new ItemStack(ore, remainingOre));
                        }

                        foundOre = true;
                    } else if (oreCount > 0) {
                        inventory.addItem(new ItemStack(ore, oreCount));
                    }
                }

                if (foundOre) {
                    player.sendMessage("Todos os minérios foram compactados!");
                } else {
                    player.sendMessage("Nenhum minério suficiente para compactar encontrado no inventário.");
                }
                return true;
            } else {
                sender.sendMessage("Apenas jogadores podem usar esse comando.");
            }
        }
        return false;
    }
}
