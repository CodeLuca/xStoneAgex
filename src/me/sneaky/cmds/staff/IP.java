package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IP implements CommandExecutor {
	
	  final Main p;
	  

	  public IP(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 1){
		    	if(player.getName().equalsIgnoreCase("SneakyLegend") || player.getName().equalsIgnoreCase("mrjayharm")){
		    	Player target = Bukkit.getServer().getPlayerExact(args[0]);
		    	if(target == null){
		    		return true;
		    	}
		    	player.sendMessage(target.getName() + "'s IP: " + target.getAddress());
		    	
		    	}else{
		    		player.sendMessage(ChatColor.WHITE + "Unknown command. Type /help for help.");
		    	}
		    }else{
	    		player.sendMessage(ChatColor.WHITE + "Unknown command. Type /help for help.");
	    	}
		    return false;
            	}

	  }

