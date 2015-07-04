package me.sneaky;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import me.sneaky.cmds.Spectate;
import me.sneaky.kits.Kits.sKits;
import me.sneaky.onevsone.Utils1v1.Types1v1;
import me.sneaky.utils.BossBar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

public class Utils {
	
	  final Main p;
	  

	  public Utils(Main instance)
	  {
	    this.p = instance;
	  }
	  
	    public ArrayList<Material> allMaterials = new ArrayList<Material>();
	  
		/**********************1v1********************************/
		public HashMap<Player, Player> preRequest = new HashMap<Player, Player>();
		public HashMap<Player, Player> request = new HashMap<Player, Player>();
		public HashMap<Player, Player> onevsone = new HashMap<Player, Player>();
		public ArrayList<Player> arena = new ArrayList<Player>();
		
		public HashMap<Player, Types1v1> type1v1 = new HashMap<Player, Types1v1>();
		/**********************************************************/
	  
	    public ArrayList<Player> Invis = new ArrayList<Player>();
	    
	    public HashSet<Player> gladiatored = new HashSet<Player>();
	    
	    public HashMap<Player, Integer> KillStreak = new HashMap<Player, Integer>();
	  
		/**********************Kits********************************/
		public HashMap<Player, sKits> Kit = new HashMap<Player, sKits>();
		public HashMap<Player, sKits> CopycatKit = new HashMap<Player, sKits>();
		public HashMap<Player, Integer> CD = new HashMap<Player, Integer>();
		public HashMap<Player, Integer> TaskCD = new HashMap<Player, Integer>();
		/**********************************************************/
		/**********************Event********************************/
		public ArrayList<Player> Players = new ArrayList<Player>();
		/**********************************************************/
		/**********************Clans********************************/
		public HashMap<String, ArrayList<Player>> clanFight = new HashMap<String, ArrayList<Player>>();
		/**********************************************************/
		
		  
		public HashMap<Player, Player> ninja = new HashMap<Player, Player>();
		
		public boolean usePerms = true;
	  
		public void Clear(Player player){
			
			if(Spectate.isInSpectate(player)){
			Spectate.spectateMode(player, false, false);
			}
			  for(Entity ent : player.getWorld().getEntities()){
				  if(ent instanceof Zombie){
				  Zombie zom = (Zombie) ent;
				  if(zom.getCustomName() != null){
				  if(zom.getCustomName().equals(ChatColor.GOLD + player.getName() + "'s" + ChatColor.BLACK + " Minion")){
					  zom.remove();
				  }
				  }
				  }
			  }
			
		    PlayerInventory pInv = player.getInventory();
		    for (PotionEffect potions : player.getActivePotionEffects())
		    {
		      player.removePotionEffect(potions.getType());
		    }
		    
		    for(Player pl : Bukkit.getServer().getOnlinePlayers()){
		    	if(ninja.get(pl) == player){
		    		ninja.remove(pl);
		    	}
		    }
		    
		    preRequest.remove(player);
		    request.remove(player);
		    onevsone.remove(player);
		    arena.remove(player);
		    type1v1.remove(player);




		    Kit.put(player, null);
		    
		    CopycatKit.put(player, null);
		    
		    CD.put(player, 0);
	        player.getInventory().setHelmet(new ItemStack(Material.AIR));
		    player.getInventory().setChestplate(new ItemStack(Material.AIR));
		    player.getInventory().setLeggings(new ItemStack(Material.AIR));
		    player.getInventory().setBoots(new ItemStack(Material.AIR));
		    
		    BossBar.removeBar(player);
		    
		    pInv.clear();
	  }
	  
	  public void giveStuffHG(Player player){
		  ItemStack sword = new ItemStack(Material.STONE_SWORD);
		  sword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		  player.getInventory().setItem(0, sword);
		  giveSoup(player);
	  }
	  
