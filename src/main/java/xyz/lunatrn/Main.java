package xyz.lunatrn;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            if (label.equalsIgnoreCase("jjk")) {
                if (args[0].equalsIgnoreCase("stop")) {
                    this.getServer().getPluginManager().disablePlugin(this);
                }
            }
        }
        return true;
    }
}
