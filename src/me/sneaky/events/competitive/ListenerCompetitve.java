package me.sneaky.events.competitive;

import java.util.ArrayList;
import java.util.HashMap;

import me.sneaky.Main;
import me.sneaky.events.competitive.UtilsCompetitve.teams;
import me.sneaky.events.competitive.WeaponsCompetitve.gunType;
import me.sneaky.events.competitive.WeaponsCompetitve.guns;
import me.sneaky.events.competitive.WeaponsCompetitve.hitType;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ListenerCompetitve implements Listener {
	
	  final Main p;
	  

	  public ListenerCompetitve(Main instance)
	  {
	    this.p = instance;
	  }

	UtilsCompetitve hg = new UtilsCompetitve();

	WeaponsCompetitve weapons = new WeaponsCompetitve();
	
	GuiCompetitive gui = new GuiCompetitive();
	
	
	public HashMap<Player, Integer> defuseTime = new HashMap<Player, Integer>();
	public HashMap<Player, Integer> defuseTask = new HashMap<Player, Integer>();
	
	public HashMap<Entity, guns> gunBullet = new HashMap<Entity, guns>();
	public HashMap<Entity, Location> startLocBullet = new HashMap<Entity, Location>();
	
	ArrayList<Player> CD = new ArrayList<Player>();
	
	  @SuppressWarnings("deprecation")
	@EventHandler
	  public void BombPlant(PlayerItemConsumeEvent event) {
	    final Player player = event.getPlayer();
		if(event.getItem().equals(weapons.bomb())){
			if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK){
				 final Item tnt = (Item) event.getPlayer().getWorld().dropItem(player.getLocation(), new ItemStack(Material.TNT));
				tnt.setVelocity(new Vector(0, 0, 0));
				 tnt.setPickupDelay(99999999);
				UtilsCompetitve.planted = true;
				UtilsCompetitve.tntBomb = tnt;
				hg.broadcastMSG("The bomb has been planted!");
				final Location loc = player.getLocation();
				Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
					public void run() {
						tnt.teleport(loc);
					}		
				}, 20);
			}else{
				event.setItem(weapons.bomb());
				player.updateInventory();
				Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
					public void run() {
					player.setItemInHand(weapons.bomb());	
					player.updateInventory();
					}		
				}, 1);
			}
		}
	  }
	  
	  
	  public void defuseTask(final Player player, final Item tnt){	  
		  if(defuseTime.get(player) == null){
		  player.getWorld().playSound(player.getLocation(), Sound.ANVIL_BREAK, 25, 1);
		  int taskID = p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable(){
			public void run() {
				if(player.isSneaking() && player.isBlocking()){
					defuseTime.put(player, defuseTime.get(player) + 1);
					if(defuseTime.get(player) == 100){
					tnt.remove();
					hg.newRound(teams.COUNTER_TERRORIST);
					hg.broadcastMSG("The bomb has been defused!");
					}
				}else{
					if(defuseTask.get(player) != null){
					p.getServer().getScheduler().cancelTask(defuseTask.get(player));
					}
					defuseTask.remove(player);
					defuseTime.remove(player);
				}
			}	  
		  }, 0, 1);
		  defuseTask.put(player, taskID);
		  defuseTime.put(player, 0);
		  }
	  }
	  
	  
	  @EventHandler
	  public void playerDefuse(PlayerToggleSneakEvent e){
		  Player player = e.getPlayer();
			if(UtilsCompetitve.players.contains(player)){
				if(UtilsCompetitve.CT.contains(player)){
					if(player.isBlocking()){
					for(Entity ent : player.getNearbyEntities(2, 1, 2)){
						if(ent instanceof Item){
							Item tnt = (Item) ent;
							defuseTask(player, tnt);
						}
					}
				}
				}
			}
	  }
	
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerDeath(PlayerDeathEvent e){
		final Player player = e.getEntity();
		if(UtilsCompetitve.players.contains(player)){
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			for(ItemStack item : e.getDrops()){
				if(item.equals(weapons.bomb())){
					items.add(item);
				}
			}
			e.getDrops().clear();
			for(ItemStack item : items){
				player.getWorld().dropItemNaturally(player.getLocation(), item);
			}
			
			if(player.getKiller() != null){
				if(player.getKiller() instanceof Projectile){
					Projectile prot = (Projectile) player.getKiller();
					if(prot.getShooter() instanceof Player){
					Player ent = (Player) prot.getShooter();
					hg.addMoney(ent, gunBullet.get(ent).getKillPrice());
					}
				}
			}
			
					if(UtilsCompetitve.CT.contains(player)){
						UtilsCompetitve.ctPlayersLeft--;
					if(UtilsCompetitve.ctPlayersLeft == 0){
						hg.newRound(teams.TERRORIST);
					}else{
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 100));
						hg.broadcastMSG(player.getName() + " Died");
						player.setHealth(20D);
						hg.tpToLobby(player);
					}
					}
					if(UtilsCompetitve.T.contains(player)){
						UtilsCompetitve.tPlayersLeft--;
					if(UtilsCompetitve.tPlayersLeft == 0){
						if(UtilsCompetitve.planted == false){
						hg.newRound(teams.COUNTER_TERRORIST);
						}else{
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 100));
							hg.broadcastMSG(player.getName() + " Died");
							player.setHealth(20D);
							hg.tpToLobby(player);
						}
					}else{
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 100));
						hg.broadcastMSG(player.getName() + " Died");
						player.setHealth(20D);
						hg.tpToLobby(player);
					}
					}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void shoot(PlayerInteractEvent e){
		final Player player = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR && player.getItemInHand().getType().toString().contains("HOE")){
				if(!CD.contains(player)){
				if(weapons.getGunInHand(player).getType() == gunType.SNIPER){
					Projectile bullet = player.launchProjectile(Arrow.class);
					bullet.setVelocity(bullet.getVelocity().multiply(10));
					bullet.setShooter(player);
					gunBullet.put(bullet, weapons.getGunInHand(player));
					startLocBullet.put(bullet, player.getEyeLocation());
					CD.add(player);
					Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run() {
							CD.remove(player);
						}
					}, 20 / weapons.getGunInHand(player).getFirerate()); 
				}else{
					Projectile bullet = player.launchProjectile(Snowball.class);
					bullet.setVelocity(bullet.getVelocity().multiply(10));
					bullet.setShooter(player);
					gunBullet.put(bullet, weapons.getGunInHand(player));
					startLocBullet.put(bullet, player.getEyeLocation());
					CD.add(player);
					Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run() {
							CD.remove(player);
						}
					}, 20 / weapons.getGunInHand(player).getFirerate()); 
				}
			}
			}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void DMGEvent(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Arrow){
			Player player = (Player) e.getEntity();
			Arrow bullet = (Arrow) e.getDamager();
			if(bullet.getShooter() != null){
				if(bullet.getShooter() instanceof Player){
			Player shooter = (Player) bullet.getShooter();
			if(shooter instanceof Player){
			int damage = 0;
			if(UtilsCompetitve.players.contains(player)){
				e.setCancelled(true);
				//if((!UtilsCompetitve.T.contains(player) && UtilsCompetitve.T.contains(shooter) || UtilsCompetitve.CT.contains(player) && UtilsCompetitve.CT.contains(shooter))){
				int y = (int) bullet.getLocation().getY();
	            int shotY = (int) player.getLocation().getY();
	            int headshot = y - shotY;
	            guns gun = gunBullet.get(bullet);
	        	if (headshot > 1.3D) {
	        		damage = weapons.getDamage(gun, hitType.HEAD, (int) startLocBullet.get(bullet).distance(player.getLocation()), UtilsCompetitve.playerArmor.get(player));
	        	}else
		        	if (headshot > 1D) {
		        		damage = weapons.getDamage(gun, hitType.CHEST_ARM, (int) startLocBullet.get(bullet).distance(player.getLocation()), UtilsCompetitve.playerArmor.get(player));
		        	}else
			        	if (headshot > 0.50D) {
			        		damage = weapons.getDamage(gun, hitType.STOMACH, (int) startLocBullet.get(bullet).distance(player.getLocation()), UtilsCompetitve.playerArmor.get(player));
			        	}else
	        	if (headshot > 0D) {
	        		damage = weapons.getDamage(gun, hitType.LEG, (int) startLocBullet.get(bullet).distance(player.getLocation()), UtilsCompetitve.playerArmor.get(player));
	        	}
	        	
	        	player.damage(damage / 10, shooter);
				}
			//}
			}
			}
			}
		}
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Snowball){
			Player player = (Player) e.getEntity();
			Snowball bullet = (Snowball) e.getDamager();
			if(bullet.getShooter() != null){
				if(bullet.getShooter() instanceof Player){
			Player shooter = (Player) bullet.getShooter();
			if(shooter instanceof Player){
			int damage = 0;
			if(UtilsCompetitve.players.contains(player)){
				e.setCancelled(true);
				//if((!UtilsCompetitve.T.contains(player) && UtilsCompetitve.T.contains(shooter) || UtilsCompetitve.CT.contains(player) && UtilsCompetitve.CT.contains(shooter))){
				int y = (int) bullet.getLocation().getY();
	            int shotY = (int) player.getLocation().getY();
	            int headshot = y - shotY;
	            guns gun = gunBullet.get(bullet);
	        	if (headshot > 1.2D) {
	        		damage = weapons.getDamage(gun, hitType.HEAD, (int) startLocBullet.get(bullet).distance(player.getLocation()), true);
	        	}else
		        	if (headshot > 0.9D) {
		        		damage = weapons.getDamage(gun, hitType.CHEST_ARM, (int) startLocBullet.get(bullet).distance(player.getLocation()), true);
		        	}else
			        	if (headshot > 0.50D) {
			        		damage = weapons.getDamage(gun, hitType.STOMACH, (int) startLocBullet.get(bullet).distance(player.getLocation()), true);
			        	}else
	        	if (headshot > 0D) {
	        		damage = weapons.getDamage(gun, hitType.LEG, (int) startLocBullet.get(bullet).distance(player.getLocation()), true);
	        	}
	        	player.damage(damage / 10, shooter);
				}
			}
			}
			//}
		}
		}
	}
	
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		if(!player.isOp()){
			if(UtilsCompetitve.players.contains(player)){
			e.setCancelled(true);
			hg.sendMSG(player, "No Commands Allowed");
			}
		}
		}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(UtilsCompetitve.players.contains(e.getPlayer())){
			if(!e.getItemDrop().equals(weapons.bomb())){
			e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPicup(PlayerPickupItemEvent e){
		if(UtilsCompetitve.players.contains(e.getPlayer())){
			if(UtilsCompetitve.T.contains(e.getPlayer())){
			if(!e.getItem().equals(weapons.bomb())){
			e.setCancelled(true);
			}
			}else{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onVelocity(PlayerVelocityEvent e){
		if(UtilsCompetitve.players.contains(e.getPlayer())){
			e.setCancelled(true);
			e.getPlayer().setVelocity(new Vector(0, 0, 0));
		}
	}
	
	
	@EventHandler
	public void chooseGun(final InventoryClickEvent event){
		final Player player = (Player) event.getWhoClicked();
		if(UtilsCompetitve.players.contains(player)){
			event.setCancelled(true);
			if(event.getInventory().getTitle().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Competitive Items")){
				if(event.getCurrentItem() != null){
				if(event.getCurrentItem().getType() != null){
					if(event.getCurrentItem().getType() != Material.AIR){
						event.setCancelled(true);
						if(event.getCurrentItem().hasItemMeta()){
						if(weapons.getGun(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())) != null){
							guns gun = weapons.getGun(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
							if(hg.hasMoney(player, gun.getPrice())){
							if(gun.getType() != gunType.PISTOL){
								if(UtilsCompetitve.playerGun2.get(player) == null){
									hg.removeMoney(player, gun.getPrice());
									UtilsCompetitve.playerGun2.put(player, gun);
									player.getInventory().setItem(0, weapons.getWeapon(gun));
								}else{
									hg.removeMoney(player, gun.getPrice());
									UtilsCompetitve.playerGun2.put(player, gun);
									player.getInventory().remove(weapons.getWeapon(gun));
									player.getInventory().setItem(0, weapons.getWeapon(gun));
								}
							}else{
								if(UtilsCompetitve.playerGun1.get(player) == null){
									hg.removeMoney(player, gun.getPrice());
									UtilsCompetitve.playerGun1.put(player, gun);
									player.getInventory().setItem(1, weapons.getWeapon(gun));
								}else{
									hg.removeMoney(player, gun.getPrice());
									UtilsCompetitve.playerGun1.put(player, gun);
									player.getInventory().remove(weapons.getWeapon(gun));
									player.getInventory().setItem(1, weapons.getWeapon(gun));
								}
							}
							}
						}
						}
					}
				}
				}
			}
	}
	}
	
}
