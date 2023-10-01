package xyz.lunatrn;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class JJKEventHandler implements Listener {

    private void registerPlugin(Main plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    public JJKEventHandler(Main plugin){
        registerPlugin(plugin);
    }

    private boolean isHoldingStick(Player p){
        return p.getInventory().getItemInMainHand().getType() == Material.STICK;
    }

    @EventHandler
    public boolean onPlayerInteract(PlayerInteractEvent e){
        if(isHoldingStick((Player) e)){
            // Domain Verify and Create
        }
        return true;
    }


}
