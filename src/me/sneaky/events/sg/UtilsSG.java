package me.sneaky.events.sg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.sneaky.Main;
import me.sneaky.events.Events;
import me.sneaky.events.Events.Minigames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilsSG implements CommandExecutor {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public static boolean started = false;
	public static boolean countdown = false;
	public static boolean noMove = false;
	
	public static int playersStartedWith = 0;
	
	public static HashMap<Player, Location> warpLoc = new HashMap<Player, Location>();
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.GOLD + "SG " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.GOLD + "SG " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void addPlayer(Player player){
		if(!players.contains(player)){
		players.add(player);
		tpToLobby(player);
		Main.instance.util.Clear(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 100));
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
		UtilsSG.started = false;
		UtilsSG.countdown = false;
		Events.eventHosted = null;
		Events.newEvent();
	}
	
	public void startGame(){	
		if(players.size() >= 4){
			broadcastMSG("Started!");
			UtilsSG.started = true;
			UtilsSG.playersStartedWith = players.size();
			int i = 0;
			for(Player pl : players){
				i++;
				giveStuff(pl);
				tpToArena(pl, i);
				pl.setNoDamageTicks(20 * 10);
				warpLoc.put(pl, pl.getLocation());
			}
			UtilsSG.noMove = true;
			
			broadcastMSG("You can move in 5 seconds");
			Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run() {
					broadcastMSG("You can move in 4 seconds");
				}
				}, 20);
			Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run() {
					broadcastMSG("You can move in 3 seconds");
				}
				}, 40);
			Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run() {
					broadcastMSG("You can move in 2 seconds");
				}
				}, 60);
			Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run() {
					broadcastMSG("You can move in 1 seconds");
				}
				}, 80);
			Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run() {
					broadcastMSG("You can now move");
					UtilsSG.noMove = false;
				}
				}, 100);
			
			
			
			fillChests();
			
		}else{
			stopGame();
			broadcastMSG("Not Enough Players!");
		}
	}
	
	public void fillChests(){
		for(Location loc : Main.instance.locUtil.getCuboid(mapPos1(), mapPos2())){
			if(loc.getBlock().getType() == Material.CHEST){
                Chest chest = (Chest) loc.getBlock().getState();
                chest.getInventory().clear();
                for(ItemsSG items : ItemsSG.values()){
                	if(new Random().nextInt(10000) < items.getChance() * 100.0D){
                		chest.getInventory().addItem(new ItemStack(items.getMat()));
                	}
                }
                chest.update();  
			}
		}
	}
	
	public void giveStuff(Player player){
		Main.instance.util.Clear(player);
	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public Location mapPos1(){
		World w = Bukkit.getWorld("world");
		if(w == null){
			w = Bukkit.getWorld("Stone");
		}
		int x = Main.instance.getConfig().getInt("event.sg.pos.1.x");
		int y = Main.instance.getConfig().getInt("event.sg.pos.1.y");
		int z = Main.instance.getConfig().getInt("event.sg.pos.1.z");
		
		Location loc = new Location(w, x, y, z);
		return loc;
	}
	
	public Location mapPos2(){
		World w = Bukkit.getWorld("world");
		if(w == null){
			w = Bukkit.getWorld("Stone");
		}
		int x = Main.instance.getConfig().getInt("event.sg.pos.2.x");
		int y = Main.instance.getConfig().getInt("event.sg.pos.2.y");
		int z = Main.instance.getConfig().getInt("event.sg.pos.2.z");
		
		Location loc = new Location(w, x, y, z);
		return loc;
	}
	
	public void tpToArena(Player player, int spawn){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.sg.arena."+spawn+".x");
		int y = Main.instance.getConfig().getInt("event.sg.arena."+spawn+".y");
		int z = Main.instance.getConfig().getInt("event.sg.arena."+spawn+".z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.sg.lobby.x");
		int y = Main.instance.getConfig().getInt("event.sg.lobby.y");
		int z = Main.instance.getConfig().getInt("event.sg.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsSG.countdown == false && UtilsSG.started == false){
		UtilsSG.countdown = true;
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
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /SG Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /SG Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /SG Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /SG Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /SG Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /SG Join To Join");}
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
	    		if(UtilsSG.countdown == false && UtilsSG.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsSG.started == false && UtilsSG.countdown == true){
	    			if(players.size() < 12){
	    		sendMSG(player, "You Have Joined The SG Tournament");
	    		this.addPlayer(player);
	    			}else{
	    				sendMSG(player, "SG is full!");
	    			}
	    		}else
	    			if(UtilsSG.started == true){
	    			sendMSG(player, "The SG Tournament Already Started");
	    		}else
	    			if(UtilsSG.countdown == false){
	    				sendMSG(player, "There Is No SG Tournament Hosted At The Moment");
	    			}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left The SG Tournament");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In The SG Tournament");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /SG <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 3){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("arena")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.sg.arena." + args[2] + ".x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.sg.arena." + args[2] + ".y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.sg.arena." + args[2] + ".z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The SG Arena "+ args[2] +"Point");
	    			}
	    			}
	    		}
	    	}else
	    		if(args.length == 2){
	    			if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("lobby")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.sg.lobby.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.sg.lobby.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.sg.lobby.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The SG Lobby Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("1")){
		    			Main.instance.getConfig().set("event.sg.pos.1.x", player.getLocation().getX());
		    			Main.instance.getConfig().set("event.sg.pos.1.y", player.getLocation().getY());
		    			Main.instance.getConfig().set("event.sg.pos.1.z", player.getLocation().getZ());
		    			Main.instance.saveConfig();
		    			sendMSG(player, "You Have Set The SG Map Pos 1 Point");
	    			}
	    			if(args[1].equalsIgnoreCase("2")){
		    			Main.instance.getConfig().set("event.sg.pos.2.x", player.getLocation().getX());
		    			Main.instance.getConfig().set("event.sg.pos.2.y", player.getLocation().getY());
		    			Main.instance.getConfig().set("event.sg.pos.2.z", player.getLocation().getZ());
		    			Main.instance.saveConfig();
		    			sendMSG(player, "You Have Set The SG Map Pos 1 Point");
	    			}
	    			}
	    		}
	    return false;
        	}
	
}
