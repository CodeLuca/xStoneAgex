package me.sneaky.cmds.staff;


import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kick implements CommandExecutor{

	
	  final Main p;
	  

	  public Kick(Main instance)
	  {
	    this.p = instance;
	  }
	  
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    	if(player.hasPermission("skits.staff.kick")){
		    		p.chat.sendMessagePlayer(player, "Usage: /Kick [Player]");
		    	}   	
		    }else
		    if(args.length == 1){
		    	if(player.hasPermission("skits.staff.kick")){
		    		Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                    	 p.chat.sendMessagePlayer(player, "Could Not Find Player " + args[0] + "!");
                        return true;
                }
		    		target.kickPlayer(ChatColor.RED + "You Have Been Kicked");
                    Bukkit.broadcastMessage(ChatColor.GREEN + target.getName() + ChatColor.GRAY + " Was Kicked By " +  ChatColor.GREEN + player.getName());
		    	}
		    }
			return false;
          	}

	
}
