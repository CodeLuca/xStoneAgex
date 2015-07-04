package me.sneaky.kits.gladiator;

import java.util.ArrayList;
import java.util.HashMap;

import me.sneaky.Main;
import me.sneaky.kits.Kits.sKits;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GladiatorListener implements Listener {
	
	  final Main p;
	  

	  public GladiatorListener(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  
	  public HashMap<Player, Player> gladiator = new HashMap<Player, Player>();
	  HashMap<Player, Location> gladiatorLoc = new HashMap<Player, Location>();
	  ArrayList<Location> gladiatorBlocks = new ArrayList<Location>();
	  HashMap<Player, ArrayList<Location>> gladiatorPlayerBlocks = new HashMap<Player, ArrayList<Location>>();
	  
	  HashMap<Player, Location> gladiatorArenaLoc1 = new HashMap<Player, Location>();
	  HashMap<Player, Location> gladiatorArenaLoc2 = new HashMap<Player, Location>();
	  
	  public boolean isInFight(Player player){
		  if(gladiator.get(player) != null){
		  return true;
		  }else{
			  return false;  
		  }
	  }
	  
	  public void createArena(Player player, Player target, int plusY){
		  boolean b = false;
		  if(player.getLocation().getY() + 128 + plusY < 240){
		  double x1 = player.getLocation().getX() + 10;
		  double y1 = player.getLocation().getY() + 98 + plusY;
		  double z1 = player.getLocation().getZ() - 10;
		  
		  double x2 = player.getLocation().getX() - 10;
		  double y2 = player.getLocation().getY() + 90 + plusY;
		  double z2 = player.getLocation().getZ() + 10;
		  
		  double x3 = player.getLocation().getX() + 9;
		  double y3 = player.getLocation().getY() + 97 + plusY;
		  double z3 = player.getLocation().getZ() - 9;
		  
		  double x4 = player.getLocation().getX() - 9;
		  double y4 = player.getLocation().getY() + 91 + plusY;
		  double z4 = player.getLocation().getZ() + 9;
		  
		  double x5 = player.getLocation().getX() + 8;
		  double y5 = player.getLocation().getY() + 90 + plusY + 2;
		  double z5 = player.getLocation().getZ() - 8;
		  
		  double x6 = player.getLocation().getX() - 8;
		  double y6 = player.getLocation().getY() + 90 + plusY + 2;
		  double z6 = player.getLocation().getZ() + 8;
		  
		  Location loc1 = new Location(player.getWorld(), x1, y1, z1);
		  Location loc2 = new Location(player.getWorld(), x2, y2, z2);
		  
		  Location loc3 = new Location(player.getWorld(), x3, y3, z3);
		  Location loc4 = new Location(player.getWorld(), x4, y4, z4);
		  
		  Location loc5 = new Location(player.getWorld(), x5, y5, z5);
		  Location loc6 = new Location(player.getWorld(), x6, y6, z6);
		  
		  for(Location loc : p.locUtil.getCuboid(loc1, loc2)){
			  if(loc.getBlock().getType() != Material.AIR){
				  b = true;
			  }
		  }
		  
		  if(b == true){
			  createArena(player, target, plusY + 10);
		  }else{
			  ArrayList<Location> blocks = new ArrayList<Location>();
		  for(Location loc : p.locUtil.getCuboid(loc1, loc2)){
			  loc.getBlock().setType(Material.GLASS);
			  gladiatorBlocks.add(loc);
			  blocks.add(loc);
		  }
		  
		  for(Location loc : p.locUtil.getCuboid(loc3, loc4)){
			  loc.getBlock().setType(Material.AIR);
		  }
		  
		  gladiatorPlayerBlocks.put(player, blocks);
		  gladiatorPlayerBlocks.put(target, blocks);
		  
		  gladiatorLoc.put(player, player.getLocation());
		  gladiatorLoc.put(target, player.getLocation());
		  
		  gladiatorArenaLoc1.put(player, loc1);
		  gladiatorArenaLoc2.put(player, loc2);
		  
		  gladiatorArenaLoc1.put(target, loc1);
		  gladiatorArenaLoc2.put(target, loc2);
		  
		  player.teleport(loc5);
		  target.teleport(loc6);
		  
		  player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 999));
		  target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 999));
		  
		  gladiator.put(player, target);
		  gladiator.put(target, player);
		  
		  p.util.gladiatored.add(player);
		  p.util.gladiatored.add(target);
		  }
		  }else{
			  player.sendMessage(ChatColor.RED + "Can't Gladiator Here");
		  }
	  }
	  
	    public boolean isInArena(Player player) {
			  Location loc = player.getLocation();
			  
			  double x = loc.getX();
			  double y = loc.getY();
			  double z = loc.getZ();
			  
			  double l1X = gladiatorArenaLoc1.get(player).getX();
			  double l1Y = gladiatorArenaLoc1.get(player).getY();
			  double l1Z = gladiatorArenaLoc1.get(player).getZ();
			  
			  double l2X = gladiatorArenaLoc2.get(player).getX();
			  double l2Y = gladiatorArenaLoc2.get(player).getY();
			  double l2Z = gladiatorArenaLoc2.get(player).getZ();
			  
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
		public void Gladiator(PlayerInteractEntityEvent e){
			Player player = e.getPlayer();
			if(e.getRightClicked() instanceof Player){
				Player target = (Player) e.getRightClicked();
				if(p.util.hasKit(player, sKits.Gladiator)){
				if(player.getItemInHand().getType() == Material.IRON_FENCE){
					if(!p.util.isInSpawn(target)){
					if(gladiator.get(player) == null && gladiator.get(target) == null){
					createArena(player, target, 0);
					}
					}
				}
			}
			}
		}
		
		@EventHandler
		public void Gladiator(PlayerInteractEvent e){
			Player player = e.getPlayer();
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK
					|| e.getAction() == Action.LEFT_CLICK_BLOCK){
					if(gladiator.get(player) != null){
						if(gladiatorBlocks.contains(e.getClickedBlock().getLocation()) && e.getClickedBlock().getType() == Material.GLASS){
							final Block b = e.getClickedBlock();
							b.setType(Material.BEDROCK);
							p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
								public void run(){
									if(b.getLocation().getBlock().getType() == Material.BEDROCK){
										b.setType(Material.GLASS);
									}
								}
							}, 20L);
						}
		}
		}
		}
		
		@EventHandler
		public void Gladiator1(PlayerDeathEvent e) {
			if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
			if(gladiator.get(player) != null){
			Player winner = gladiator.get(player);
			winner.teleport(gladiatorLoc.get(winner));
			
			for(Location loc : gladiatorPlayerBlocks.get(player)){
				loc.getBlock().setType(Material.AIR);
				gladiatorBlocks.remove(loc);
			}
			
			winner.sendMessage(ChatColor.GREEN + "You Won");
			player.sendMessage(ChatColor.RED + "You Lost");
			
			gladiatorPlayerBlocks.remove(player);
			gladiatorPlayerBlocks.remove(winner);
			
			gladiator.remove(player);
			gladiator.remove(winner);
			
			gladiatorLoc.remove(player);
			gladiatorLoc.remove(winner);
			
			  gladiatorArenaLoc1.remove(player);
			  gladiatorArenaLoc2.remove(player);
			  
			  gladiatorArenaLoc1.remove(winner);
			  gladiatorArenaLoc2.remove(winner);
			  
			  p.util.gladiatored.remove(player);
			  p.util.gladiatored.remove(winner);
			  
			  e.getDrops().clear();
			  for(ItemStack item : player.getInventory().getContents()){
		    	   if(item.getType() == Material.MUSHROOM_SOUP || item.getType() == Material.BOWL  
		    			   || item.getType() == Material.RED_MUSHROOM || item.getType() == Material.BROWN_MUSHROOM 
		    			   || item.getType() == Material.STONE_SWORD){
				  player.getWorld().dropItemNaturally(winner.getLocation(), item);
		    	   }
			  }
			
			}
		}		
	}
		
		@EventHandler
		public void Gladiator2(PlayerQuitEvent e) {
			Player player = (Player) e.getPlayer();
			if(gladiator.get(player) != null){
			Player winner = gladiator.get(player);
			winner.teleport(gladiatorLoc.get(winner));
			
			for(Location loc : gladiatorPlayerBlocks.get(player)){
				loc.getBlock().setType(Material.AIR);
				gladiatorBlocks.remove(loc);
			}
			
			winner.sendMessage(ChatColor.GREEN + "You Won");
			
			gladiatorPlayerBlocks.remove(player);
			gladiatorPlayerBlocks.remove(winner);
			
			gladiator.remove(player);
			gladiator.remove(winner);
			
			gladiatorLoc.remove(player);
			gladiatorLoc.remove(winner);
			
			  gladiatorArenaLoc1.remove(player);
			  gladiatorArenaLoc2.remove(player);
			  
			  gladiatorArenaLoc1.remove(winner);
			  gladiatorArenaLoc2.remove(winner);
			  
			  p.util.gladiatored.remove(player);
			  p.util.gladiatored.remove(winner);
			  
			  for(ItemStack item : player.getInventory().getContents()){
		    	   if(item.getType() == Material.MUSHROOM_SOUP || item.getType() == Material.BOWL  
		    			   || item.getType() == Material.RED_MUSHROOM || item.getType() == Material.BROWN_MUSHROOM 
		    			   || item.getType() == Material.STONE_SWORD){
				  player.getWorld().dropItemNaturally(winner.getLocation(), item);
		    	   }
			  }
			
			}
		}		

}
