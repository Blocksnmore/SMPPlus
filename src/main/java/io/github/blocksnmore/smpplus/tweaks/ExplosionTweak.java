package io.github.blocksnmore.smpplus.tweaks;

import io.github.blocksnmore.smpplus.Main;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionTweak implements Listener {
    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Creeper) {
            if (!Main.config.getBoolean("noexplosions")) return;
            e.setCancelled(true);
        }
    }
}
