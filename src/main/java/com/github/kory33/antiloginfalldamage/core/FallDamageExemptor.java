package com.github.kory33.antiloginfalldamage.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class that validates players whose fall damage should be exempted
 * @author kory
 *
 */
public class FallDamageExemptor implements Runnable{
	private final Set<Player> players;
	private boolean continueMonitor;
	private JavaPlugin plugin;
	
	public FallDamageExemptor(JavaPlugin plugin) {
		this.plugin = plugin;
		this.players = new HashSet<>();
		this.continueMonitor = true;
		
		this.run();
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public boolean isPlayerInSet(Player player) {
		return this.players.contains(player);
	}
	
	@Override
	public void run() {
		if(!this.continueMonitor) {
			return;
		}
		
		Iterator<Player> playerIter = this.players.iterator();
		while (playerIter.hasNext()) {
			Player player = playerIter.next();
			if(player.getVelocity().getY() >= 0.0) {
				playerIter.remove();
			}
		}
		
		this.plugin.getServer().getScheduler().runTaskLater(this.plugin, this, 1);
	}
	
	public void stop() {
		this.continueMonitor = false;
	}
}