		public void giveSoup(Player player){
			player.getInventory().setItem(14, new ItemStack(Material.BROWN_MUSHROOM, 32));
			player.getInventory().setItem(15, new ItemStack(Material.BOWL, 32));
			player.getInventory().setItem(16, new ItemStack(Material.RED_MUSHROOM, 32));
		    for (int i = 0; i < 34; i++)
		    {
		    	player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.MUSHROOM_SOUP) });
		    }
			}
		
		public void giveSoupNoRC(Player player){
		    for (int i = 0; i < 34; i++)
		    {
		    	player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.MUSHROOM_SOUP) });
		    }
			}
		
		public void giveSoup1Bar(Player player){
		    for (int i = 0; i < 8; i++)
		    {
		    	player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.MUSHROOM_SOUP) });
		    }
			}
		  
		  public void setKit(Player player, sKits kit){
			  Kit.put(player, kit);
		  }
		
		public boolean hasKit(Player player, sKits kit){
			return Kit.get(player) == kit;
		}
		
		public boolean isCopyCat(Player player){
			return CopycatKit.get(player) == sKits.Copycat;
		}
		
	    public boolean isOnCD(Player player){
	    	if(isInSpawn(player)){
	    		p.chat.inSpawnMessage(player);
	    		return true;
	    	}else
	    	if(getTimeCD(player) <= 0){
	    		return false;
	    	}else{
	    		p.chat.cooldownMessage(player);
	    		return true;
	    	}
	    }
	    
	    public int getTimeCD(Player player){
	    	if(CD.get(player) != null){
	    	return CD.get(player);
	    	}else{
	    		return 0;
	    	}
	    }
	    
	    public void addCD(final Player player, final Integer time){
	    	if(TaskCD.get(player) != null){
	    		p.getServer().getScheduler().cancelTask(TaskCD.get(player));
	            }
	    	CD.put(player, time);
	    	BossBar.setBar(player, ChatColor.GRAY + "You Are Still On A Cooldown For " + ChatColor.RED + p.util.getTimeCD(player) + ChatColor.GRAY + " Seconds", ((100 / time) * p.util.getTimeCD(player)));
	    	int task = p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable(){
	    		public void run(){
	    			CD.put(player, getTimeCD(player) - 1);
	    			BossBar.setBar(player, ChatColor.GRAY + "You Are Still On A Cooldown For " + ChatColor.RED + p.util.getTimeCD(player) + ChatColor.GRAY + " Seconds", (100 / time) * p.util.getTimeCD(player) > 0 ? (100 / time) * p.util.getTimeCD(player) : 0);
	    			if(getTimeCD(player) <= 0){
	    				BossBar.removeBar(player);
	    				p.getServer().getScheduler().cancelTask(TaskCD.get(player));
	    				p.chat.cooldownOverMessage(player);
	    				TaskCD.put(player, null);
	    			}
	    		}
	    	}, 0, 20);
	    	
	    	TaskCD.put(player, task);
	    }
	    
	    public void addNoMessageCD(final Player player, Integer time){
	    	if(TaskCD.get(player) != null){
	    		p.getServer().getScheduler().cancelTask(TaskCD.get(player));
	            }
	    	CD.put(player, time);
	    	
	    	int task = p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable(){
	    		public void run(){
	    			CD.put(player, getTimeCD(player) - 1);
	    			
	    			if(getTimeCD(player) == 0){
	    				p.getServer().getScheduler().cancelTask(TaskCD.get(player));
	    				TaskCD.put(player, null);
	    			}
	    		}
	    	}, 0, 20);
	    	
	    	TaskCD.put(player, task);
	    }
		
		public void setColor(ItemStack item, Color color){
			   LeatherArmorMeta itemMeta = (LeatherArmorMeta)item.getItemMeta();
			   itemMeta.setColor(color);
			   item.setItemMeta(itemMeta);
		}
		
		public boolean hasPermission(Player player, String perm){
			if(player.hasPermission("skits." + perm) || player.isOp()){
				return false;
			}else{
				p.chat.noPermMessage(player);
			return true;
			}
		}
		
		public sKits getKit(Player player){
			if(Kit.get(player) != null){
				return Kit.get(player);
			}else{
				return sKits.PvP;
			}
		}
		
	    public boolean isInSpawn(Player player) {
			  Location loc = player.getLocation();
			  
			  double x = loc.getX();
			  double y = loc.getY();
			  double z = loc.getZ();
			  
			  double l1X = p.getConfig().getDouble("prot.pos.1.x");
			  double l1Y = p.getConfig().getDouble("prot.pos.1.y");
			  double l1Z = p.getConfig().getDouble("prot.pos.1.z");
			  
			  double l2X = p.getConfig().getDouble("prot.pos.2.x");
			  double l2Y = p.getConfig().getDouble("prot.pos.2.y");
			  double l2Z = p.getConfig().getDouble("prot.pos.2.z");
			  
			  if ((x >= l1X && x <= l2X) || (x <= l1X && x >= l2X)) {
				  if ((y >= l1Y && y <= l2Y) || (y <= l1Y && y >= l2Y)) {
				  if ((z <= l1Z && z >= l2Z) || (z >= l1Z && z <= l2Z)) {
					  return true;
			}
		}
		}
			return false;
	    }
	    
	    public boolean isInSpawn(Location loc) {			  
			  double x = loc.getX();
			  double y = loc.getY();
			  double z = loc.getZ();
			  
			  double l1X = p.getConfig().getDouble("prot.pos.1.x");
			  double l1Y = p.getConfig().getDouble("prot.pos.1.y");
			  double l1Z = p.getConfig().getDouble("prot.pos.1.z");
			  
			  double l2X = p.getConfig().getDouble("prot.pos.2.x");
			  double l2Y = p.getConfig().getDouble("prot.pos.2.y");
			  double l2Z = p.getConfig().getDouble("prot.pos.2.z");
			  
			  if ((x >= l1X && x <= l2X) || (x <= l1X && x >= l2X)) {
				  if ((y >= l1Y && y <= l2Y) || (y <= l1Y && y >= l2Y)) {
				  if ((z <= l1Z && z >= l2Z) || (z >= l1Z && z <= l2Z)) {
					  return true;
			}
		}
		}
			return false;
	    }
	    
	    @SuppressWarnings("deprecation")
		public boolean isCritical(Player player) {
	    	if((player.getVelocity().getY() + 0.0784000015258789) <= 0 && !player.isOnGround()){
	    	return true;	
	    	}else{
	    		return false;
	    	}
	    }
}
