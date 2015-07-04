package me.sneaky.onevsone;

import me.sneaky.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD1v1 implements CommandExecutor {
	
	  final Main p;
	  

	  public CMD1v1(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  Utils1v1 u1v1 = new Utils1v1(Main.instance);
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    	if(p.util.isInSpawn(player)){
		    	p.util.Clear(player);
		    	u1v1.tpTo1v1(player);
		    	p.chat.sendCustomMSG(player, "1v1", "Welcome To The 1v1 Arena");
		    	}else{
		    		p.chat.sendCustomMSG(player, "1v1", "You Can Do /1v1 In Spawn");	
		    	}
		    }else
		    
		    if(args.length == 1){
		    	if(player.hasPermission("skits.1v1.set")){
		    	if(args[0].equalsIgnoreCase("spawn")){
		    		p.getConfig().set("1v1.spawn.x", player.getLocation().getX());
		    		p.getConfig().set("1v1.spawn.y", player.getLocation().getY());
		    		p.getConfig().set("1v1.spawn.z", player.getLocation().getZ());
		    		p.saveConfig();
		    		p.chat.sendMessagePlayer(player, "You Have Set The 1v1 Spawn");
		    	}
		    	if(args[0].equalsIgnoreCase("1")){
		    		p.getConfig().set("1v1.arena.pos.1.x", player.getLocation().getX());
		    		p.getConfig().set("1v1.arena.pos.1.y", player.getLocation().getY());
		    		p.getConfig().set("1v1.arena.pos.1.z", player.getLocation().getZ());
		    		p.saveConfig();
		    		p.chat.sendMessagePlayer(player, "You Have Set The 1v1 Pos 1");
		    	}
		    	if(args[0].equalsIgnoreCase("2")){
		    		p.getConfig().set("1v1.arena.pos.2.x", player.getLocation().getX());
		    		p.getConfig().set("1v1.arena.pos.2.y", player.getLocation().getY());
		    		p.getConfig().set("1v1.arena.pos.2.z", player.getLocation().getZ());
		    		p.saveConfig();
		    		p.chat.sendMessagePlayer(player, "You Have Set The 1v1 Pos 2");
		    	}
		    	}
		    }else{
		    	p.chat.sendMessagePlayer(player, "Usage: /1v1");
		    }
		    return false;
            	}

	  }

