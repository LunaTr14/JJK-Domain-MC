package xyz.lunatrn;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.LinkedList;

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

    private void teleportPlayersToDomain(Player domainOwner, LinkedList<Player> trappedPlayers){

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(isHoldingStick(e.getPlayer())){
            new Domain().activateDomain(e.getPlayer());
            // Domain Verify and Create
        }
    }

    private String getPlayerUUID(PlayerJoinEvent e){
        return e.getPlayer().getUniqueId().toString();
    }

    private void createWorld(Server serv,String playerUUID){
        WorldCreator newWorld = new WorldCreator(playerUUID);
        serv.createWorld(newWorld);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        for(World w : e.getPlayer().getServer().getWorlds()){
            if(w.getName().equalsIgnoreCase(getPlayerUUID(e))){
                return;
            }
        }
        createWorld(e.getPlayer().getServer(), getPlayerUUID(e));
    }
}
