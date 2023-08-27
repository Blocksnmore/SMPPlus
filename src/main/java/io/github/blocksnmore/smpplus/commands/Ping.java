package io.github.blocksnmore.smpplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import io.github.blocksnmore.smpplus.tools.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("playerping|pingpong|pong|ping")
public class Ping extends BaseCommand {
    @Default
    public void onDefault(Player player, @Optional OnlinePlayer target){
        if (target == null) {
            StringBuilder message = new StringBuilder(Color.applyColor("&b&l------------------") + "\n");
            for (Player p : Bukkit.getOnlinePlayers()) {
                int ping = p.getPing();
                message.append(Color.applyColor("&b" + p.getName() + ": " + (ping < 1 ? "Unknown" : String.valueOf(ping)))).append("\n");
            }
            player.sendMessage(Color.applyColor(message + "&b&l------------------"));
        } else {
            int ping = target.getPlayer().getPing();
            player.sendMessage(
                    Color.applyColor("&b&l------------------\n&b") +
                    target.getPlayer().getName()+"'s ping is: "+ (ping < 1 ? "Unknown" : String.valueOf(ping)) +
                    Color.applyColor("\n&b&l------------------")
            );
        }
    }
}
