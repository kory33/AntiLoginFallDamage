package com.github.kory33.antiloginfalldamage.core;

import java.io.File;

import org.bukkit.event.HandlerList;

import com.github.kory33.antiloginfalldamage.data.DataHandler;
import com.github.kory33.updatenotificationplugin.bukkit.github.GithubUpdateNotifyPlugin;

public class AntiLoginFallDamage extends GithubUpdateNotifyPlugin {
    private DataHandler dHandler;
    private EventInterceptor eInterceptor;
    
    @Override
    public void onEnable(){
        // initialize config.yml
        if(!(new File("config.yml").exists())){
            saveResource("config.yml", false);
        }
        
        this.dHandler = new DataHandler(this);
        
        // initialize event interceptors
        this.eInterceptor = new EventInterceptor(this, this.dHandler);
    }
    
    @Override
    public void onDisable(){
    	this.eInterceptor.stopMonitoring();
    	HandlerList.unregisterAll(this.eInterceptor);
        
        this.getLogger().info("Unloaded/unregistered MinecartChestFilter successfully.");
    }

	@Override
	public String getGithubRepository() {
		return "Kory33/AntiLoginFallDamage";
	}
}
