package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mute implements CommandExecutor {
	
	  final Main p;
	  

	  public Mute(Main instance)
	  {
	    this.p = instance;
	  }
	  
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 1){
		    	Player target = Bukkit.getPlayer(args[0]);
		    	if(target == null){
		    		player.sendMessage(ChatColor.RED + "That Player Is Not Online!");
		    	}
		    	player.sendMessage(ChatColor.GRAY + "You Have Muted " + target.getName());
		    }
		    return false;
            	}

	  }

