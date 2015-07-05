package me.sneaky.cmds.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sneaky.Main;
import me.sneaky.kits.Kits.sKits;

public class FreeKitDay implements CommandExecutor {
	
	  final Main p;
	  

	  public FreeKitDay(Main instance)
	  {
	    this.p = instance;
	  }
	
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			s.sendMessage(ChatColor.RED + "Correct Usage: /freekitday [true / false]");
		}
		
		if (!(s instanceof Player)) {
			s.sendMessage("Players can only use that command!");
			return true;
		}
		
		Player p = (Player) s;
		

	    if (args.length == 1) {
	    	
			if (!p.hasPermission("skits.staff.freekitday")) {
				this.p.chat.noPermMessage(p);
				return true;
			}
			 
			
			if(args[0].equalsIgnoreCase("true")){
				for(sKits kit : sKits.values()){
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mangaddp default skits.kit." + kit.toString().toLowerCase());	
				}
			}
			
			if(args[0].equalsIgnoreCase("false")){
				for(sKits kit : sKits.values()){
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mangdelp default skits.kit." + kit.toString().toLowerCase());	
				}
			}
			
			
	    }
		
		
		return true;
	
	}
}
