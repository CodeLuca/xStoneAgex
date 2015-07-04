package me.sneaky.stats.killstreaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.sneaky.Main;
import me.sneaky.events.chickenwars.UtilsChickenWars;
import me.sneaky.events.custom.TimeSecondEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Killstreak implements Listener{
	
	public static ItemStack airstrikeItem(){
		ItemStack item = new ItemStack(Material.LEVER);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Airstrike");
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static ItemStack predatorItem(){
		ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Predator Missle");
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static ItemStack sentryItem(){
		ItemStack item = new ItemStack(Material.DISPENSER);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Sentry Gun");
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static ItemStack mortarItem(){
		ItemStack item = new ItemStack(Material.TORCH);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Mortar");
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static void airstrike(final Player player){
		Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " Activated " + ChatColor.RED + "Airstrike");
		
		BlockIterator bItr = new BlockIterator(player, 15);
		Block block;
		int i = 0;
		int bx, by, bz;
	while (bItr.hasNext()) {
        block = bItr.next();
        bx = block.getX();
        by = block.getY();
        bz = block.getZ();
        
        i = i++;
        
		final Location loc = new Location(player.getWorld(), bx, by, bz);
		
        for(Location l : Main.instance.locUtil.getLine(player.getLocation(), loc)){
        	l.getWorld().playEffect(l, Effect.SMOKE, 1);
        }
        
        Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run() {
				Egg egg = player.getWorld().spawn(loc.add(0, 50, 0), Egg.class);
				egg.setMetadata("airstrike", new FixedMetadataValue(Main.instance, new Random().nextInt(999999)));
				egg.setVelocity(loc.toVector().subtract(egg.getLocation().toVector()).multiply(2));
			}
        },20 * i);
        }
	}
	
	@SuppressWarnings("deprecation")
	public static void predator(final Player player){
		final Block b = player.getTargetBlock(null, 100);
		if(b.getLocation().distance(player.getLocation()) < 50){
			final Location loc = b.getLocation();
			for(int i = 0; i < 3; i++){
		        Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
					public void run() {
			Egg egg = b.getWorld().spawn(loc.add(0, 50, 0), Egg.class);
			egg.setMetadata("predator", new FixedMetadataValue(Main.instance, new Random().nextInt(999999)));
			egg.setVelocity(loc.toVector().subtract(egg.getLocation().toVector()).multiply(5));
			egg.getWorld().playEffect(loc.add(0, 1, 0), Effect.SMOKE, 1);
					}
		        }, (20 / 3) * i);
			}
			player.getInventory().removeItem(predatorItem());
			Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " Activated " + ChatColor.RED + "Predator Missle");
		}else{
			player.sendMessage(ChatColor.RED + "You Must Look At A Block In A 50 Block Radius!");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void sentrygun(final Player player, final Block b){
		  if(b.getLocation().add(0, 1, 0).getBlock().getType() != Material.AIR || 
				  b.getLocation().add(0, 2, 0).getBlock().getType() != Material.AIR  ){
			  player.sendMessage(ChatColor.RED + "There Must Be 2 Block Of Air Above The Sentry Gun");
			  return;
		  }
		  
		  Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " Activated " + ChatColor.RED + "Sentry Gun");
		  player.getInventory().removeItem(sentryItem());
		  b.getLocation().add(0, 1, 0).getBlock().setType(Killstreak.sentryItem().getType());
		  final FallingBlock fb = player.getWorld().spawnFallingBlock(b.getLocation().add(0, 2, 0), Material.AIR, (byte) 1);
		  
		  final int taskID = Main.instance.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			  public void run(){
				  Player t = null;
				  for(Entity ent : fb.getNearbyEntities(20, 20, 20)){
					  if(ent instanceof Player){
						  Player target = (Player) ent;
						  if(t == null){
							  t = target;
						  }else{
							  if(!target.getName().equals(player.getName())){
							  if(target.getLocation().distance(fb.getLocation()) > t.getLocation().distance(fb.getLocation())){
							  t = target;
							  }
							  }
						  }
						  
					  }
				  }
				  if(t != null){
					  Vector vec = t.getLocation().add(0, 1, 0).toVector().subtract(fb.getLocation().toVector()).normalize();
					  Snowball sb = (Snowball) fb.getWorld().spawnEntity(fb.getLocation(), EntityType.SNOWBALL);
				      sb.setVelocity(vec.multiply(4));
				      sb.setMetadata("sentry", new FixedMetadataValue(Main.instance, new Random().nextInt(999999)));
				      fb.getWorld().playEffect(fb.getLocation(), Effect.GHAST_SHOOT, 1);
				  }
			  }
		  }, 0, 20 / 3);
		  
		  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			  public void run(){
				  Main.instance.getServer().getScheduler().cancelTask(taskID);
				  fb.remove();
				  b.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
			  }
		  }, 20 * 10);
	}
	
	public static void attackdogs(Player player){
		 Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " Activated " + ChatColor.RED + "Attack Dogs");
		for(int i = 0; i < 7; i++){
		final Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
		wolf.setOwner(player);
		wolf.setAngry(true);
		wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
		wolf.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 1));
		wolf.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 1));
		  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			  public void run(){
				  if(!wolf.isDead()){
					  wolf.remove();
				  }
			  }
			  }, 20 * 60);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void nuke(final Player player){
		Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Nuke Incoming In 5 Seconds");
		player.playSound(player.getLocation(), Sound.NOTE_PLING, 100, -3);
		
		  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			  public void run(){
				  Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Nuke Incoming In 4 Seconds");
				  player.playSound(player.getLocation(), Sound.NOTE_PLING, 100, -3);
			  }
			  }, 20 * 1L);
		
		  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			  public void run(){
				  Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Nuke Incoming In 3 Seconds");
				  player.playSound(player.getLocation(), Sound.NOTE_PLING, 100, -3);
			  }
			  }, 20 * 2L);
		  
		  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			  public void run(){
				  Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Nuke Incoming In 2 Seconds");
				  player.playSound(player.getLocation(), Sound.NOTE_PLING, 100, -3);
			  }
			  }, 20 * 3L);
		  
		  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			  public void run(){
				  Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Nuke Incoming In 1 Seconds");
				  player.playSound(player.getLocation(), Sound.NOTE_PLING, 100, -3);
			  }
			  }, 20 * 4L);
		
		  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			  public void run(){
		for(Entity ent : player.getNearbyEntities(20, 20, 20)){
			if(ent instanceof Player){
				Player pl = (Player) ent;
				if(!pl.getName().equalsIgnoreCase(player.getName())){
					if(!Main.instance.util.isInSpawn(pl)){
					pl.damage(999, player);
					player.playSound(player.getLocation(), Sound.EXPLODE, 100, -10);
					}
				}
			}
		}
		}
		}, 20 * 5L);
	}
	
	public static ArrayList<Entity> choppers = new ArrayList<Entity>();
	public static HashMap<Player, Entity> playerVehicle = new HashMap<Player, Entity>();
	public static HashMap<Player, Location> playerLoc = new HashMap<Player, Location>();
	
	public static void choppergunner(final Player player){
		Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " Activated " + ChatColor.RED + "Chopper Gunner");
		
		playerLoc.put(player, player.getLocation());
		
		final Chicken chick = (Chicken) player.getWorld().spawnEntity(player.getLocation().add(0, 50, 0), EntityType.CHICKEN);
		chick.setPassenger(player);
		chick.setNoDamageTicks(Integer.MAX_VALUE);
		chick.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 99));
		playerVehicle.put(player, chick);
		choppers.add(chick);
		
		  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			  public void run(){
				  if(!chick.isDead()){
				  chick.remove();
				  }
				  playerVehicle.remove(player);
				  player.teleport(playerLoc.get(player));
				  playerLoc.remove(player);
				  choppers.remove(chick);
				  }
			  }, 20 * 30L);
	}
	
	public static HashMap<Entity, Integer> skullTask = new HashMap<Entity, Integer>();
	
	public static void mortar(Player player, final Player target){
		  Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " Activated " + ChatColor.RED + "Mortar");
		  player.getInventory().removeItem(mortarItem());
		final WitherSkull skull = (WitherSkull) player.getWorld().spawnEntity(player.getLocation().add(0, 20, 0), EntityType.WITHER_SKULL);
		skull.setVelocity(target.getLocation().toVector().subtract(skull.getLocation().toVector()).normalize().multiply(0.2));
		  skull.setDirection(target.getLocation().toVector().subtract(skull.getLocation().toVector()).normalize().multiply(0.2)); 
		skull.setMetadata("moab", new FixedMetadataValue(Main.instance, new Random().nextInt(999999)));
		int TaskID = Main.instance.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			  public void run(){
				  if(skull.isDead()){
				  Main.instance.getServer().getScheduler().cancelTask(skullTask.get(skull));
				  skullTask.remove(skull);
				  }else{
					  skull.setDirection(target.getLocation().toVector().subtract(skull.getLocation().toVector()).normalize().multiply(0.2));  
						skull.setVelocity(target.getLocation().toVector().subtract(skull.getLocation().toVector()).normalize().multiply(0.2));  
				  }
			  }
			  }, 0, (long) 2.5);
		skullTask.put(skull, TaskID);
	}
	
	
	@EventHandler
	public void onUse(PlayerInteractEvent e){
		Player player = e.getPlayer();
		if(player.getItemInHand().equals(airstrikeItem())){
			if(e.getAction() == Action.RIGHT_CLICK_AIR){
				airstrike(player);
				player.getInventory().removeItem(airstrikeItem());
			}
		}
		if(player.getItemInHand().equals(predatorItem())){
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
				predator(player);
			}
		}
		if(player.getItemInHand().equals(sentryItem())){
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
				sentrygun(player, e.getClickedBlock());
			}
		}
	}
	
	@EventHandler
	public void onUse(PlayerInteractEntityEvent e){
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof Player && player.getItemInHand().equals(Killstreak.mortarItem())){
			Player target = (Player) e.getRightClicked();
			mortar(player, target);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onHit(final ProjectileHitEvent e){
		if(e.getEntity() instanceof Egg){
			if(e.getEntity().hasMetadata("airstrike")){
				e.getEntity().getLocation().getWorld().createExplosion(e.getEntity().getLocation().getX(), e.getEntity().getLocation().getY(), e.getEntity().getLocation().getZ(), 3, false, false);
			}
			if(e.getEntity().hasMetadata("predator")){
				TNTPrimed tnt = e.getEntity().getWorld().spawn(e.getEntity().getLocation(), TNTPrimed.class);
				tnt.setFuseTicks(1);
			}
			if(e.getEntity().hasMetadata("chopper")){
				TNTPrimed tnt = e.getEntity().getWorld().spawn(e.getEntity().getLocation(), TNTPrimed.class);
				tnt.setFuseTicks(1);
				tnt.setMetadata(((Player) e.getEntity().getShooter()).getName(), new FixedMetadataValue(Main.instance, new Random().nextInt(999999)));
			}
		}
		if(e.getEntity() instanceof WitherSkull){
			if(e.getEntity().hasMetadata("moab")){
				e.getEntity().getLocation().getWorld().createExplosion(e.getEntity().getLocation().getX(), e.getEntity().getLocation().getY(), e.getEntity().getLocation().getZ(), 3, false, false);
				TNTPrimed tnt = e.getEntity().getWorld().spawn(e.getEntity().getLocation(), TNTPrimed.class);
				tnt.setFuseTicks(20 * 3);		
				for(int i = 0; i < 3; i++){
				  Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
					  public void run(){
						  e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.NOTE_PLING, 100, 1);
					  }
					  }, 20 * i);
				}
				
			}
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Snowball){
			if(e.getDamager().hasMetadata("sentry")){
				Player player = (Player) e.getEntity();
				player.damage(2D);
			}
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageEvent e){
		if(choppers.contains(e.getEntity())){
			e.setCancelled(true);
		}
		if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
		if(playerVehicle.get(player) != null){
			e.setCancelled(true);
		}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void eggThrow(TimeSecondEvent e){
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			if(playerVehicle.get(player) != null){
				Egg egg = player.getWorld().spawn(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getLocation(), Egg.class);
				egg.setMetadata("chopper", new FixedMetadataValue(Main.instance, new Random().nextInt(999999)));
				egg.setShooter(player);
			}
		}
	}
	
	@EventHandler
	public void playerMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
		if(playerVehicle.get(player) != null){
			if(player.getVehicle() != null){
				player.getVehicle().setVelocity(player.getLocation().getDirection().multiply(0.50));
			}else{
					UtilsChickenWars.playerVehicle.get(player).setPassenger(player);
			}
		}
	}

}
