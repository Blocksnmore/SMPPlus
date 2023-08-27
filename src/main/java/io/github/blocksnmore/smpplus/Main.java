package io.github.blocksnmore.smpplus;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.blocksnmore.smpplus.commands.*;
import io.github.blocksnmore.smpplus.tweaks.*;

public class Main extends JavaPlugin {
    public static FileConfiguration config;
    public static PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        getLogger().info("Starting SMPPlus!");
        saveDefaultConfig();
        Main.config = getConfig();
        Main.commandManager = new PaperCommandManager(this);
        this.loadEventsAndCommands();
        getLogger().info("Started SMPPlus!");
    }

    private void loadEventsAndCommands(){
        Main.commandManager.registerCommand(new Smpplus());
        Main.commandManager.registerCommand(new Invsee());
        Main.commandManager.registerCommand(new Ping());
        Main.commandManager.registerCommand(new Rename());
        Main.commandManager.registerCommand(new Ilore());

        getServer().getPluginManager().registerEvents(new ColorTweaks(), this);
        getServer().getPluginManager().registerEvents(new ExplosionTweak(), this);
        getServer().getPluginManager().registerEvents(new MakeXpGreatAgain(), this);
        getServer().getPluginManager().registerEvents(new EZCrops(), this);

        new PlayerClient(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling SMPPlus!");
    }
}
