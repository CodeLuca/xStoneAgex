package me.sneaky.events.chickenwars;

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
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilsChickenWars implements CommandExecutor {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static HashMap<Player, Entity> playerVehicle = new HashMap<Player, Entity>();
	
	public static boolean started = false;
	public static boolean countdown = false;
	
	public static int playersStartedWith = 0;
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.YELLOW + "Chicken Wars " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.YELLOW + "Chicken Wars " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
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
		UtilsChickenWars.started = false;
		UtilsChickenWars.countdown = false;
		Events.eventHosted = null;
		Events.newEvent();
	}
	
	public void startGame(){
		if(players.size() >= 2){
			broadcastMSG("Started!");
			UtilsChickenWars.started = true;
			UtilsChickenWars.playersStartedWith = players.size();
			int i = 0;
			for(Player pl : players){
				i++;
				tpToArena(pl, i);
				giveStuff(pl);
			}
		}else{
			stopGame();
			broadcastMSG("Not Enough Players!");
		}
	}
	
	public void giveStuff(Player player){
		Main.instance.KitsClass.giveKit(player, sKits.PvP);
		Chicken chick = (Chicken) player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.CHICKEN);
		chick.setPassenger(player);
		chick.setNoDamageTicks(Integer.MAX_VALUE);
		chick.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 99));
		playerVehicle.put(player, chick);
	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public void tpToArena(Player player, int spawn){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.cwars.arena."+spawn+".x");
		int y = Main.instance.getConfig().getInt("event.cwars.arena."+spawn+".y");
		int z = Main.instance.getConfig().getInt("event.cwars.arena."+spawn+".z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.cwars.lobby.x");
		int y = Main.instance.getConfig().getInt("event.cwars.lobby.y");
		int z = Main.instance.getConfig().getInt("event.cwars.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsChickenWars.countdown == false && UtilsChickenWars.started == false){
		Events.eventHosted = Minigames.ChickenWars;
		UtilsChickenWars.countdown = true;
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
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /CWars Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /CWars Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /CWars Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /CWars Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /CWars Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /CWars Join To Join");}
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
	    		if(UtilsChickenWars.countdown == false && UtilsChickenWars.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsChickenWars.players.size() < 12){
	    		if(UtilsChickenWars.started == false && UtilsChickenWars.countdown == true){
	    		sendMSG(player, "You Have Joined The ChickenWars Tournament");
	    		this.addPlayer(player);
	    		}else
	    			if(UtilsChickenWars.started == true){
	    			sendMSG(player, "The ChickenWars Tournament Already Started");
	    		}else
	    			if(UtilsChickenWars.countdown == false){
	    				sendMSG(player, "There Is No ChickenWars Tournament Hosted At The Moment");
	    			}
	    		}else{
	    			sendMSG(player, "ChickenWars Is Full");
	    		}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left The ChickenWars Tournament");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In The ChickenWars Tournament");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /CWars <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 3){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("arena")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.cwars.arena."+ args[2] +".x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.cwars.arena."+ args[2] +".y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.cwars.arena."+ args[2] +".z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The ChickenWars Arena Spawn Point");
	    			}
	    			}
	    		}
	    		
	    	}else
		    	if(args.length == 2){
		if(args[1].equalsIgnoreCase("lobby")){
			if(player.isOp()){
		Main.instance.getConfig().set("event.cwars.lobby.x", player.getLocation().getX());
		Main.instance.getConfig().set("event.cwars.lobby.y", player.getLocation().getY());
		Main.instance.getConfig().set("event.cwars.lobby.z", player.getLocation().getZ());
		Main.instance.saveConfig();
		sendMSG(player, "You Have Set The ChickenWars Lobby Spawn Point");
		}
		}
		}
	    return false;
        	}
	
}
