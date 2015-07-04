package me.sneaky.events.competitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.sneaky.Main;
import me.sneaky.events.Events;
import me.sneaky.events.Events.Minigames;
import me.sneaky.events.competitive.WeaponsCompetitve.guns;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UtilsCompetitve implements CommandExecutor {
	
	WeaponsCompetitve weapons = new WeaponsCompetitve();
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public static ArrayList<Player> T = new ArrayList<Player>();
	public static ArrayList<Player> CT = new ArrayList<Player>();
	
	public static Item tntBomb = null;
	
	public static HashMap<Player, Integer> playerMoney = new HashMap<Player, Integer>();
	
	public static HashMap<Player, teams> playerTeam = new HashMap<Player, teams>();
	public static HashMap<Player, guns> playerGun1 = new HashMap<Player, guns>();
	public static HashMap<Player, guns> playerGun2 = new HashMap<Player, guns>();
	public static HashMap<Player, Boolean> playerArmor = new HashMap<Player, Boolean>();
	
	public static int ctWins = 0;
	public static int tWins = 0;
	
	public static int ctPlayersLeft = 0;
	public static int tPlayersLeft = 0;
	
	public static int rounds = 0;
	
	public static boolean planted = false;
	
	public static boolean started = false;
	public static boolean countdown = false;
	
	
	public enum teams{
		TERRORIST,
		COUNTER_TERRORIST;
	}
	
	public void addMoney(Player player, int amount){
		if(playerMoney.get(player) != null){
			playerMoney.put(player, playerMoney.get(player) + amount);
		}else{
			playerMoney.put(player, amount);
		}
	}
	
	public void removeMoney(Player player, int amount){
		if(playerMoney.get(player) != null){
			playerMoney.put(player, playerMoney.get(player) - amount);
		}
	}
	
	public boolean hasMoney(Player player, int amount){
		if(playerMoney.get(player) != null){
			if(amount <= playerMoney.get(player)){
				return true;
			}
		}
		return false;
	}
	
	public void stopGame(){
		if(tntBomb != null){
		if(!tntBomb.isDead()){
			tntBomb.remove();
		}
		}
		players.clear();
		UtilsCompetitve.started = false;
		UtilsCompetitve.countdown = false;
		Events.eventHosted = null;
		playerTeam.clear();
		playerGun2.clear();
		playerGun1.clear();
		playerArmor.clear();
		T.clear();
		CT.clear();
		ctWins = 0;
		tWins = 0;
		ctPlayersLeft = 0;
		tPlayersLeft = 0;
		rounds = 0;
		
		planted = false;
		tntBomb = null;
	}
	
	public void broadcastMSG(String msg){
		Bukkit.broadcastMessage(ChatColor.GOLD + "Competitve " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}
	
	public void sendMSG(Player player, String msg){
		player.sendMessage(ChatColor.GOLD + "Competitve " + ChatColor.BLACK + "> " + ChatColor.GRAY + msg);
	}

	public void newRound(teams teamWon){
		if(teamWon != null){
		if(teamWon == teams.TERRORIST){
			tWins++;
			broadcastMSG("Terrorists Won!");
			broadcastMSG("The score is now");
			broadcastMSG("Terrorists = " + tWins + " || Counter Terrorists = " + ctWins);
		}
		if(teamWon == teams.COUNTER_TERRORIST){
			ctWins++;
			broadcastMSG("Counter Terrorists Won!");
			broadcastMSG("The score is now");
			broadcastMSG("Terrorists = " + tWins + " || Counter Terrorists = " + ctWins);
		}
		}
		if(UtilsCompetitve.rounds == 6){
			for(Player pl : T){
				this.tpToArenaT(pl);
				tPlayersLeft++;
				playerArmor.put(pl, false);
			}
			for(Player pl : CT){
				this.tpToArenaCT(pl);
				ctPlayersLeft++;
				playerArmor.put(pl, false);
			}
		}
		rounds++;
		tPlayersLeft = 0;
		ctPlayersLeft = 0;
		if(tntBomb != null){
		if(!tntBomb.isDead()){
			tntBomb.remove();
		}
		}
		planted = false;
		tntBomb = null;
		TimerCompetitive.bombTime = 45; 
		TimerCompetitive.time = 135; 
		if(UtilsCompetitve.ctWins < 6 && UtilsCompetitve.tWins < 6){
			for(Player pl : T){
				Main.instance.util.Clear(pl);
				pl.setFoodLevel(16);
				pl.setHealth(20D);
				this.tpToArenaT(pl);
				tPlayersLeft++;
				pl.getInventory().setItem(2, new ItemStack(Material.STONE_SWORD));
				if(playerGun1.get(pl) == null){
					playerGun1.put(pl, guns.GLOCK);
					pl.getInventory().setItem(1, weapons.getWeapon(guns.GLOCK));
				}else{
					pl.getInventory().setItem(1, weapons.getWeapon(playerGun1.get(pl)));
				}
				if(playerGun2.get(pl) != null){
					pl.getInventory().setItem(0, weapons.getWeapon(playerGun2.get(pl)));
				}
				Main.instance.CompGUI.openInv(pl);
			}
			for(Player pl : CT){
				Main.instance.util.Clear(pl);
				pl.setFoodLevel(16);
				pl.setHealth(20D);
				this.tpToArenaCT(pl);
				ctPlayersLeft++;
				pl.getInventory().setItem(2, new ItemStack(Material.STONE_SWORD));
				if(playerGun1.get(pl) == null){
					playerGun1.put(pl, guns.USP);
					pl.getInventory().setItem(1, weapons.getWeapon(guns.USP));
				}else{
					pl.getInventory().setItem(1, weapons.getWeapon(playerGun1.get(pl)));
				}
				if(playerGun2.get(pl) != null){
					pl.getInventory().setItem(0, weapons.getWeapon(playerGun2.get(pl)));
				}
				Main.instance.CompGUI.openInv(pl);
			}
			
			int random = new Random().nextInt(T.size());
			Player player = T.get(random);
			player.getInventory().setItem(8, weapons.bomb());
			broadcastMSG(player.getName() + " has the bomb!");
		}else{
			this.stopGame();
		}
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
	
	public void addPlayerToT(Player player){
		if(!T.contains(player)){
			playerTeam.put(player, teams.TERRORIST);
		T.add(player);
		}
	}
	
	
	public void addPlayerToCT(Player player){
		if(!CT.contains(player)){
			playerTeam.put(player, teams.COUNTER_TERRORIST);
		CT.add(player);
		}
	}
	
	public void removePlayerToT(Player player){
		if(T.contains(player)){
		T.remove(player);
		playerTeam.remove(player);
		}
	}
	
	
	public void removePlayerToCT(Player player){
		if(CT.contains(player)){
		CT.remove(player);
		playerTeam.remove(player);
		}
	}
	
	public void startGame(){
		if(players.size() >= 2){
			broadcastMSG("Started!");
			UtilsCompetitve.started = true;
			for(Player pl : players){
				this.addMoney(pl, 800);
				if(new Random().nextInt(2) == 1){
					if(T.size() < 8 && T.size() <= CT.size()){
					addPlayerToT(pl);
					}else{
						addPlayerToCT(pl);
					}
				}else{
					if(CT.size() < 8 && CT.size() <= T.size()){
					addPlayerToCT(pl);
					}else{
						addPlayerToT(pl);
					}
				}
			}
			newRound(null);
		}else{
			stopGame();
			broadcastMSG("Not Enough Players!");
		}
	}
	
	public void tpToSpawn(Player player){
		player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	}
	
	public void tpToArenaT(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.competitve.arena.t.x");
		int y = Main.instance.getConfig().getInt("event.competitve.arena.t.y");
		int z = Main.instance.getConfig().getInt("event.competitve.arena.t.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToArenaCT(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.competitve.arena.ct.x");
		int y = Main.instance.getConfig().getInt("event.competitve.arena.ct.y");
		int z = Main.instance.getConfig().getInt("event.competitve.arena.ct.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void tpToLobby(Player player){
		World w = player.getWorld();
		int x = Main.instance.getConfig().getInt("event.competitve.lobby.x");
		int y = Main.instance.getConfig().getInt("event.competitve.lobby.y");
		int z = Main.instance.getConfig().getInt("event.competitve.lobby.z");
		
		Location loc = new Location(w, x, y, z);
		player.teleport(loc);
	}
	
	public void countDown(){
		if(UtilsCompetitve.countdown == false && UtilsCompetitve.started == false){
		UtilsCompetitve.countdown = true;
		Events.eventHosted = Minigames.HG;
		players.clear();
		broadcastMSG("Starting In 1 Minute");
		Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run() {
				startGame();
			}
			}, 20 * 10);
		for(int i = 0; i < 60; i ++){
			final int c = i;
			Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
				public void run() {
					int time = 60 - c;
					if(time == 50){broadcastMSG("Starting In 50 Seconds");broadcastMSG("Do /Competitve Join To Join");}
					if(time == 40){broadcastMSG("Starting In 40 Seconds");broadcastMSG("Do /Competitve Join To Join");}
					if(time == 30){broadcastMSG("Starting In 30 Seconds");broadcastMSG("Do /Competitve Join To Join");}
					if(time == 20){broadcastMSG("Starting In 20 Seconds");broadcastMSG("Do /Competitve Join To Join");}
					if(time == 10){broadcastMSG("Starting In 10 Seconds");broadcastMSG("Do /Competitve Join To Join");}
					if(time == 5){broadcastMSG("Starting In 5 Seconds");broadcastMSG("Do /Competitve Join To Join");}
				}
			}, 20 * i);
		}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
	    final Player player = (Player)sender;
	    if(args.length == 1){
	    	if(args[0].equalsIgnoreCase("host")){
	    		if(UtilsCompetitve.countdown == false && UtilsCompetitve.started == false){
	    			this.countDown();
	    		}else{
	    			sendMSG(player, "Already Hosted");
	    		}
	    }else
	    	if(args[0].equalsIgnoreCase("join")){
	    		if(UtilsCompetitve.started == false && UtilsCompetitve.countdown == true){
	    			if(players.size() != 16){
	    		sendMSG(player, "You Have Joined The Competitve Tournament");
	    		this.addPlayer(player);
	    			}else{
	    				sendMSG(player, "Competitive is full!");
	    			}
	    		}else
	    			if(UtilsCompetitve.started == true){
	    			sendMSG(player, "The Competitve Tournament Already Started");
	    		}else
	    			if(UtilsCompetitve.countdown == false){
	    				sendMSG(player, "There Is No Competitve Tournament Hosted At The Moment");
	    			}
	    	}
	    	else
		    	if(args[0].equalsIgnoreCase("leave")){
		    		if(players.contains(player)){
		    		this.removePlayer(player);
		    		sendMSG(player, "You Have Left The Competitve Tournament");
		    		this.tpToSpawn(player);
		    		}else{
		    			sendMSG(player, "You Are Not In The Competitve Tournament");
		    		}
		    	}else{
		    		sendMSG(player, "Usage /Competitve <Join/Leave>");
		    	}
	    	
	    }else
	    	if(args.length == 2){
	    		if(args[0].equalsIgnoreCase("pos")){
	    			if(args[1].equalsIgnoreCase("t")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.competitve.arena.t.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.competitve.arena.t.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.competitve.arena.t.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The Competitve Arena T Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("ct")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.competitve.arena.ct.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.competitve.arena.ct.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.competitve.arena.ct.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The Competitve Arena CT Spawn Point");
	    			}
	    			}
	    			if(args[1].equalsIgnoreCase("lobby")){
	    				if(player.isOp()){
	    			Main.instance.getConfig().set("event.competitve.lobby.x", player.getLocation().getX());
	    			Main.instance.getConfig().set("event.competitve.lobby.y", player.getLocation().getY());
	    			Main.instance.getConfig().set("event.competitve.lobby.z", player.getLocation().getZ());
	    			Main.instance.saveConfig();
	    			sendMSG(player, "You Have Set The Competitve Lobby Spawn Point");
	    			}
	    			}
	    		}
	    		
	    	}
	    return false;
        	}
	
}
