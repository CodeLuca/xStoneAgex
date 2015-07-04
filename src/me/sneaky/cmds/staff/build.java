package me.sneaky.cmds.staff;

import java.util.HashSet;

import me.sneaky.Main;

import org.bukkit.ChatColor; 	
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class build implements CommandExecutor {
	
	  final Main p;
	  

	  public build(Main instance)
	  {
	    this.p = instance;
	  }

	public static HashSet<String> Build = new HashSet<String>();

	
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage("Players can only use that command!");
			return true;
		}
		
		if(args.length == 1){
		Player p = (Player) s;
		

	    if (args[0].equalsIgnoreCase("butter")){
	    	
			if (!p.hasPermission("skits.staff.broadcast")) {
				this.p.chat.noPermMessage(p);
				return true;
			}
			
	        if (!Build.contains(p.getName())) {
		        Build.add(p.getName());
	          p.setGameMode(GameMode.CREATIVE);
	          p.sendMessage(ChatColor.RED + "Build " + ChatColor.GRAY + "Mode Activated!");
	          return true;
	        }
	     
	       if (Build.contains(p.getName())){
		    Build.remove(p.getName());
	        p.setGameMode(GameMode.SURVIVAL);
	        p.sendMessage(ChatColor.RED + "Build " + ChatColor.GRAY + "Mode Desactivated!");
	        return true;
	       		}
	    }
		}
		
		return true;
	
	}
}
