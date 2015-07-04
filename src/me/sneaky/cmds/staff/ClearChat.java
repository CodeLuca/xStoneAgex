package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {
	
	  final Main p;
	  

	  public ClearChat(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    	if(player.hasPermission("skits.staff.cc")){
		    	for(int i = 0; i < 120; i++){
		    	Bukkit.broadcastMessage("");
		    	}
		    	Bukkit.broadcastMessage(ChatColor.GRAY + "The Chat Has Been Cleared By " + ChatColor.GREEN + player.getName());
		    }
		    }
		    return false;
            	}

	  }

