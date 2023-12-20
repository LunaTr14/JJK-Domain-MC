package xyz.lunatrn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedList;

public class JJKEventHandler implements Listener {

    private float DOMAIN_RANGE = 5.0f;

    private long DOMAIN_LENGTH_MS = 200000;

    private long NEXT_DOMAIN_DELAY_MS = 300000;

    HashMap<Player,Domain> activeDomains = new HashMap<>();
    private Main plugin;

    private void registerPlugin(Main plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    public JJKEventHandler(Main plugin){
        this.plugin = plugin;
        registerPlugin(plugin);
    }

    private boolean isHoldingStick(Player p){
        return p.getInventory().getItemInMainHand().getType() == Material.STICK;
    }

    private LinkedList<Player> getNearbyPlayers(Location centerLocation){
        LinkedList nearbyPlayers = new LinkedList<Player>();
        for(Player p : plugin.getServer().getOnlinePlayers()){
            if(p.getLocation().distance(centerLocation) >= DOMAIN_RANGE){
                nearbyPlayers.add(p);
            }
        }
        return nearbyPlayers;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(isHoldingStick(e.getPlayer())){

            // Cancels Domain if delay is active
            if(activeDomains.containsKey(e.getPlayer())){
                return;
            }

            Player domainOwner = e.getPlayer();
            Location domainOwnerLocation = domainOwner.getLocation();

            Domain playerDomain = new Domain(domainOwner,getNearbyPlayers(domainOwnerLocation));


            if(playerDomain.isDomainActive){
                playerDomain.createDomain();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        activeDomains.get(domainOwner).breakDomain();
                        activeDomains.remove(domainOwner);
                    }
                }.runTaskLaterAsynchronously(plugin,DOMAIN_LENGTH_MS * 20);
                plugin.getServer().broadcastMessage(domainOwner.getDisplayName() + " has activated a domain");
                plugin.getServer().broadcastMessage(domainOwner.getDisplayName() + " domain will end in " + (DOMAIN_LENGTH_MS / 1000) + " Seconds");
            }
        }
    }


}
