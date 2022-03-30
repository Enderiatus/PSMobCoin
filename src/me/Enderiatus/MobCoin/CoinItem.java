package me.Enderiatus.MobCoin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CoinItem {
	
	private ItemStack item;
	private int price;
	private List<String> cmds;

	public CoinItem(String itemMaterial, String itemName, List<String> lore, List<String> cmds, int price) {
		this.item = new ItemStack(Material.matchMaterial(itemMaterial));
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(itemName);
		for(int i = 0 ; i<lore.size() ; i++) {
			lore.set(i,ChatColor.translateAlternateColorCodes('&',lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		this.setPrice(price);
		this.setCmds(cmds);
	}
	
	public ItemStack getItem() {
		return item;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<String> getCmds() {
		return cmds;
	}

	public void setCmds(List<String> cmds) {
		this.cmds = cmds;
	}

}
