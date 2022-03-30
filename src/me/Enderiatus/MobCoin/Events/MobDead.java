package me.Enderiatus.MobCoin.Events;

import java.util.Random;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import me.Enderiatus.MobCoin.Main;

public class MobDead implements Listener{
	
	Main plugin;
	public MobDead(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void mobKill(EntityDeathEvent e) {
		if(e.getEntity().getType() == EntityType.PLAYER) 
			return;
		if(!(e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) 
			return;
		if(!(e.getEntity().getKiller() instanceof Player)) 
			return;
		if(!(e.getEntity().getType() == EntityType.ZOMBIE || e.getEntity().getType() == EntityType.SKELETON ||
			e.getEntity().getType() == EntityType.BLAZE || e.getEntity().getType() == EntityType.CREEPER ||
			e.getEntity().getType() == EntityType.ENDERMAN || e.getEntity().getType() == EntityType.WITHER)) return;
		int randomC = new Random().nextInt(100);
		Player p = e.getEntity().getKiller();
		if(e.getEntity().getType() == EntityType.ZOMBIE || e.getEntity().getType() == EntityType.SKELETON 
				|| e.getEntity().getType() == EntityType.BLAZE) {
			if(randomC <= 5) {
				int givenPoint = new Random().nextInt(3)+1;
				plugin.mobCoins.put(p.getName(), plugin.mobCoins.get(p.getName())+givenPoint);
				p.sendMessage("§e§l[!] §eBir yaratýk öldürdüðünüz için §6"+givenPoint+" §emob puaný kazandýnýz.");
			}
		}else if(e.getEntity().getType() == EntityType.ENDERMAN || e.getEntity().getType() == EntityType.CREEPER) {
			if(randomC <= 8) {
				int givenPoint = new Random().nextInt(3)+1;
				plugin.mobCoins.put(p.getName(), plugin.mobCoins.get(p.getName())+givenPoint);
				p.sendMessage("§e§l[!] §eBir yaratýk öldürdüðünüz için §6"+givenPoint+" §emob puaný kazandýnýz.");
			}
		}else {
			if(randomC <= 50) {
				int givenPoint = new Random().nextInt(3)+1;
				plugin.mobCoins.put(p.getName(), plugin.mobCoins.get(p.getName())+givenPoint);
				p.sendMessage("§e§l[!] §eBir yaratýk öldürdüðünüz için §6"+givenPoint+" §emob puaný kazandýnýz.");
			}
		}
	}

}
