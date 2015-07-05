package me.sneaky.events.oitc;

import java.util.ArrayList;
import java.util.HashMap;

import me.sneaky.Main;
import me.sneaky.events.Events;
import me.sneaky.events.Events.Minigames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UtilsOITC implements CommandExecutor {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static HashMap<Player, Integer> playerLives = new HashMap<Player, Integer>();
	
	public static boolean started = false;
	public static boolean countdown = false;
	
	public static int playersStartedWith = 0;
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.BLUE + "OITC " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.BLUE + "OITC " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void removeLife(Player player, int lifes){
		if(playerLives.get(player) != null){
			playerLives.put(player, playerLives.get(player) - lifes);
		}
	}
	
	public void addLife(Player player, int lifes){
		if(playerLives.get(player) != null){
			playerLives.put(player, playerLives.get(player) + lifes);
		}else{
			playerLives.put(player, 5);
		}
	}
	
	public void setLife(Player player, int lifes){
			playerLives.put(player, lifes);
	}
	
	public int getLifes(Player player){
		int lifes = 0;
		if(playerLives.get(player) != null){
			lifes = playerLives.get(player);
		}
		return lifes;
	}
	
	public void addPlayer(Player player){
		if(!players.contains(player)){
		players.add(player);
		tpToLobby(player);
		Main.instance.util.Clear(player);
		setLife(player, 5);
		}
	}
	
	public void removePlayer(Player player){
		if(players.contains(player)){
		players.remove(player);
		Main.instance.util.Clear(player);
		setLife(player, 0);
		}
	}
	
	public void stopGame(){
		players.clear();
		UtilsOITC.started = false;
		UtilsOITC.countdown = false;
		Events.eventHosted = null;
		Events.newEvent();
		playerLives.clear();
	}
	
	public void startGame(){
		if(players.size() >= 2){
			broadcastMSG("Started!");
			UtilsOITC.started = true;
			UtilsOITC.playersStartedWith = players.size();
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
		ItemStack bow = new ItemStack(Material.BOW);
		bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 10);
		player.getInventory().setItem(0, bow);
		
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		sword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		player.getInventory().setItem(1, sword);
		
		ItemStack arrow = new ItemStack(Material.ARROW);
		player.getInventory().setItem(8, arrow);
	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public void tpToArena(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.oitc.arena.x");
		int y = Main.instance.getConfig().getInt("event.oitc.arena.y");
		int z = Main.instance.getConfig().getInt("event.oitc.arena.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.oitc.lobby.x");
		int y = Main.instance.getConfig().getInt("event.oitc.lobby.y");
		int z = Main.instance.getConfig().getInt("event.oitc.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsOITC.countdown == false && UtilsOITC.started == false){
		UtilsOITC.countdown = true;
		Events.eventHosted = Minigames.OITC;
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
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /OITC Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /OITC Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /OITC Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /OITC Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /OITC Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /OITC Join To Join");}
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
	    		
	    		if(UtilsOITC.countdown == false && UtilsOITC.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsOITC.started == false && UtilsOITC.countdown == true){
	    		sendMSG(player, "You Have Joined OITC");
	    		this.addPlayer(player);
	    		}else
	    			if(UtilsOITC.started == true){
	    			sendMSG(player, "OITC Already Started");
	    		}else
	    			if(UtilsOITC.countdown == false){
	    				sendMSG(player, "There Is No OITC Hosted At The Moment");
	    			}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left OITC");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In OITC");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /OITC <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 2){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("arena")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.oitc.arena.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.oitc.arena.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.oitc.arena.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The OITC Arena Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("lobby")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.oitc.lobby.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.oitc.lobby.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.oitc.lobby.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The OITC Lobby Spawn Point");
	    			}
	    			}
	    		}
	    		
	    	}
	    return false;
        	}
	
}
