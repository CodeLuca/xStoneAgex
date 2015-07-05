package me.sneaky.cmds.staff;


import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ban implements CommandExecutor{
	
	  final Main p;
	  

	  public Ban(Main instance)
	  {
	    this.p = instance;
	  }
	  
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    	if(player.hasPermission("skits.staff.ban")){
		    		p.chat.sendMessagePlayer(player, "Usage: /Ban [Player]");
		    	}   	
		    }
		    else
			    if(args.length == 1){
			    	if(player.hasPermission("skits.staff.ban")){
			    		Player target = Bukkit.getServer().getPlayer(args[0]);
	                    if (target == null) {
	                    	if(Bukkit.getServer().getOfflinePlayer(args[0]) != null){
	    			    		if(args[0].equalsIgnoreCase("sneakylegend") || args[0].equalsIgnoreCase("lucaspeedstack")
	    			    				|| args[0].equalsIgnoreCase("mrjayharm")){
	    			    			player.kickPlayer(ChatColor.RED + "DO NOT TRY TO BAN THE OWNER!!!");
	    			    			return true;
	    			    		}
	    			    		
	                    		Bukkit.getServer().getOfflinePlayer(args[0]).setBanned(true);
	    	                    p.chat.sendBroadcast(args[0] + " Was Banned By " + player.getName());
	    	                    return true;
	                    	}else{
	                    	 p.chat.sendMessagePlayer(player, "Could Not Find Player " + args[0] + "!");
	                        return true;
	                    	}
	                    }
			    		if(args[0].equalsIgnoreCase("sneakylegend") || args[0].equalsIgnoreCase("lucaspeedstack")
			    				|| args[0].equalsIgnoreCase("mrjayharm")){
			    			player.kickPlayer(ChatColor.RED + "DO NOT TRY TO BAN THE OWNER!!!");
			    			return true;
			    		}
			    		target.setBanned(true);
			    		target.kickPlayer(ChatColor.RED + "You Have Been Banned");
			    		Bukkit.broadcastMessage(ChatColor.GREEN + target.getName() + ChatColor.GRAY + " Was Banned By " +  ChatColor.GREEN + player.getName());
			    	}
			    }
			return false;
          	}

	
}
