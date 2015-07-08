package me.sneaky.events.anvil;

import java.util.ArrayList;
import java.util.Random;

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

public class UtilsAnvil implements CommandExecutor {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	
	public static int timer = 0;
	public static int procent = 0;
	
	public static boolean started = false;
	public static boolean countdown = false;
	
	public static int playersStartedWith = 0;
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.GREEN + "Anvil " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.GREEN + "Anvil " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
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
		UtilsAnvil.started = false;
		UtilsAnvil.countdown = false;
		Events.eventHosted = null;
		UtilsAnvil.timer = 0;
		UtilsAnvil.procent = 0;
		Events.newEvent();
	}
	
	public void startGame(){
		if(players.size() >= 2){
			broadcastMSG("Started!");
			UtilsAnvil.started = true;
			UtilsAnvil.playersStartedWith = players.size();
			for(Player pl : players){
				giveStuff(pl);
				tpToArena(pl);
			}
		}else{
			stopGame();
			broadcastMSG("Not Enough Players!");
		}
	}
	
	public void giveStuff(Player player){
		Main.instance.util.Clear(player);
	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public void tpToArena(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.anvil.arena.x");
		int y = Main.instance.getConfig().getInt("event.anvil.arena.y");
		int z = Main.instance.getConfig().getInt("event.anvil.arena.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.anvil.lobby.x");
		int y = Main.instance.getConfig().getInt("event.anvil.lobby.y");
		int z = Main.instance.getConfig().getInt("event.anvil.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsAnvil.countdown == false && UtilsAnvil.started == false){
		UtilsAnvil.countdown = true;
		Events.eventHosted = Minigames.Anvil;
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
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /Anvil Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /Anvil Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /Anvil Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /Anvil Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /Anvil Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /Anvil Join To Join");}
				}
			}, 20 * i);
		}
	}
	}
	
	
	@SuppressWarnings("deprecation")
	public static void dropAnvils(int percentage){
		World w = Bukkit.getWorld("world");
		int x1 = Main.instance.getConfig().getInt("event.anvil.pos.1.x");
		int y1 = Main.instance.getConfig().getInt("event.anvil.pos.1.y");
		int z1 = Main.instance.getConfig().getInt("event.anvil.pos.1.z");
		
		int x2 = Main.instance.getConfig().getInt("event.anvil.pos.2.x");
		int y2 = Main.instance.getConfig().getInt("event.anvil.pos.2.y");
		int z2 = Main.instance.getConfig().getInt("event.anvil.pos.2.z");
		
		Location loc1 = new Location(w, x1, y1, z1);
		Location loc2 = new Location(w, x2, y2, z2);
		
		for(Location loc : Main.instance.locUtil.getCuboid(loc1, loc2)){
			if(hasChance(percentage)){
				loc.getBlock().setTypeIdAndData(145, (byte) 11, true);
			}
		}
		
	}
	
	  public static boolean hasChance(int chance)
	  {
	    return new Random().nextInt(10000) < chance * 100;
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
	    		
	    		if(UtilsAnvil.countdown == false && UtilsAnvil.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    		
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsAnvil.started == false && UtilsAnvil.countdown == true){
	    		sendMSG(player, "You Have Joined The Anvil Event");
	    		this.addPlayer(player);
	    		}else
	    			if(UtilsAnvil.started == true){
	    			sendMSG(player, "The Anvil Event Already Started");
	    		}else
	    			if(UtilsAnvil.countdown == false){
	    				sendMSG(player, "There Is No Anvil Event Hosted At The Moment");
	    			}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left The Anvil Event");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In The Anvil Event");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /Anvil <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 2){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("arena")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.anvil.arena.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.anvil.arena.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.anvil.arena.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The Anvil Arena Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("lobby")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.anvil.lobby.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.anvil.lobby.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.anvil.lobby.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The Anvil Lobby Lobby Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("1")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.anvil.pos.1.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.anvil.pos.1.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.anvil.pos.1.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The Anvil Pos 1");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("2")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.anvil.pos.2.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.anvil.pos.2.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.anvil.pos.2.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The Anvil Pos 2");
	    			}
	    			}
	    		}
	    		
	    	}
	    return false;
        	}
	
}
