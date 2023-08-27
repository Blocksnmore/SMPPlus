package io.github.blocksnmore.smpplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import io.github.blocksnmore.smpplus.Main;
import io.github.blocksnmore.smpplus.tools.Color;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandAlias("iname|itemname|renameitem|rename")
public class Rename extends BaseCommand {
    @Default
    public void onDefault(Player player, String name) {
        if (player.getLevel() < 1 && Main.config.getBoolean("requirexp")){
            player.sendMessage(Color.applyColor("&cYou need at least 1 XP Level to do this!"));
        } else {
            ItemStack playerItem = player.getInventory().getItemInMainHand();
            if (playerItem.getType() == Material.AIR) {
                player.sendMessage(Color.applyColor("&cPlease hold an item!"));
            } else {
                ItemMeta itemMeta = playerItem.getItemMeta();
                itemMeta.displayName(Component.text(Color.applyColor(name)));
                playerItem.setItemMeta(itemMeta);
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                player.getInventory().setItemInMainHand(playerItem);
                player.sendMessage(Color.applyColor("&bRenamed item!"));
                if (Main.config.getBoolean("requirexp")) {
                    player.setLevel(player.getLevel() - 1);
                }
            }
        }
    }
}
