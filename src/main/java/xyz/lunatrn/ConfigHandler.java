package xyz.lunatrn;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class ConfigHandler {

    public static String[] DEFAULT_OPTIONS = {"isTrapped","nextAvailableDomainMillis"};

    private final FileConfiguration serverConfig;
    private final Main plugin;
    public ConfigHandler(Main plugin) {
        this.serverConfig = plugin.getConfig();
        this.plugin = plugin;
    }

    public void createIfEmpty(UUID playerUUID){
        String stringUUID  = playerUUID.toString();
        if(serverConfig.contains(stringUUID)){
            return;
        }
        serverConfig.set(stringUUID+".isTrapped",false);
        serverConfig.set(stringUUID+".nextAvailableDomainMillis",0.0);
        plugin.saveConfig();
    }

    public boolean doesEntryExist(UUID playerUUID){
        return serverConfig.contains("player."+playerUUID.toString());
    }

    public void updateContent(String key, Object value){
        serverConfig.set(key,value);
        plugin.saveConfig();
    }

    public void updatePlayerOption(UUID playerUUID, String optionName, Object value){
        updateContent(playerUUID+"."+optionName,value);
    }

    public Object getContent(String key){
        return serverConfig.get(key);
    }

    public Object getContentFromUUID(UUID playerUUID) {
        return getContent(playerUUID.toString());
    }

}
