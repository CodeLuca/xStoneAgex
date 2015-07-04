package me.sneaky;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {
	
	  final Main p;
	  

	  public Chat(Main instance)
	  {
	    this.p = instance;
	  }
	
		public void sendMessagePlayer(Player player, String msg){
			player.sendMessage(ChatColor.RED + "StoneAgeKits> " + ChatColor.GRAY + msg);
		}
	
		
		public void sendCustomMSG(Player player, String name, String msg){
			player.sendMessage(ChatColor.RED + name + "> " + ChatColor.GRAY + msg);
		}
	
	public void sendMessageKitPlayer(Player player, String kit){
		player.sendMessage(ChatColor.GRAY + "You are now a " + ChatColor.GREEN + kit);
	}
	
	public void cooldownMessage(Player player){
		player.sendMessage(ChatColor.GRAY + "You Are Still On A Cooldown For " + ChatColor.GREEN + p.util.getTimeCD(player) + ChatColor.GRAY + " Seconds");
	}
	
	public void inSpawnMessage(Player player){
		sendMessagePlayer(player, "You Can't Use Your Kit In The Spawn");
	}
	
	public void cooldownOverMessage(Player player){
		player.sendMessage(ChatColor.GRAY + "Cooldown Is Over");
	}
	
	public void noPermMessage(Player player){
		sendMessagePlayer(player, "Permission Denied");
	}
	
	public void sendBroadcast(String msg){
		Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "(StoneAgeKits)" + ChatColor.GOLD + "" + ChatColor.BOLD + msg);
	}
	
	public void sendCustomBroadcast(String title, String msg){
		Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "(" + title + ")" + ChatColor.GOLD + "" + ChatColor.BOLD + msg);
	}
}
