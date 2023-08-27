package io.github.blocksnmore.smpplus.tweaks;

import io.github.blocksnmore.smpplus.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EZCrops implements Listener {
    @EventHandler
    public void onCropClick(PlayerInteractEvent e) {
        if (!Main.config.getBoolean("ezcrops")) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block targetBlock = e.getClickedBlock();
        if (targetBlock == null) return;
        World world = targetBlock.getWorld();
        Material cropType = targetBlock.getType();
        Location location = targetBlock.getLocation();
        Random random = new Random();
        ArrayList<Material> cropTypes = new ArrayList<>(Arrays.asList(
                Material.CARROTS,
                Material.POTATOES,
                Material.BEETROOTS,
                Material.WHEAT,
                Material.NETHER_WART
        ));

        if (cropTypes.contains(cropType)) {
            Ageable crop = (Ageable) targetBlock.getBlockData();
            int ageToHarvest = cropType == Material.BEETROOTS || cropType == Material.NETHER_WART ? 3 : 7;
            if (crop.getAge() == ageToHarvest) {
                if (cropType == Material.CARROTS || cropType == Material.POTATOES || cropType == Material.NETHER_WART) {
                    int amountToDrop = 1;
                    for (int i = 0; i < 3; i++) {
                        if ((random.nextInt(100) + 1) <= 57) {
                            amountToDrop++;
                        }
                    }

                    ItemStack cropMainDrop = new ItemStack(cropType == Material.NETHER_WART ? Material.NETHER_WART : cropType == Material.POTATOES ? Material.POTATO : Material.CARROT);
                    cropMainDrop.setAmount(amountToDrop);

                    world.dropItemNaturally(location, cropMainDrop);

                    if (cropType == Material.POTATOES) {
                        if (random.nextInt(100) <= 1) {
                            world.dropItemNaturally(location, new ItemStack(Material.POISONOUS_POTATO));
                        }
                    }
                }

                if (cropType == Material.WHEAT || cropType == Material.BEETROOTS) {
                    world.dropItemNaturally(location, new ItemStack(cropType == Material.WHEAT ? Material.WHEAT : Material.BEETROOT));
                    int seedsToDrop = 0;

                    for (int i = 0; i < (cropType == Material.WHEAT ? 2 : 3); i++) {
                        if ((random.nextInt(100) + 1) <= 57) {
                            seedsToDrop++;
                        }
                    }

                    ItemStack cropSeedDrops = new ItemStack(cropType == Material.WHEAT ? Material.WHEAT_SEEDS : Material.BEETROOT_SEEDS);
                    cropSeedDrops.setAmount(seedsToDrop);
                    world.dropItemNaturally(location, cropSeedDrops);
                }

                crop.setAge(0);
                targetBlock.setBlockData(crop);
            }
        }
    }
}
