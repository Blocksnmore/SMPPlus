package io.github.blocksnmore.smpplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import io.github.blocksnmore.smpplus.tools.Color;
import org.bukkit.entity.Player;

@CommandAlias("smpplus|smpp")
public class Smpplus extends BaseCommand {
    @Default
    public void onDefault(Player player) {
        player.sendMessage(
                String.join("\n", new String[]{
                        Color.applyColor("&b&l------------------"),
                        Color.applyColor("&b&lSMPPlus"),
                        Color.applyColor("&b"),
                        Color.applyColor("&bCreated by &lBlocks_n_more"),
                        Color.applyColor("&b&l------------------")
                })
        );
    }
}
