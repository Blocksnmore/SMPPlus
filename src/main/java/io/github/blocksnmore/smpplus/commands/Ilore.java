package io.github.blocksnmore.smpplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import io.github.blocksnmore.smpplus.Main;
import io.github.blocksnmore.smpplus.tools.Color;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("itemlore|lore|setlore|ilore")
public class Ilore extends BaseCommand {
    @CatchUnknown
    public void onInvalid(Player p) {
        p.sendMessage(Color.applyColor("&cInvalid command use! Use /ilore help for command use!"));
    }

    @Subcommand("help")
    @Default
    public void onDefault(Player p) {
        p.sendMessage(
                String.join("\n", new String[]{
                        Color.applyColor("&b&l------------------"),
                        Color.applyColor("&b> /ilore help - Show help"),
                        Color.applyColor("&b> /ilore add - Add line"),
                        Color.applyColor("&b> /ilore insert - Insert line at position"),
                        Color.applyColor("&b> /ilore remove - Remove line"),
                        Color.applyColor("&b> /ilore clear - Clear lore"),
                        Color.applyColor("&b&l------------------")
                })
        );
    }

    @Subcommand("clear|clearlore|removelore|resetlore")
    @CommandAlias("clearlore|removelore|resetlore")
    public void clearLore(Player p) {
        ItemStack playerItem = p.getInventory().getItemInMainHand();
        if (playerItem.getType() == Material.AIR) {
            p.sendMessage(Color.applyColor("&cPlease hold an item!"));
        } else {
            ItemMeta itemMeta = playerItem.getItemMeta();
            itemMeta.lore(new ArrayList<Component>());
            playerItem.setItemMeta(itemMeta);

            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.getInventory().setItemInMainHand(playerItem);

            p.sendMessage(Color.applyColor("&bCleared lore!"));
        }
    }

    @Subcommand("remove|removeline")
    @CommandAlias("removeline")
    public void removeLine(Player p, String line) {
        if (p.getLevel() < 1 && Main.config.getBoolean("requirexp")) {
            p.sendMessage(Color.applyColor("&cYou need at least 1 xp level to do this!"));
        } else {
            ItemStack playerItem = p.getInventory().getItemInMainHand();
            if (playerItem.getType() == Material.AIR) {
                p.sendMessage(Color.applyColor("&cPlease hold an item!"));
            } else {
                try {
                    Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    p.sendMessage(Color.applyColor("&cPlease provide a valid line!"));
                    return;
                }
                ItemMeta itemMeta = playerItem.getItemMeta();
                List<Component> oldlore = itemMeta.lore();
                if (oldlore == null) {
                    oldlore = new ArrayList<Component>();
                }
                int index = Integer.parseInt(line) -1;
                if (oldlore.get(index) != null && oldlore.size() +1 >= index) {
                    oldlore.remove(index);
                    itemMeta.lore(oldlore);
                    playerItem.setItemMeta(itemMeta);

                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    p.getInventory().setItemInMainHand(playerItem);

                    p.sendMessage(Color.applyColor("&bRemoved line!"));

                    if (Main.config.getBoolean("requirexp")) {
                        p.setLevel(p.getLevel() - 1);
                    }
                } else {
                    p.sendMessage(Color.applyColor("&bInvalid Line number!"));
                }
            }
        }
    }

    @Subcommand("add|addline|addlore")
    @CommandAlias("addlore|addline")
    public void addLine(Player p, String line) {
        if (p.getLevel() < 1 && Main.config.getBoolean("requirexp")) {
            p.sendMessage(Color.applyColor("&cYou need at least 1 xp level to do this!"));
        } else {
            ItemStack playerItem = p.getInventory().getItemInMainHand();
            if (playerItem.getType() == Material.AIR) {
                p.sendMessage(Color.applyColor("&cPlease hold an item!"));
            } else {
                ItemMeta itemMeta = playerItem.getItemMeta();

                List<Component> oldlore = itemMeta.lore();
                if (oldlore == null) {
                    oldlore = new ArrayList<Component>();
                }

                oldlore.add(Component.text(Color.applyColor(line)));
                itemMeta.lore(oldlore);
                playerItem.setItemMeta(itemMeta);

                p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                p.getInventory().setItemInMainHand(playerItem);

                p.sendMessage(Color.applyColor("&bAdded lore!"));

                if (Main.config.getBoolean("requirexp")) {
                    p.setLevel(p.getLevel() - 1);
                }
            }
        }
    }

    @Subcommand("insert|insertlore|insertlore")
    @CommandAlias("insertline|insertlore")
    public void insertLore(Player p, String line, String text) {
        try {
            Integer.parseInt(line);
        } catch (NumberFormatException e) {
            p.sendMessage(Color.applyColor("&cPlease provide a valid line number!"));
            return;
        }
        if (p.getLevel() < 1 && Main.config.getBoolean("requirexp")) {
            p.sendMessage(Color.applyColor("&cYou need at least 1 xp level to do this!"));
        } else {
            ItemStack playerItem = p.getInventory().getItemInMainHand();
            if (playerItem.getType() == Material.AIR) {
                p.sendMessage(Color.applyColor("&cPlease hold an item!"));
            } else {
                ItemMeta itemMeta = playerItem.getItemMeta();

                List<Component> oldlore = itemMeta.lore();
                if (oldlore == null) {
                    oldlore = new ArrayList<Component>();
                }

                int index = Integer.parseInt(line) -1;

                oldlore.add(index, Component.text(Color.applyColor(text)));
                itemMeta.lore(oldlore);
                playerItem.setItemMeta(itemMeta);

                p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                p.getInventory().setItemInMainHand(playerItem);

                p.sendMessage(Color.applyColor("&bAdded lore!"));

                if (Main.config.getBoolean("requirexp")) {
                    p.setLevel(p.getLevel() - 1);
                }
            }
        }
    }
}
