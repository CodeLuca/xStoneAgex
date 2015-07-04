package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapVideo implements CommandExecutor{

	
	  final Main p;
	  

	  public MapVideo(Main instance)
	  {
	    this.p = instance;
	  }
	  
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player) sender;
		    
				if (!player.hasPermission("skits.staff.block")) {
					p.chat.noPermMessage(player);
					return true;
				}
				if (args.length == 3){
					
				}
		    return true;	    
	}
}