package me.Enderiatus.MobCoin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.Enderiatus.MobCoin.Events.InvClick;
import me.Enderiatus.MobCoin.Events.MobDead;
import me.Enderiatus.MobCoin.Events.PJoinLeave;

public class Main extends JavaPlugin{
	
	
	/*
	 * 				TO DO LIST
	 *   1: sql lite ile veriler çekilecek. ++
	 *   2: enable/disable de player verileri savelenecek. --
	 *   3: oyuncu gir/çýkta savelenecek. ++
	 *   4: default hashmap kullanýlacak, verileri çekmek için. sonrasýnda üstünden gidilecek. ++
	 *   5: config üzerinden slot ayarý. ++
	 *   	- RandomName:
	 *   		- DisplayName: 'Denem item'
	 *   		- ItemCode: 264
	 *   		- Slot: 1
	 *   		- Price: 1212
	 *   		- Lore:
	 *   			- bla bla
	 *   			- bla bla
	 *   		- Commands:
	 *   			- bla bla
	 *   			-bla bla
	 *   
	 *   6: config üzerinden mob-coin drop rate --
	 *   7: inv. click event ++
	 *   
	 */
	
	
	public HashMap<String, Integer> mobCoins;
	public HashMap<Integer, CoinItem> coinItems;
	public SQLUtil sql; 
	
	public void onEnable() {
		Bukkit.getLogger().info("[PS MobCoin] Eklenti aktiflestirildi.");
		saveDefaultConfig();
		Bukkit.getServer().getPluginManager().registerEvents(new InvClick(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PJoinLeave(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new MobDead(this), this);
		getCommand("mobcoin").setExecutor(new CoinCommand(this));
		mobCoins = new HashMap<String, Integer>();
		coinItems = new HashMap<Integer, CoinItem>();
		sql = new SQLUtil(this); sql.loadSQL();
		loadItems();
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			getPlayerMobCoin(p);
		}
		
	}
	
	public void onDisable() {
		Bukkit.getLogger().info("[PS MobCoin] Eklenti kapatiliyor.");
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			sql.setMobCoin(p.getName());
		}
		try {
			sql.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getPlayerMobCoin(Player player) {
		mobCoins.put(player.getName(), sql.getMobCoin(player.getName()));
		
	}
	
	private void loadItems() {
		for(String slot : getConfig().getConfigurationSection("Items").getKeys(false)) {
			coinItems.put(Integer.parseInt(slot), new CoinItem(
					getConfig().getString("Items."+slot+".Item"),
					ChatColor.translateAlternateColorCodes('&', getConfig().getString("Items."+slot+".Name")),
					getConfig().getStringList("Items."+slot+".Lore"),
					getConfig().getStringList("Items."+slot+".Commands"),
					getConfig().getInt("Items."+slot+".Price")));
			
		}
	}

	public void openMobMarket(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, "§6§lMob Puan Marketi");
		for(int slot : coinItems.keySet()) {
			inv.setItem(slot, coinItems.get(slot).getItem());
		}
		inv.setItem(4, createItem(Material.DOUBLE_PLANT, (byte)1, (short)0, 
				"&e&l[!] &6"+mobCoins.get(p.getName())+" §etane Mob Puanýn var", 
				"&&&e&lBILGILENDIRME&& &7Mob puanlarýn ile alabileceklerin:&&  &8- &7Yetkiler&&  &8- &7Rütbeler&&  &8- &7Sandýklar"));
		inv.setItem(49, createItem(Material.PAPER, (byte)1, (short)0, 
				"&e&l[!] Nasýl elde edilir?", 
				"&&&7Yaratýk öldürerek puan elde edebilirsiniz.&&&7Öldürdüðünüz yaratýðýn gücü fazla ise&&&7puan kazanma þansýnýz artar.&&&&&e&lORANLAR&&  &aZombi, Iskelet, Blaze &f%5&&  &2Creeper, EnderMan &f%8&&  &cWither &f%50"));
		int[] borderLines = {0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 50, 51, 52, 53};
		for(int i = 0; i<borderLines.length ; i++) {
			inv.setItem(borderLines[i], createItem(Material.STAINED_GLASS_PANE, (byte)1, (short)15, " ", " "));
		}
		p.openInventory(inv);
	}
	
	public ItemStack createItem(Material material, byte miktar, short value, String name, String lore) {
		ItemStack item = new ItemStack(material, miktar, value);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		ArrayList<String> Lore = new ArrayList<String>();
		String[] listing = lore.split("&&");
		for(int deger = 0; deger<listing.length ;deger++) {
			Lore.add(ChatColor.translateAlternateColorCodes('&',listing[deger]));
		}
		meta.setLore(Lore);
		item.setItemMeta(meta);
		return item;
	}
	
	

}
