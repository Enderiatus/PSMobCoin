package me.Enderiatus.MobCoin;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinCommand implements CommandExecutor {
	
	Main plugin;
	CoinCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("mobcoin")) 
			return false;
		Player p = (Player) sender;
		if(args.length < 1) {
			p.sendMessage("§4! §e"+plugin.mobCoins.get(p.getName())+" §6mob puanýnýz mevcut.");
			return true;
		}
		if(args[0].equalsIgnoreCase("market")) {
			plugin.openMobMarket(p);
		}
		if(args[0].equalsIgnoreCase("ekle")) {
			if(!p.isOp()) {
				p.sendMessage("§c§l[!] §cBu komutu kullanmak için yetkiniz yok..");
				return false;
			}
			if(!(args.length == 3)) {
				p.sendMessage("§c§l[!] §cKullaným þekli: §7/mobcoin ekle <oyuncu> <miktar>");
				return false;
			}
			if(!(plugin.mobCoins.containsKey(args[1]))) {
				p.sendMessage("§c§l[!] §cOyuncu aktif deðil.");
				return false;
			}
			try {
				int addedCoin = Integer.parseInt(args[2]);
				plugin.mobCoins.put(p.getName(), plugin.mobCoins.get(p.getName())+addedCoin);
				p.sendMessage("§e§l[!] §eBaþarýlý bir þekilde eklendi.");
			}catch(NumberFormatException error) {
				p.sendMessage("§c§l[!] §cKullaným þekli: §7/mobcoin ekle <oyuncu> <miktar>");
				
			}

			
		}
		
		
		return false;
	}

}
