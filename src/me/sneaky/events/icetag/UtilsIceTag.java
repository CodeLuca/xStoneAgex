package me.sneaky.events.icetag;

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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UtilsIceTag implements CommandExecutor {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static HashMap<Player, playerType> typePlayer = new HashMap<Player, playerType>();
	
	public static int tagged = 0;
	
	public static boolean started = false;
	public static boolean countdown = false;
	
	public static int playersStartedWith = 0;
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.GOLD + "Ice Tag " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.GOLD + "Ice Tag " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public enum playerType{
		player,
		tagger;
	}
	
	public void setTypePlayer(Player player, playerType type){
		
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
		typePlayer.clear();
		UtilsIceTag.started = false;
		UtilsIceTag.countdown = false;
		Events.eventHosted = null;
		Events.newEvent();
		UtilsIceTag.tagged = 0;
	}
	
	public void startGame(){
		if(players.size() >= 2){
			broadcastMSG("Started!");
			UtilsIceTag.started = true;
			UtilsIceTag.playersStartedWith = players.size();
			for(Player pl : players){
				giveStuffPlayer(pl);
				tpToArenaPlayer(pl);
			}
			int random = new Random().nextInt(players.size());
			Player player = players.get(random);
			giveStuffTagger(player);
			tpToArenaTagger(player);
			broadcastMSG(player.getName() + " Is the tagger");
		}else{
			stopGame();
			broadcastMSG("Not Enough Players!");
		}
	}
	
	public void giveStuffPlayer(Player player){
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Confusion Stick");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Right Click To Shoot A Snowball");
		lore.add("To Confuse A Tagger");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
	}
	
	public void giveStuffTagger(Player player){
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Tagger Stick");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Right Click To Shoot A Snowball");
		lore.add("To Tag A Player");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public void tpToArenaPlayer(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.icetag.player.x");
		int y = Main.instance.getConfig().getInt("event.icetag.player.y");
		int z = Main.instance.getConfig().getInt("event.icetag.player.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToArenaTagger(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.icetag.tagger.x");
		int y = Main.instance.getConfig().getInt("event.icetag.tagger.y");
		int z = Main.instance.getConfig().getInt("event.icetag.tagger.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.icetag.lobby.x");
		int y = Main.instance.getConfig().getInt("event.icetag.lobby.y");
		int z = Main.instance.getConfig().getInt("event.icetag.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsIceTag.countdown == false && UtilsIceTag.started == false){
		UtilsIceTag.countdown = true;
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
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /IceTag Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /IceTag Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /IceTag Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /IceTag Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /IceTag Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /IceTag Join To Join");}
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
	    		if(UtilsIceTag.countdown == false && UtilsIceTag.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsIceTag.started == false && UtilsIceTag.countdown == true){
	    		sendMSG(player, "You Have Joined The IceTag Tournament");
	    		this.addPlayer(player);
	    		}else
	    			if(UtilsIceTag.started == true){
	    			sendMSG(player, "The IceTag Tournament Already Started");
	    		}else
	    			if(UtilsIceTag.countdown == false){
	    				sendMSG(player, "There Is No IceTag Tournament Hosted At The Moment");
	    			}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left The IceTag Tournament");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In The IceTag Tournament");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /IceTag <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 2){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("player")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.icetag.player.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.icetag.player.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.icetag.player.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The IceTag Arena Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("tagger")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.icetag.tagger.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.icetag.tagger.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.icetag.tagger.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The IceTag Arena Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("lobby")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.icetag.lobby.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.icetag.lobby.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.icetag.lobby.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The IceTag Lobby Spawn Point");
	    			}
	    			}
	    		}
	    		
	    	}
	    return false;
        	}
	
}
