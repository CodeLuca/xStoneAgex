package me.sneaky.events.hg;

import java.util.ArrayList;
import java.util.HashMap;

import me.sneaky.Main;
import me.sneaky.events.Events;
import me.sneaky.events.Events.Minigames;
import me.sneaky.kits.Kits.sKits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilsHG implements CommandExecutor {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static HashMap<Player, sKits> preKit = new HashMap<Player, sKits>();
	
	public static boolean started = false;
	public static boolean countdown = false;
	
	public static int playersStartedWith = 0;
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.GOLD + "HG " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.GOLD + "HG " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void addPlayer(Player player){
		if(!players.contains(player)){
		players.add(player);
		preKit.put(player, Main.instance.util.getKit(player));
		tpToLobby(player);
		Main.instance.util.Clear(player);
		}
	}
	
	public void removePlayer(Player player){
		if(players.contains(player)){
		players.remove(player);
		preKit.remove(player);
		Main.instance.util.Clear(player);
		}
	}
	
	public void stopGame(){
		players.clear();
		preKit.clear();
		UtilsHG.started = false;
		UtilsHG.countdown = false;
		Events.eventHosted = null;
		Events.newEvent();
	}
	
	public void startGame(){
		if(players.size() >= 2){
			broadcastMSG("Started!");
			UtilsHG.started = true;
			UtilsHG.playersStartedWith = players.size();
			for(Player pl : players){
				giveStuff(pl);
				tpToArena(pl);
				pl.setNoDamageTicks(20 * 10);
				sendMSG(pl, "You Get No Damage For 10 Seconds");
			}
		}else{
			stopGame();
			broadcastMSG("Not Enough Players!");
		}
	}
	
	public void giveStuff(Player player){
		Main.instance.KitsClass.giveKit(player, preKit.get(player) != null ? preKit.get(player) : sKits.PvP);
	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public void tpToArena(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.hg.arena.x");
		int y = Main.instance.getConfig().getInt("event.hg.arena.y");
		int z = Main.instance.getConfig().getInt("event.hg.arena.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.hg.lobby.x");
		int y = Main.instance.getConfig().getInt("event.hg.lobby.y");
		int z = Main.instance.getConfig().getInt("event.hg.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsHG.countdown == false && UtilsHG.started == false){
		UtilsHG.countdown = true;
		Events.eventHosted = Minigames.HG;
		players.clear();
		preKit.clear();
		broadcastMSG("Starting In 1 Minute");
		Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run() {
				startGame();
			}
			}, 20 * 60);
		for(int i = 0; i < 60; i ++){
			final int c = i;
			Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run() {
					int time = 60 - c;
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /HG Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /HG Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /HG Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /HG Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /HG Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /HG Join To Join");}
				}
			}, 20 * i);
		}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
	    final Player player = (Player)sender;
	    if(args.length == 1){
	    	if(args[0].equalsIgnoreCase("host")){
	    		if(!player.hasPermission("skits.event.host")){
	    			sendMSG(player, "No Permission");
	    			return true;
	    		}
	    		
	    		if(Events.eventHosted != null){
	    			sendMSG(player, "Only 1 event at the time");
	    			return true;
	    		}
	    		
	    		if(UtilsHG.countdown == false && UtilsHG.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsHG.started == false && UtilsHG.countdown == true){
	    		sendMSG(player, "You Have Joined The HG Tournament");
	    		this.addPlayer(player);
	    		}else
	    			if(UtilsHG.started == true){
	    			sendMSG(player, "The HG Tournament Already Started");
	    		}else
	    			if(UtilsHG.countdown == false){
	    				sendMSG(player, "There Is No HG Tournament Hosted At The Moment");
	    			}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left The HG Tournament");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In The HG Tournament");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /HG <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 2){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("arena")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.hg.arena.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.hg.arena.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.hg.arena.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The HG Arena Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("lobby")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.hg.lobby.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.hg.lobby.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.hg.lobby.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The HG Lobby Spawn Point");
	    			}
	    			}
	    		}
	    		
	    	}
	    return false;
        	}
	
}
