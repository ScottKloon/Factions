package br.com.redeend.factions.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Descompactar implements CommandExecutor {
    private final HashMap<Material, Material> decompactingMap = new HashMap<>();

    public Descompactar() {
        decompactingMap.put(Material.IRON_BLOCK, Material.IRON_INGOT);
        decompactingMap.put(Material.GOLD_BLOCK, Material.GOLD_INGOT);
        decompactingMap.put(Material.DIAMOND_BLOCK, Material.DIAMOND);
        decompactingMap.put(Material.EMERALD_BLOCK, Material.EMERALD);
        // Adicione outros blocos que deseja descompactar aqui
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("descompactar")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Inventory inventory = player.getInventory();
                boolean foundBlock = false;

                for (Material block : decompactingMap.keySet()) {
                    Material ore = decompactingMap.get(block);
                    int blockCount = 0;

                    // Conta quantos blocos o jogador tem
                    for (ItemStack item : inventory.getContents()) {
                        if (item != null && item.getType() == block) {
                            blockCount += item.getAmount();
                            inventory.remove(item);
                        }
                    }

                    // Descompacta os blocos em minérios
                    if (blockCount > 0) {
                        int oreCount = blockCount * 9;
                        inventory.addItem(new ItemStack(ore, oreCount));

                        foundBlock = true;
                    }
                }

                if (foundBlock) {
                    player.sendMessage("Todos os blocos foram descompactados!");
                } else {
                    player.sendMessage("Nenhum bloco encontrado no inventário.");
                }
                return true;
            } else {
                sender.sendMessage("Apenas jogadores podem usar esse comando.");
            }
        }
        return false;
    }
}
