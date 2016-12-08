package com.github.kory33.antiloginfalldamage.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DataHandler {
    private JavaPlugin plugin;

    public DataHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void writeUUIDWithValue(String path, Player player, Object value) {
        this.getConfig().set(path + "." + player.getUniqueId(), value);
        this.plugin.saveConfig();
        return;
    }
    
    public Object readUUID(String path, Player player) {
        return this.getConfig().get(path + "." + player.getUniqueId());
    }
    
    public boolean existUUIDKey(String path, Player player){
        return this.getConfig().contains(path + "." + player.getUniqueId());
    }
    
    private FileConfiguration getConfig() {
        return this.plugin.getConfig();
    }
}
