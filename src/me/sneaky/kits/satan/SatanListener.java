package me.sneaky.kits.satan;

import java.util.ArrayList;
import java.util.HashMap;

import me.sneaky.Main;
import me.sneaky.kits.Kits.sKits;
import me.sneaky.kits.gladiator.GladiatorListener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SatanListener implements Listener {
	
	  final Main p;
	  

	  public SatanListener(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  GladiatorListener gListener = new GladiatorListener(Main.instance);
	  
	  ArrayList<Player> satan = new ArrayList<Player>();
	  HashMap<Player, Location> satanLoc = new HashMap<Player, Location>();
	  HashMap<Player, Integer> satanTask = new HashMap<Player, Integer>();
	  ArrayList<Location> satanBlocks = new ArrayList<Location>();
	  HashMap<Player, ArrayList<Location>> satanPlayerBlocks = new HashMap<Player, ArrayList<Location>>();
	  
	  HashMap<Player, Location> satanArenaLoc1 = new HashMap<Player, Location>();
	  HashMap<Player, Location> satanArenaLoc2 = new HashMap<Player, Location>();
	  
	  public void createArena(final Player player, int plusY){
		  boolean b = false;
		  if(player.getLocation().getY() + 10 + plusY < 240){
		  double x1 = player.getLocation().getX() + 2;
		  double y1 = player.getLocation().getY() + 14 + plusY;
		  double z1 = player.getLocation().getZ() - 2;
		  
		  double x2 = player.getLocation().getX() - 2;
		  double y2 = player.getLocation().getY() + 10 + plusY;
		  double z2 = player.getLocation().getZ() + 2;
		  
		  double x3 = player.getLocation().getX() + 1;
		  double y3 = player.getLocation().getY() + 13 + plusY;
		  double z3 = player.getLocation().getZ() - 1;
		  
		  double x4 = player.getLocation().getX() - 1;
		  double y4 = player.getLocation().getY() + 11 + plusY;
		  double z4 = player.getLocation().getZ() + 1;
		  
		  double x5 = player.getLocation().getX() ;
		  double y5 = player.getLocation().getY() + 10 + plusY + 1;
		  double z5 = player.getLocation().getZ() ;
		  
		  Location loc1 = new Location(player.getWorld(), x1, y1, z1);
		  Location loc2 = new Location(player.getWorld(), x2, y2, z2);
		  
		  Location loc3 = new Location(player.getWorld(), x3, y3, z3);
		  Location loc4 = new Location(player.getWorld(), x4, y4, z4);
		  
		  Location loc5 = new Location(player.getWorld(), x5, y5, z5);
		  
		  for(Location loc : p.locUtil.getCuboid(loc1, loc2)){
			  if(loc.getBlock().getType() != Material.AIR){
				  b = true;
			  }
		  }
		  
		  if(b == true){
			  createArena(player, plusY + 10);
		  }else{
			  ArrayList<Location> blocks = new ArrayList<Location>();
		  for(Location loc : p.locUtil.getCuboid(loc1, loc2)){
			  loc.getBlock().setType(Material.NETHER_BRICK);
			  satanBlocks.add(loc);
			  blocks.add(loc);
		  }
		  
		  for(Location loc : p.locUtil.getCuboid(loc3, loc4)){
			  loc.getBlock().setType(Material.LAVA);
		  }
		  
		  satanPlayerBlocks.put(player, blocks);
		  
		  satanLoc.put(player, player.getLocation());
		  
		  satanArenaLoc1.put(player, loc1);
		  satanArenaLoc2.put(player, loc2);
		  
		  satan.add(player);
		  
		  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 1));
		  
		  player.teleport(loc5);
		  
		  int i;
		  
		  i = p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
			  public void run(){
					for(Location loc : satanPlayerBlocks.get(player)){
						loc.getBlock().setType(Material.AIR);
						satanBlocks.remove(loc);
					}
					
					player.teleport(satanLoc.get(player));
					
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1));
					
					satanPlayerBlocks.remove(player);
					
					satan.remove(player);
					
					satanLoc.remove(player);
					
					satanArenaLoc1.remove(player);
					satanArenaLoc2.remove(player);
					
					satanTask.remove(player);
					
			  }
		  }, 20 * 5L);
		  
		  satanTask.put(player, i);
		  }
		  }else{
			  player.sendMessage(ChatColor.RED + "Can't Satan A Player Here");
		  }
	  }
	  
	    public boolean isInArena(Player player) {
			  Location loc = player.getLocation();
			  
			  double x = loc.getX();
			  double y = loc.getY();
			  double z = loc.getZ();
			  
			  double l1X = satanArenaLoc1.get(player).getX();
			  double l1Y = satanArenaLoc1.get(player).getY();
			  double l1Z = satanArenaLoc1.get(player).getZ();
			  
			  double l2X = satanArenaLoc2.get(player).getX();
			  double l2Y = satanArenaLoc2.get(player).getY();
			  double l2Z = satanArenaLoc2.get(player).getZ();
			  
			  if ((x >= l1X && x <= l2X) || (x <= l1X && x >= l2X)) {
				  if ((y >= l1Y && y <= l2Y) || (y <= l1Y && y >= l2Y)) {
				  if ((z <= l1Z && z >= l2Z) || (z >= l1Z && z <= l2Z)) {
					  return true;
			}
		}
		}
			return false;
	    }
	  
		@EventHandler
		public void Satan(PlayerInteractEntityEvent e){
			Player player = e.getPlayer();
			if(e.getRightClicked() instanceof Player){
				Player target = (Player) e.getRightClicked();
				if(p.util.hasKit(player, sKits.Satan)){
				if(player.getItemInHand().getType() == Material.NETHER_FENCE){
					if(!p.util.isOnCD(player)){
					if(!p.util.isInSpawn(target)){
					if(!satan.contains(player)){
						if(p.util.gladiatored.contains(target)){
							p.chat.sendMessagePlayer(player, "You Can't Satan Someone In A Gladiator Fight");
						}else{
					createArena(target, 0);
					p.util.addCD(player, 40);
						}
					}
					}
					}
				}
			}
			}
		}
		
		@EventHandler
		public void Satan1(PlayerMoveEvent e) {
			Player player = (Player) e.getPlayer();
			if(satan.contains(player)){
				if(!isInArena(player)){
			
			for(Location loc : satanPlayerBlocks.get(player)){
				loc.getBlock().setType(Material.AIR);
				satanBlocks.remove(loc);
			}
			
			satanPlayerBlocks.remove(player);
			
			satan.remove(player);
			
			satanLoc.remove(player);
			
			satanArenaLoc1.remove(player);
			satanArenaLoc2.remove(player);
			
			p.getServer().getScheduler().cancelTask(satanTask.get(player));
			
			satanTask.remove(player);
			
				}
			}
		}
		
		@EventHandler
		public void Satan2(PlayerDeathEvent e) {
			if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
			if(satan.contains(player)){
			
			for(Location loc : satanPlayerBlocks.get(player)){
				loc.getBlock().setType(Material.AIR);
				satanBlocks.remove(loc);
			}
			
			satanPlayerBlocks.remove(player);
			
			satan.remove(player);
			
			satanLoc.remove(player);
			
			satanArenaLoc1.remove(player);
			satanArenaLoc2.remove(player);
			
			p.getServer().getScheduler().cancelTask(satanTask.get(player));
			
			satanTask.remove(player);
			}
		}		
	}
		
		@EventHandler
		public void Satan3(PlayerQuitEvent e) {
			Player player = (Player) e.getPlayer();
			if(satan.contains(player)){
				
			for(Location loc : satanPlayerBlocks.get(player)){
				loc.getBlock().setType(Material.AIR);
				satanBlocks.remove(loc);
			}
			
			satanPlayerBlocks.remove(player);
			
			satan.remove(player);
			
			satanLoc.remove(player);
			
			satanArenaLoc1.remove(player);
			satanArenaLoc2.remove(player);
			
			p.getServer().getScheduler().cancelTask(satanTask.get(player));
			
			satanTask.remove(player);
			
			}
		}		

}
