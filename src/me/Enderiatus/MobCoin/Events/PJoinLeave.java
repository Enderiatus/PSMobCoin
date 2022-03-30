package me.Enderiatus.MobCoin.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Enderiatus.MobCoin.Main;

public class PJoinLeave implements Listener{
	
	Main plugin;
	public PJoinLeave(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void joinEvent(PlayerJoinEvent e) {
		plugin.getPlayerMobCoin(e.getPlayer());
	}
	
	@EventHandler
	public void disconnectEvent(PlayerQuitEvent e) {
		plugin.sql.setMobCoin(e.getPlayer().getName());
		plugin.mobCoins.remove(e.getPlayer().getName());
		
	}

}
