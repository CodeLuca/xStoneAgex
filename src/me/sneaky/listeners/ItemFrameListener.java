package me.sneaky.listeners;

import me.sneaky.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ItemFrameListener implements CommandExecutor {
	
	  final Main p;
	  

	  public ItemFrameListener(Main instance)
	  {
	    this.p = instance;
	  }
	  
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    if(args.length == 0){

		    }
		    return false;
            	}
	  
	  

}
