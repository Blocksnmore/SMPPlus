package io.github.blocksnmore.smpplus.tweaks;

import io.github.blocksnmore.smpplus.Main;
import io.github.blocksnmore.smpplus.tools.Color;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.md_5.bungee.chat.TextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class ColorTweaks implements Listener {
    @EventHandler
    public void onSignPlace(SignChangeEvent e) {
        if (!Main.config.getBoolean("colortweak")) return;
        for(int i = 0; i < e.lines().size(); i++) {
            e.setLine(i, Color.applyColor(e.getLine(i)));
        }
    }

    @EventHandler
    public void onAnvilGUI(InventoryClickEvent e) {
        if (!Main.config.getBoolean("colortweak")) return;
        if (e.getInventory().getType() == InventoryType.ANVIL && e.getWhoClicked() instanceof Player) {
            if (e.getRawSlot() == 2) {
                ItemStack outputItem = e.getInventory().getItem(2);
                assert outputItem != null;
                ItemMeta outputItemMeta = outputItem.getItemMeta();

                outputItemMeta.displayName(Component.text(Color.applyColor(outputItemMeta.getDisplayName())));
                outputItem.setItemMeta(outputItemMeta);

                e.setCurrentItem(outputItem);
            }
        }
    }

    @EventHandler
    // Yes, this is no longer supported in 1.17
    // But the docs are actual shit for adventure
    public void onChat(AsyncPlayerChatEvent e) {
        if (!Main.config.getBoolean("betterchat.enabled")) return;
        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacy('&');
        String playerMessage = Color.applyColor(e.getMessage());
        String format = Color.applyColor(Objects.requireNonNull(Main.config.getString("betterchat.format")));
        format = format.replaceAll("%", "%%");
        format = format.replaceAll("%%player%%", "%s");
        format = format.replaceAll("%%text%%", "%s");
        e.setMessage(playerMessage);
        e.setFormat(format);
    }
}
