package me.sneaky.events.deathrace;

import java.util.ArrayList;
import me.sneaky.Main;
import me.sneaky.events.Events;
import me.sneaky.events.Events.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilsDeathRace implements CommandExecutor {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public static boolean started = false;
	public static boolean countdown = false;
	
	public static int playersStartedWith = 0;
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.RED + "Death Race" + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.RED + "Death Race " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void addPlayer(Player player){
		if(!players.contains(player)){
		players.add(player);
		tpToLobby(player);
		Main.instance.util.Clear(player);
		}
	}
	
	public void removePlayer(Player player){
		if(players.contains(player)){
		players.remove(player);
		Main.instance.util.Clear(player);
		}
	}
	
	public void stopGame(){
		players.clear();
		UtilsDeathRace.started = false;
		UtilsDeathRace.countdown = false;
		Events.eventHosted = null;
		Events.newEvent();
	}
	
	public void startGame(){
		if(players.size() >= 2){
			broadcastMSG("Started!");
			UtilsDeathRace.started = true;
			UtilsDeathRace.playersStartedWith = players.size();
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

	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public void tpToArena(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.deathrace.arena.x");
		int y = Main.instance.getConfig().getInt("event.deathrace.arena.y");
		int z = Main.instance.getConfig().getInt("event.deathrace.arena.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.deathrace.lobby.x");
		int y = Main.instance.getConfig().getInt("event.deathrace.lobby.y");
		int z = Main.instance.getConfig().getInt("event.deathrace.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsDeathRace.countdown == false && UtilsDeathRace.started == false){
		UtilsDeathRace.countdown = true;
		Events.eventHosted = Minigames.HG;
		players.clear();
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
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /DRace Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /DRace Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /DRace Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /DRace Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /DRace Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /DRace Join To Join");}
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
	    		
	    		if(UtilsDeathRace.countdown == false && UtilsDeathRace.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsDeathRace.started == false && UtilsDeathRace.countdown == true){
	    		sendMSG(player, "You Have Joined The DeathRace Tournament");
	    		this.addPlayer(player);
	    		}else
	    			if(UtilsDeathRace.started == true){
	    			sendMSG(player, "The DeathRace Tournament Already Started");
	    		}else
	    			if(UtilsDeathRace.countdown == false){
	    				sendMSG(player, "There Is No DeathRace Tournament Hosted At The Moment");
	    			}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left The DeathRace Tournament");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In The DeathRace Tournament");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /DeathRace <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 2){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("arena")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.deathrace.arena.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.deathrace.arena.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.deathrace.arena.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The DeathRace Arena Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("lobby")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.deathrace.lobby.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.deathrace.lobby.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.deathrace.lobby.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The DeathRace Lobby Spawn Point");
	    			}
	    			}
	    		}
	    		
	    	}
	    return false;
        	}
	
}
