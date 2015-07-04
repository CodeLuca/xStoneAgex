package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Broadcast implements CommandExecutor{

	
	  final Main p;
	  

	  public Broadcast(Main instance)
	  {
	    this.p = instance;
	  }
	  
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player) sender;


			if (cmd.getName().equalsIgnoreCase("bc")) { 
				if (!player.hasPermission("skits.staff.broadcast")) {
					p.chat.noPermMessage(player);
					return true;
				}
				
				if (args.length == 0) {
					p.chat.sendMessagePlayer(player, "/broadcast <message>");
					return true;
				}
				
				if (args.length >= 1){
				
				StringBuilder sb = new StringBuilder();
				
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				
				String msg = sb.toString();
				
				p.chat.sendBroadcast(msg + ChatColor.RED + "" + ChatColor.ITALIC + "-" + player.getName());
				}
			}
		    return true;	    
	}
}