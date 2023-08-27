package io.github.blocksnmore.smpplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import io.github.blocksnmore.smpplus.tools.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@CommandAlias("openinv|openinventory|invsee")
public class Invsee extends BaseCommand {
    @Default
    public void onDefault(Player player, OnlinePlayer targetPlayer){
        if (!player.hasPermission("smpplus.invsee")) {
            player.sendMessage(Color.applyColor("&cYou do not have permission to do this!"));
        } else {
            if (targetPlayer == null) {
                player.sendMessage(Color.applyColor("&cUnknown player! Make sure the user is online"));
            } else {
                player.sendMessage(Color.applyColor("&bOpening inventory!"));
                player.openInventory((Inventory) targetPlayer.getPlayer().getInventory());
            }
        }
    }
}
