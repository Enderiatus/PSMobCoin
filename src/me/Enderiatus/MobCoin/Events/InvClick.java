package me.Enderiatus.MobCoin.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import me.Enderiatus.MobCoin.Main;

public class InvClick implements Listener {
	
	Main plugin;
	public InvClick(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void invClickEvent(InventoryClickEvent e) {
		if(e.getSlotType() == SlotType.OUTSIDE) 
			return;
		if(!(e.getClickedInventory().getName().equalsIgnoreCase("§6§lMob Puan Marketi"))) 
			return;
		e.setCancelled(true);
		if(!plugin.coinItems.containsKey(e.getSlot())) 
			return;
		if(!(plugin.mobCoins.get(e.getWhoClicked().getName()) >= plugin.coinItems.get(e.getSlot()).getPrice())) {
			e.getWhoClicked().sendMessage("§c§l[!] §cYeterli mob puanýnýz bulunmamaktadýr.");
			return;
		}
		if(e.getWhoClicked().getInventory().firstEmpty() == -1) {
			e.getWhoClicked().sendMessage("§c§l[!] §cEnvanteriniz dolu olduðu için iþlem yapýlamýyor.");
			return;
		}
		plugin.mobCoins.put(e.getWhoClicked().getName(), plugin.mobCoins.
				get(e.getWhoClicked().getName())-plugin.coinItems.get(e.getSlot()).getPrice());
		for(String exC : plugin.coinItems.get(e.getSlot()).getCmds()) {
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), exC.replaceAll("%player%", e.getWhoClicked().getName()));
		}
		e.getWhoClicked().sendMessage("§e§l[!] §eÜrün satýn alma baþarýlý.");
		
	}

}
