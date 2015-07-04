package me.sneaky.cmds;

import me.sneaky.Main;
import mkremins.fanciful.FancyMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help implements CommandExecutor {
	
	  final Main p;
	  

	  public Help(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    	p.chat.sendMessagePlayer(player, "Help:");
		    	p.chat.sendMessagePlayer(player, "Hold Your Mouse On The");
		    	p.chat.sendMessagePlayer(player, "Text For More Info ");
		          new FancyMessage("")
		          .then("Post Your Own Kit Ideas Here")
		          .color(ChatColor.GRAY)
		          .link("http://goo.gl/Txgx02")
		          .tooltip("Click Here To Open The Link")
		          .send(player);
		          
		          new FancyMessage("")
		          .then("Stats")
		          .color(ChatColor.GRAY)
		          .command("/stats")
		          .tooltip("Click Here To See Your Stats")
		          .send(player);
		          
		          new FancyMessage("")
		          .then("1v1")
		          .color(ChatColor.GRAY)
		          .command("/1v1")
		          .tooltip("Click Here To Go To The 1v1")
		          .send(player);
		          
		          new FancyMessage("")
		          .then("Ping")
		          .color(ChatColor.GRAY)
		          .ping(player)
		          .send(player);
		    }
		    return false;
            	}

	  }

