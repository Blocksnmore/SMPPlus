package io.github.blocksnmore.smpplus.tweaks;

import io.github.blocksnmore.smpplus.Main;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class MakeXpGreatAgain implements Listener {
    public static int levelsToXp(int levels) {
        if (levels == 0) return 0;
        if (levels <= 16) {
            return (int) (Math.pow(levels, 2) + 6 * levels);
        } else if (levels <= 31) {
            return (int) (2.5 * Math.pow(levels, 2) + -40.5 * levels + 360);
        } else {
            return (int) (4.5 * Math.pow(levels, 2) + -162.5 * levels + 2220);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!Main.config.getBoolean("fixstupidxp")) return;
        Location spawnSpot = e.getEntity().getLocation();
        if (Boolean.TRUE.equals(spawnSpot.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY))) return;
        e.setKeepLevel(true);
        if (Main.config.getBoolean("dropxp")) {
            int levels = e.getEntity().getLevel();
            float xp = e.getEntity().getExp();
            int xpToNextLevel = levelsToXp(levels + 1) - levelsToXp(levels);
            int totalXp = (int) (Math.round(Math.ceil(xpToNextLevel * xp)) + levelsToXp(levels));
            e.setDroppedExp(0);
            ExperienceOrb orb = (ExperienceOrb) spawnSpot.getWorld().spawnEntity(spawnSpot, EntityType.EXPERIENCE_ORB);
            orb.setExperience(totalXp);
            e.getEntity().setExp(0);
            e.getEntity().setLevel(0);
        }
    }
}
