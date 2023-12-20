package xyz.lunatrn;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedList;

public class Domain {
    public Player domainOwner;
    public boolean isDomainActive = false;
    private LinkedList<Player> trappedPlayers;

    private Location originalLocation;

    static PotionEffect[] DOMAIN_OWNER_BUFF = {
            new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999,3),
            new PotionEffect(PotionEffectType.INCREASE_DAMAGE,999999, 3)
    };

    public Domain(Player domainOwner, LinkedList<Player> trappedPlayers){
        this.domainOwner = domainOwner;
        this.trappedPlayers = trappedPlayers;
        if(trappedPlayers.size() == 0){
            this.breakDomain();
        }
        this.isDomainActive = true;
        this.originalLocation = domainOwner.getLocation();
    }
    public void createDomain(){
        // Cancels domain activation If there are no players trapped
        if(!isDomainActive){
            return;
        }
        buffOwner();
        createNewWorld(); // Optimise World Creation Later
        teleportPlayersIntoDomain();
    }

    public void breakDomain(){
        if(!isDomainActive)return;
        isDomainActive = false;
        teleportPlayersOutOfDomain();
    }

    private void buffOwner(){
        for(PotionEffect effect : DOMAIN_OWNER_BUFF){
            this.domainOwner.addPotionEffect(effect);
        }
    }

    private World getDomainWorld(){
        return domainOwner.getServer().getWorld(domainOwner.getUniqueId().toString());
    }

    private void createNewWorld(){
        World newWorld = new  WorldCreator(domainOwner.getUniqueId().toString()).createWorld();
        newWorld.getWorldBorder().setSize(200,200);
        newWorld.getWorldBorder().setCenter(0,0);
    }
    private void teleportPlayersIntoDomain(){
        World domainWorld = domainOwner.getServer().getWorld(domainOwner.getUniqueId().toString());
        Location domainLocation = new Location(domainWorld,0,domainWorld.getHighestBlockYAt(0,0),0);
        domainOwner.teleport(domainLocation);
        for(Player p: trappedPlayers){
            p.teleport(domainLocation);
        }
    }

    private void teleportPlayersOutOfDomain(){
        domainOwner.teleport(originalLocation);
        for(Player p : trappedPlayers){
            p.teleport(originalLocation);
        }
    }
}
