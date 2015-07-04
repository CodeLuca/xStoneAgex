package me.sneaky.anticheat;

import java.util.HashMap;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class aUtils{
	
	  final Main p;
	  
	  public aUtils(Main instance)
	  {
	    this.p = instance;
	  }
	  int timer;
	  int SpamTicks;
	  
	  /*****************AutoClick/Macro's**********/
	  public HashMap<Player, Integer> acClicks = new HashMap<Player, Integer>();
	  public HashMap<Player, Integer> acWarns = new HashMap<Player, Integer>();
	  /********************************************/
	  
	  /*****************AutoSoup******************/
	  public HashMap<String, Integer> asInv = new HashMap<String, Integer>();
	  public HashMap<Player, Integer> asWarns = new HashMap<Player, Integer>();
	  /********************************************/
	  
	  
	  public void checkHax(){
			p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable() {
				public void run() {
					timer++;
					SpamTicks++;
					if(timer >= 10){
						timer = 0;
					}
					for(Player player : Bukkit.getServer().getOnlinePlayers()){
					
					/*****************AutoClick/Macro's******************/
					if(acClicks.get(player) != null){
						if(acClicks.get(player) >= 25){
							sendMsgMacroAdmin(player, acClicks.get(player) );
							acClicks.put(player, 0);
							acWarns.put(player, acWarns.get(player) != null ? acWarns.get(player) + 1 : 1);
						}
					}
					if(acWarns.get(player) != null){
					if(acWarns.get(player) == 3){
						player.kickPlayer(ChatColor.RED + "If You Won't Stop Using AutoClicker/Macro's You Will Be Banned");
						acWarns.put(player, 0);
					}
					}
					acClicks.put(player, 0);
					
					/*****************AutoSoup******************/
					if(asWarns.get(player) != null){
						if(asWarns.get(player) >= 1){
							sendMsgAdmin(player, "AutoSoup");
							asWarns.put(player, 0);
						}	
					}
					
					
					}
				}
				}, 20, 20);
	  }
	  
	  public void sendMsgMacroAdmin(Player player, Integer i){
		  for(Player pl : Bukkit.getServer().getOnlinePlayers()){
			  if(pl.hasPermission("skits.anticheat.alert")){
			  pl.sendMessage(ChatColor.RED + player.getName() + " Is Suspicious" + "(" + i + " Clicks/Second)" );
			  }
		  }
	  }
	  
	  public void sendMsgAdmin(Player player, String string){
		  for(Player pl : Bukkit.getServer().getOnlinePlayers()){
			  if(pl.hasPermission("skits.anticheat.alert")){
			  pl.sendMessage(ChatColor.RED + player.getName() + " Is Suspicious" + "(" + string + ")" );
			  }
		  }
	  }
	  
	  
	  



}
