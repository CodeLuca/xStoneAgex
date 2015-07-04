package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Admin implements CommandExecutor, Listener {
	
	  final Main p;

	  public Admin(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @EventHandler
	  public void join(PlayerJoinEvent e){
		  Player player = e.getPlayer();
		  if(!player.hasPermission("skits.staff.admin")){
		  for(Player pl : Bukkit.getServer().getOnlinePlayers()){
			  if(p.util.Invis.contains(pl)){
				  player.hidePlayer(pl);
			  }
		  }
		  }
		  
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		  
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    	if(player.hasPermission("skits.staff.admin")){
		    	if(!p.util.Invis.contains(player)){
		    		p.chat.sendBroadcast(ChatColor.BLUE + "StoneAgeKits> " + ChatColor.GOLD + player.getName() + " Left");
		    	for(Player pl : Bukkit.getServer().getOnlinePlayers()){
		    		if(!pl.hasPermission("skits.admin.invis")){
		    		pl.hidePlayer(player);
		    		}
		    	}
		    	p.util.Invis.add(player);
		    	p.chat.sendMessagePlayer(player, "You Are Now Invisible To Mod+ And Below");
		    }else{
		    	p.chat.sendBroadcast(ChatColor.BLUE + "StoneAgeKits> " + ChatColor.GOLD + player.getName() + " Joined");
		    	for(Player pl : Bukkit.getServer().getOnlinePlayers()){
		    		pl.showPlayer(player);
		    	}
		    	p.util.Invis.remove(player);
		    	p.chat.sendMessagePlayer(player, "You Are Now Visible To Default And Above");
		    }
		    }
		    }
		    return false;
            	}

	  }

