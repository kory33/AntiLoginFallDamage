package com.github.kory33.antiloginfalldamage.core;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.kory33.antiloginfalldamage.data.DataHandler;

public class EventInterceptor implements Listener{
    private DataHandler dHandler;
    private FallDamageExemptor fDamageExemptor;
    
    public EventInterceptor(JavaPlugin plugin, DataHandler dHandler) {
    	this.fDamageExemptor = new FallDamageExemptor(plugin);
        this.dHandler = dHandler;

    	PluginManager pManager = plugin.getServer().getPluginManager();
        pManager.registerEvents(this, plugin);
        
        plugin.getServer().getLogger().info("Registered core interceptors");
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        
        boolean isFlying = player.isFlying();
        if(this.fDamageExemptor.isPlayerInSet(player)){
            isFlying = true;
            this.fDamageExemptor.addPlayer(player);
        }
        
        this.dHandler.writeUUIDWithValue("playerData", player, player.isFlying());

        if(isFlying){
            player.getServer().getLogger().info("Player(name: " + player.getName() +
                    ", UUID: " + player.getUniqueId() + ") will be exempted from next fall damage.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!this.dHandler.existUUIDKey("playerData", player)){
            return;
        }
        
        if((boolean)this.dHandler.readUUID("playerData", player)){
            this.fDamageExemptor.addPlayer(player);
        }
        return;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerFall(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        
        Player player = (Player) event.getEntity();
        if(event.getCause() == DamageCause.FALL && this.fDamageExemptor.isPlayerInSet(player)){
            event.setCancelled(true);
            this.fDamageExemptor.removePlayer(player);
        }
    }
    
    public void stopMonitoring() {
    	this.fDamageExemptor.stop();
    }
}
