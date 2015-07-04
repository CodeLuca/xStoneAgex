package me.sneaky.cmds.staff;


import me.sneaky.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelPos implements CommandExecutor{

	
	  final Main p;
	  

	  public DuelPos(Main instance)
	  {
	    this.p = instance;
	  }
	  
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
	    	if(player.hasPermission("skits.duel.pos")){
		    if(args.length == 1){
			    	if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("2")){
			    	p.getConfig().set("duel.pos." + args[0] + ".x", player.getLocation().getX());
			    	p.getConfig().set("duel.pos." + args[0] + ".y", player.getLocation().getY());
			    	p.getConfig().set("duel.pos." + args[0] + ".z", player.getLocation().getZ());
			    	p.getConfig().set("duel.pos." + args[0] + ".yaw", player.getLocation().getYaw());
			    	p.getConfig().set("duel.pos." + args[0] + ".pitch", player.getLocation().getPitch());
			    	p.saveConfig();
		    	}
			    }
		    }
			return false;
          	}

	
}
