package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warn implements CommandExecutor {
	
	  final Main p;
	  

	  public Warn(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  public enum WarnTypes{
		  Spam(1),
		  Hack(2);
		  
		 int type;
		 
		 WarnTypes(int type){
			 this.type = type;
		 }
		 
		 public int getId(){
			 return type;
		 }
		 
	  }
	  
		 public WarnTypes getType(String t){
			 WarnTypes tt = null;
			 for(WarnTypes type : WarnTypes.values()){
				 String typeName = type.toString();
				 if(t.equalsIgnoreCase(typeName) || typeName.equalsIgnoreCase(t)){
					 tt = type;
				 }
			 }
			 return tt;
		 }
		 
		 public WarnTypes getType(int t){
			 WarnTypes tt = null;
			 for(WarnTypes type : WarnTypes.values()){
				 int typeId = type.getId();
				 if(typeId == t){
					 tt = type;
				 }
			 }
			 return tt;
		 }
	  
	  @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 2){
		    	if(player.hasPermission("skits.staff.warn")){
		    	Player target = Bukkit.getServer().getPlayerExact(args[0]);
		    	if(target == null){
		    		return true;
		    	}
		    	WarnTypes type = getType(args[1]);
		    	if(type != null){
		    		switch (type){
					case Hack:
				    	target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You Have Been Warned For Spamming!");
						break;
					case Spam:
				    	target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You Have Been Warned For Hacking!");
						break;
					default:
						break;
		    		
		    		}
		    	}
		    	}
		    }

		    return false;
            	}

	  }

