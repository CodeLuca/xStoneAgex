package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cAdd implements CommandExecutor{

	
	  final Main p;
	  

	  public cAdd(Main instance)
	  {
	    this.p = instance;
	  }
	  
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0 || args.length == 1){
		    	if(player.hasPermission("skits.staff.cadd")){
		    		p.chat.sendMessagePlayer(player, "Usage: /cAdd [Player] [Credits");
		    	}   	
		    }else
		    if(args.length == 2){
		    	if(player.hasPermission("skits.staff.cadd")){
		    		Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                    	 p.chat.sendMessagePlayer(player, "Could Not Find Player " + args[0] + "!");
                        return true;
                }
    				if(Integer.valueOf(args[1]) == null){
    					player.sendMessage("Enter a Valid Amount");
    					return true; 
    				}
    				
    				try {
						p.stats.addCredits(target, Integer.valueOf(args[1]));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


    				player.sendMessage("Added " + Integer.valueOf(args[1]) + " to the balance of " + target.getName() + ".");
    				return true;
    				
		    	}
		    }
			return false;
          	}

	
}
