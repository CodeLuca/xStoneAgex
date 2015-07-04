package me.sneaky.events.tntrun;

import java.util.ArrayList;

import me.sneaky.Main;
import me.sneaky.events.Events;
import me.sneaky.events.Events.Minigames;
import me.sneaky.kits.Kits.sKits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilsTNTRun implements CommandExecutor {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<BlockState> blocks = new ArrayList<BlockState>();
	
	public static boolean started = false;
	public static boolean countdown = false;
	
	public static boolean removeBlock = false;
	
	public static int playersStartedWith = 0;
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "TNTRun " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.DARK_PURPLE + "TNTRun " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
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
		UtilsTNTRun.started = false;
		UtilsTNTRun.countdown = false;
		UtilsTNTRun.removeBlock = false;
		Events.eventHosted = null;
		for(BlockState bState : UtilsTNTRun.blocks){
			bState.setType(bState.getType());
			bState.getBlock().getLocation().getBlock().setType(bState.getType());
			bState.update();
		}
		UtilsTNTRun.blocks.clear();
		Events.newEvent();
	}
	
	public void startGame(){
		if(players.size() >= 2){
			broadcastMSG("Started!");
			UtilsTNTRun.started = true;
			UtilsTNTRun.playersStartedWith = players.size();
			for(Player pl : players){
				giveStuff(pl);
				tpToArena(pl);
				pl.setNoDamageTicks(20 * 10);
				sendMSG(pl, "You Have 10 Seconds Before The Blocks Disappear");
			}
			Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run() {
			UtilsTNTRun.removeBlock = true;
				}
			}, 20 * 10);
		}else{
			stopGame();
			broadcastMSG("Not Enough Players!");
		}
	}
	
	public void giveStuff(Player player){
		Main.instance.KitsClass.giveKit(player, sKits.PvP);
	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public void tpToArena(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.tntrun.arena.x");
		int y = Main.instance.getConfig().getInt("event.tntrun.arena.y");
		int z = Main.instance.getConfig().getInt("event.tntrun.arena.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.tntrun.lobby.x");
		int y = Main.instance.getConfig().getInt("event.tntrun.lobby.y");
		int z = Main.instance.getConfig().getInt("event.tntrun.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsTNTRun.countdown == false && UtilsTNTRun.started == false){
		UtilsTNTRun.countdown = true;
		Events.eventHosted = Minigames.TNTRun;
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
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /TNTRun Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /TNTRun Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /TNTRun Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /TNTRun Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /TNTRun Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /TNTRun Join To Join");}
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
	    		if(UtilsTNTRun.countdown == false && UtilsTNTRun.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsTNTRun.started == false && UtilsTNTRun.countdown == true){
	    		sendMSG(player, "You Have Joined The TNTRun Tournament");
	    		this.addPlayer(player);
	    		}else
	    			if(UtilsTNTRun.started == true){
	    			sendMSG(player, "The TNTRun Tournament Already Started");
	    		}else
	    			if(UtilsTNTRun.countdown == false){
	    				sendMSG(player, "There Is No TNTRun Tournament Hosted At The Moment");
	    			}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left The TNTRun Tournament");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In The TNTRun Tournament");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /TNTRun <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 2){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("arena")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.tntrun.arena.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.tntrun.arena.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.tntrun.arena.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The TNTRun Arena Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("lobby")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.tntrun.lobby.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.tntrun.lobby.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.tntrun.lobby.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The TNTRun Lobby Spawn Point");
	    			}
	    			}
	    		}
	    		
	    	}
	    return false;
        	}
	
}
