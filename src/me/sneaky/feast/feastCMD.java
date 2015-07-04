package me.sneaky.feast;

import me.sneaky.Main;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class feastCMD implements CommandExecutor {
	
	  final Main p;
	  

	  public feastCMD(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 1){
		    	if(args[0].equalsIgnoreCase("1")){
		    	if(p.util.hasPermission(player, "feast.set")) return true;
		    	Block block = player.getTargetBlock(null, 0);
		    	
		    	
		    	double x = block.getLocation().getX();
		    	double y = block.getLocation().getY();
		    	double z = block.getLocation().getZ();
		    		
		    	p.getConfig().set("feast.world", player.getWorld().getName());
		    	
		    	p.getConfig().set("feast.pos.1.x", x);
		    	p.getConfig().set("feast.pos.1.y", y);
		    	p.getConfig().set("feast.pos.1.z", z);
		    	p.saveConfig();
		    	}
		    	if(args[0].equalsIgnoreCase("2")){
		    	if(p.util.hasPermission(player, "feast.set")) return true;
		    	Block block = player.getTargetBlock(null, 0);
		    	
		    	
		    	double x = block.getLocation().getX();
		    	double y = block.getLocation().getY();
		    	double z = block.getLocation().getZ();
		    			
		    	p.getConfig().set("feast.pos.2.x", x);
		    	p.getConfig().set("feast.pos.2.y", y);
		    	p.getConfig().set("feast.pos.2.z", z);
		    	p.saveConfig();
		    	}
		    	if(args[0].equalsIgnoreCase("mid")){
		    	if(p.util.hasPermission(player, "feast.set")) return true;
		    	Block block = player.getTargetBlock(null, 0);
		    	
		    	
		    	double x = block.getLocation().getX();
		    	double y = block.getLocation().getY();
		    	double z = block.getLocation().getZ();
		    			
		    	p.getConfig().set("feast.mid.x", x);
		    	p.getConfig().set("feast.mid.y", y);
		    	p.getConfig().set("feast.mid.z", z);
		    	p.saveConfig();
		    	}
		    	if(args[0].equalsIgnoreCase("fill")){
		    	if(p.util.hasPermission(player, "feast.fill")) return true;
		    	feastListener.fillFeast(p);
		    	}
		    }
		    return false;
            	}

	  }

