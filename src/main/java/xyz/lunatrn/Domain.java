package xyz.lunatrn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class Domain {
    private Player domainOwner;
    private LinkedList<Player> trappedPlayers = new LinkedList<Player>();
    float domainRange = 2.0f;

    float domainHealth = 10.0f;

    float domainStrength = 2.0f; //Determines Domains Owners Buff

    private Server serv;
    private World domainWorld;

    private boolean hasCooldownPassed(){
        return false;
    }
    private void findNearbyPlayers(){
        for(Player p : domainOwner.getServer().getOnlinePlayers()){
            if(p.getLocation().distance(domainOwner.getLocation()) <= domainRange){
                trappedPlayers.add(p);
            }
        }
    }

    private void teleportPlayers(){
        Location tpLocation = new Location(domainWorld, 0, domainWorld.getHighestBlockYAt(0,0)+ 2,0);
        domainOwner.teleport(tpLocation);
        for(Player p : trappedPlayers){
            p.teleport(tpLocation.add(-1,1,-1));
        }
    }

    private Block getHighestBlock(Location loc){
        return loc.getWorld().getHighestBlockAt(loc);
    }
    private void createDomainShell(){
        getHighestBlock(domainOwner.getLocation()).setType(Material.OBSIDIAN);
    }
    public static void activateDomain(Player domainOwner, Server server){
        if(!doesDomainWorldExist(domainOwner,server.getWorlds())){
            createNewDomainWorld(domainOwner.getUniqueId().toString());
        }
        World domainWorld = getDomainWorld(domainOwner,server);
        Player[] nearbyPlayers = getNearbyPlayers(domainOwner,server);
        Location teleportLocation = createDomainTeleportLocation(domainOwner,domainWorld);
        teleportPlayers(nearbyPlayers,teleportLocation);
        broadcastTeleportEvent(domainOwner,nearbyPlayers);
        buffDomainOwner(domainOwner);
    }
}
