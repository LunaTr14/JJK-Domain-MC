package xyz.lunatrn;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    Logger logger = this.getLogger();
    @Override
    public void onEnable() {
        this.logger.log(Level.INFO,"JJK-Domains Enabled");
    }

    @Override
    public void onDisable() {
        this.logger.log(Level.INFO,"JJK-Domains Disabled");
    }
}
