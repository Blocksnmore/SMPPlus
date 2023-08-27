package io.github.blocksnmore.smpplus.tweaks;

import io.github.blocksnmore.smpplus.Main;
import io.github.blocksnmore.smpplus.tools.Color;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class PlayerClient implements PluginMessageListener {
    Main main;

    public PlayerClient(Main main) {
        this.main = main;
        main.getServer().getMessenger().registerIncomingPluginChannel(main, "minecraft:brand", this);
    }

    @Override
    public void onPluginMessageReceived(String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals("minecraft:brand")) return;
        String s = new String(message, StandardCharsets.US_ASCII).substring(1);
        String interpreted = s.equalsIgnoreCase("vanilla") ? "Vanilla" : "Modded";

        main.getLogger().info(player.getName() + " joined on " + interpreted + " ("+s+")");

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("smpplus.joininfo")) {
                p.sendMessage(Component.text(Color.applyColor("&b[SMPPlus] " + player.getName() + " joined on " + interpreted + " ("+s+")")));
            }
        }
    }
}
