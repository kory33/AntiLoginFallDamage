package com.github.kory33.antiloginfalldamage.core;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.github.kory33.antiloginfalldamage.data.DataHandler;
import com.github.kory33.updatenotificationplugin.bukkit.github.GithubUpdateNotifyPlugin;

public class AntiLoginFallDamage extends GithubUpdateNotifyPlugin {
    private DataHandler dHandler;
    private ArrayList<Listener> eventInterceptors;
    
    @Override
    public void onEnable(){
        // initialize config.yml
        if(!(new File("config.yml").exists())){
            saveResource("config.yml", false);
        }
        
        this.dHandler = new DataHandler(this);
        
        // initialize event interceptors
        this.eventInterceptors = new ArrayList<Listener>();
        
        this.eventInterceptors.add(new EventInterceptor(this, dHandler));
    }
    
    @Override
    public void onDisable(){
        for (Listener listener : eventInterceptors) {
            HandlerList.unregisterAll(listener);
        }
        
        this.getLogger().info("Unloaded/unregistered MinecartChestFilter successfully.");
    }

	@Override
	public String getGithubRepository() {
		return "Kory33/AntiLoginFallDamage";
	}
}
