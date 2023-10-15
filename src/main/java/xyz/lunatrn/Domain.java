package xyz.lunatrn;

import org.bukkit.entity.Player;

import java.util.LinkedList;

public class Domain {




        return false;
    }
            }
        }
    }

        }
    }

    }
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
