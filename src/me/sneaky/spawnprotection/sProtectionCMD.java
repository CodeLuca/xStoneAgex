package me.sneaky.spawnprotection;

import me.sneaky.Main;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sProtectionCMD implements CommandExecutor {
	
	  final Main p;
	  

	  public sProtectionCMD(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 1){
		    	if(args[0].equalsIgnoreCase("1")){
		    	if(p.util.hasPermission(player, "prot.set")) return true;
		    	Block block = player.getTargetBlock(null, 0);
		    	
		    	
		    	double x = block.getLocation().getX();
		    	double y = block.getLocation().getY();
		    	double z = block.getLocation().getZ();
		    			
		    	p.getConfig().set("prot.pos.1.x", x);
		    	p.getConfig().set("prot.pos.1.y", y);
		    	p.getConfig().set("prot.pos.1.z", z);
		    	p.saveConfig();
		    	}
		    	if(args[0].equalsIgnoreCase("2")){
		    	if(p.util.hasPermission(player, "prot.set")) return true;
		    	Block block = player.getTargetBlock(null, 0);
		    	
		    	
		    	double x = block.getLocation().getX();
		    	double y = block.getLocation().getY();
		    	double z = block.getLocation().getZ();
		    			
		    	p.getConfig().set("prot.pos.2.x", x);
		    	p.getConfig().set("prot.pos.2.y", y);
		    	p.getConfig().set("prot.pos.2.z", z);
		    	p.saveConfig();
		    	}
		    }
		    return false;
            	}

	  }

