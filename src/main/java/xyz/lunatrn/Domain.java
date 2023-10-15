package xyz.lunatrn;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedList;
import java.util.List;

public class Domain {

    static int DOMAIN_BREAK_TIME_TICKS = 10 * 20;

    int domainHealth = 10;

    private static int DOMAIN_EFFECT_RANGE = 5;

    static PotionEffect[] DOMAIN_OWNER_BUFF = {
            new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,DOMAIN_BREAK_TIME_TICKS,3),
            new PotionEffect(PotionEffectType.INCREASE_DAMAGE,DOMAIN_BREAK_TIME_TICKS, 3)
    };


    private static boolean doesDomainWorldExist(Player p, List<World> worldNames){
        for(World world : worldNames){
            if(world.getName().equalsIgnoreCase(p.getUniqueId().toString())) return true;
        }
        return false;
    }

    private static void createNewDomainWorld(String domainName){
        new WorldCreator(domainName).createWorld();
    }
    
    private static World getDomainWorld(Player domainOwner, Server server){
        return server.getWorld(domainOwner.getUniqueId().toString());
    }
    
    private static Player[] getNearbyPlayers(Player domainOwner, Server server){
        Location domainOwnerLocation = domainOwner.getLocation();
        Player[] nearbyPlayers = {};
        for(Player onlinePlayer : server.getOnlinePlayers()){
            if(domainOwnerLocation.distance(onlinePlayer.getLocation()) > DOMAIN_EFFECT_RANGE){
                nearbyPlayers[nearbyPlayers.length] = onlinePlayer;
            }
        }
        return nearbyPlayers;
    }

    private static Location createDomainTeleportLocation(Player domainOwner, World domainWorld) {
        Location domainLocation = domainOwner.getLocation();
        domainLocation.setWorld(domainWorld);
        domainLocation.setY(domainWorld.getHighestBlockYAt(domainLocation));
        return domainLocation;
    }

    private static void teleportPlayers(Player[] playersToTeleport, Location newLocation){
        for(Player playerToTeleport : playersToTeleport){
            playerToTeleport.teleport(newLocation);
        }
    }

    private static void broadcastTeleportEvent(Player domainOwner,Player[] players){
        for(Player player : players){
            player.sendMessage(domainOwner.getName() + " has activated their domain");
        }
    }
    private static void buffDomainOwner(Player domainOwner) {
        for(PotionEffect effect : DOMAIN_OWNER_BUFF)
            domainOwner.addPotionEffect(effect);
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
