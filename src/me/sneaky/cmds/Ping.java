package me.sneaky.cmds;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Ping implements CommandExecutor {
	
	  final Main p;
	  

	  public Ping(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    int ping = ((CraftPlayer) player).getHandle().ping;
		    p.chat.sendMessagePlayer(player, ChatColor.BOLD + "Your Ping Is " + (ping > 60 ? (ping > 100 ? ChatColor.DARK_RED + "" + ping : ChatColor.RED + "" + ping) : ChatColor.GREEN + "" + ping));
		    }
		    
		    if(args.length == 1){
		    	Player target = Bukkit.getPlayer(args[0]);
		    	if(target == null){
		    		return true;
		    	}
		    	int ping = ((CraftPlayer) target).getHandle().ping;
		    	p.chat.sendMessagePlayer(player, ChatColor.BOLD + target.getName() + "'s Ping Is " + (ping > 60 ? (ping > 100 ? ChatColor.DARK_RED + "" + ping : ChatColor.RED + "" + ping) : ChatColor.GREEN + "" + ping));
		    }
		    return false;
            	}

	  }

