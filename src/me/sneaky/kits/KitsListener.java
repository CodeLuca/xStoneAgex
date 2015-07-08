package me.sneaky.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;

import me.sneaky.LocationUtils;
import me.sneaky.Main;
import me.sneaky.events.custom.TimeSecondEvent;
import me.sneaky.kits.Kits.sKits;
import me.sneaky.kits.gladiator.GladiatorListener;
import me.sneaky.kits.grappler.FishingLine;
import me.sneaky.kits.grappler.GrapplerState;
import me.sneaky.spawnprotection.sProtectionListener;


public class KitsListener implements Listener {
	  final Main p;
	  

	  public KitsListener(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  GladiatorListener gListener = new GladiatorListener(Main.instance);
	  
	  
		@EventHandler
		public void Airbender(PlayerInteractEntityEvent e){
			Player player = e.getPlayer();
			if(e.getRightClicked() instanceof Player){
				Player target = (Player) e.getRightClicked();
				if(p.util.hasKit(player, sKits.Airbender)){
				if(player.getItemInHand().getType() == Material.BLAZE_ROD){
				if(!p.util.isOnCD(player)){
			    Vector vector = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
			    target.setVelocity(vector.multiply(2));
			    p.util.addCD(player, 10);
				}
				}
			}
			}
		}
		
		@EventHandler
		public void Berserker(PlayerDeathEvent e) {
			if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player){
			Player p = (Player) e.getEntity().getKiller();
			if(this.p.util.hasKit(p, sKits.Berserker)){
    	    	p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 0));
    	    	p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 1));
			}
		}		
	}
		
		@EventHandler
		public void Blink(PlayerInteractEvent e){
			final Player player = e.getPlayer();
			if(e.getAction() == Action.RIGHT_CLICK_AIR){
				if(player.getItemInHand().getType() == Material.NETHER_STAR){
					if(p.util.hasKit(player, sKits.Blink)){
						if(!p.util.isOnCD(player)){
							if(p.util.gladiatored.contains(player)){
								p.chat.sendMessagePlayer(player, "Can't use blink in this arena");
								return;
							}
							p.util.addCD(player, 10);
							BlockIterator bItr = new BlockIterator(player, 7);
							Block block;
							int bx, by, bz;
						while (bItr.hasNext()) {
			                block = bItr.next();
			                bx = block.getX();
			                by = block.getY();
			                bz = block.getZ();
			                
							Location loc = new Location(player.getWorld(), bx, by, bz);
							
					        for(Location l : p.locUtil.getLine(player.getLocation(), loc)){
					        	l.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 1);
					        }
							
							loc.setDirection(player.getLocation().getDirection());
							loc.setYaw(player.getLocation().getYaw());
							loc.setPitch(player.getLocation().getPitch());
							player.teleport(loc.clone());
			                }
					}
					}
				}
				}
		}

		
		@SuppressWarnings("deprecation")
		@EventHandler
		public void Dragon(PlayerInteractEvent e){
			final Player player = e.getPlayer();
			if(e.getAction() == Action.RIGHT_CLICK_AIR){
				if(p.util.hasKit(player, sKits.Dragon)){
				if(player.getItemInHand().getType() == Material.SLIME_BALL){
					if(!p.util.isOnCD(player)){
						p.util.addCD(player, 15);
						e.setCancelled(true);
						for(int i = 1; i < 25; i++){
							final int c = i;
							p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
								public void run(){
							        BlockIterator bItr = new BlockIterator(player, c);
							        Block block;
							        int bx, by, bz;
							        while (bItr.hasNext()) {
						                block = bItr.next();
							                bx = block.getX();
							                by = block.getY();
							                bz = block.getZ();
							                
											Location loc = new Location(player.getWorld(), bx, by, bz);
											if(!bItr.hasNext()){
											loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.EMERALD_BLOCK);
											
											FallingBlock bl = loc.getWorld().spawnFallingBlock(block.getLocation(), Material.AIR, (byte) 0);
											List<Entity> ent = bl.getNearbyEntities(1.50, 1.50, 1.50);
													for(Entity entity : ent){
														if(entity instanceof Player){
															Player target = (Player) entity;
															if(target != player){
															int h = 4;
															target.damage(h);
															target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 1));
															target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 5, 0));
															}
														}
													}
												}
											}
							                }
							}, i);
						}
						}
				}
						}
					}
			}	
		
		  @EventHandler
		  public void Drunk(PlayerItemConsumeEvent event) {
		    final Player player = event.getPlayer();
			if(p.util.hasKit(player, sKits.Drunk)){
			if(event.getItem().getType() == Material.MILK_BUCKET){
				if(!p.util.isOnCD(player)){
	        	    	p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
							public void run(){
	        	    			player.setItemInHand(new ItemStack(Material.MILK_BUCKET));
	        	    			player.updateInventory();
	        				    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 0));
	                	    	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
	                	    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 0));
	                	    	player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 2));
	        	    		}
	        	    	}, 1);
	            p.util.addCD(player, 90);

		    }else{
		    	p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
					public void run(){
		    			player.setItemInHand(new ItemStack(Material.MILK_BUCKET));
		    			player.updateInventory();
		    		}
		    	}, 1);
		    }
			}
			}
	}
		  
		  
			boolean maged;
			
			public HashMap<Player, Integer> taskID = new HashMap<Player, Integer>();
		    
			@SuppressWarnings("deprecation")
			@EventHandler
			public void Endermage(PlayerInteractEvent e){
				final Player player = e.getPlayer();
				if(p.util.hasKit(player, sKits.Endermage)){
					if(player.getItemInHand().getType() == Material.PORTAL){
						if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
							e.setCancelled(true);
						if(!p.util.isOnCD(player)){
							if(!p.util.gladiatored.contains(player)){
						final Block b = e.getClickedBlock();
						final Material mat = b.getType();
						maged = false;
						int task = 0;
						
						b.getLocation().getBlock().setType(Material.ENDER_PORTAL_FRAME);
						
						final FallingBlock skell =  player.getWorld().spawnFallingBlock(b.getLocation(), Material.AIR, (byte) 1);
						
						task = p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable() {
							public void run() {
							for(Entity pl : skell.getNearbyEntities(3, 300, 3)){
								if(pl instanceof Player && pl.getLocation().distance(b.getLocation()) > 5 && pl != player && (!(p.util.hasKit((Player) pl, sKits.Endermage))) && (!(p.util.isInSpawn((Player) pl)))){
									pl.teleport(b.getLocation().add(0.50, 1.5, 0.50));
									player.teleport(b.getLocation().add(0.50, 1.5, 0.50));
									b.getLocation().getBlock().setType(mat);
									skell.remove();
									maged = true;
									p.getServer().getScheduler().cancelTask(taskID.get(player));
								}
							}
							}
							}, 10, 10);
						
						taskID.put(player, task);
						
						if(maged == false){
							p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable() {
								public void run() {
									b.getLocation().getBlock().setType(mat);
									skell.remove();
									p.getServer().getScheduler().cancelTask(taskID.get(player));
									
								}
								}, 100L);
						}
						
						
						p.util.addCD(player, 7);
						}else{
							p.chat.sendMessagePlayer(player, "You Cannot Use Your Mage Portal In A Gladiator Fight!");
						}
						}
					}
					}
				}	
			}
			
			  @EventHandler
			  public void Fisherman(PlayerFishEvent event)
			  {
			    Player player = event.getPlayer();
			    if ((event.getCaught() instanceof Player)) {
			      Player caught = (Player)event.getCaught();
			      if ((p.util.hasKit(player, sKits.Fisherman)) && 
			        (player.getItemInHand().getType() == Material.FISHING_ROD)) {
			    	  if(!p.util.isOnCD(player)){
			        caught.teleport(player.getLocation());
			    	  }
			      }
			    }
			  }
			  
			  private void addEffect(Player player, PotionEffectType type){
				  player.addPotionEffect(new PotionEffect(type, 20 * 10, 0));
			  }
			  
			@SuppressWarnings("deprecation")
			@EventHandler
			public void Gambler(PlayerInteractEvent e){
				Player player = e.getPlayer();
					if(p.util.hasKit(player, sKits.Gambler)){
					if(player.getItemInHand().getType() == Material.WATCH){
					if(!p.util.isOnCD(player)){
					Random rand = new Random();
					int i = rand.nextInt(5);
					if(i == 0){
						addEffect(player, PotionEffectType.SLOW);
						p.chat.sendMessagePlayer(player, "You Have Recieved Slowness");
					}else
					if(i == 1){
						addEffect(player, PotionEffectType.INCREASE_DAMAGE);
						p.chat.sendMessagePlayer(player, "You Have Recieved Strenght");
					}else
					if(i == 2){
						addEffect(player, PotionEffectType.SPEED);
						p.chat.sendMessagePlayer(player, "You Have Recieved Speed");
					}else
					if(i == 3){
						addEffect(player, PotionEffectType.FAST_DIGGING);
						p.chat.sendMessagePlayer(player, "You Have Recieved Haste");
					}else
					if(i == 4){
						addEffect(player, PotionEffectType.POISON);
						p.chat.sendMessagePlayer(player, "You Have Recieved Poison");
					}else
					if(i == 5){
						addEffect(player, PotionEffectType.REGENERATION);
						p.chat.sendMessagePlayer(player, "You Have Recieved Regeneration");
					}
					player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				    p.util.addCD(player, 15);
					}
					}
				}
			}
			
	  
		    
			  HashMap<Player, Integer> hawk = new HashMap<Player, Integer>();
			  
				@EventHandler
				public void nuker1(PlayerInteractEvent e){
					final Player player = e.getPlayer();
					if(e.getAction() == Action.RIGHT_CLICK_AIR){
						if(player.getItemInHand().getType() == Material.FEATHER){
							if(p.util.hasKit(player, sKits.Hawk)){
							if(!p.util.isOnCD(player)){
							if(hawk.get(player) == null){
								hawk.put(player, 1);
							Vector v = new Vector(0, 3, 0);
							player.setVelocity(v);
							}else
							if(hawk.get(player) == 1){
								hawk.put(player, 2);
								player.setVelocity(player.getLocation().getDirection().multiply(4));
							}
							}
							}
							}
						}
					}
				
				@EventHandler
				  public void n(EntityDamageEvent event) {  

				          if (event.getEntity() instanceof Player) {

				                  Player player = (Player) event.getEntity();

				                          if (event.getCause() == DamageCause.FALL) {
				                        	  
				                        	  if(p.util.hasKit(player, sKits.Hawk)){
				                        	  
				                        	  if(hawk.get(player) != null){
				                        		  
				                        		  if(hawk.get(player) == 2 || hawk.get(player) == 1){
				                        			  
				                        			  p.util.addCD(player, 25);
				                        			  
				                        			  hawk.put(player, null);
				                        			  
				                        			  event.setCancelled(true);
				                        	  
				                          List<Entity> nearbyEntities = event.getEntity().getNearbyEntities(3, 3, 3);

				                          for (Entity target : nearbyEntities) {

				                                  if (target instanceof Player) {

				                                          Player t = (Player) target;

				                                          if(t.getName() == player.getName())

				                                                  continue;
				                                          
				                      		            Vector vector = t.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
				                      		            
				                    		            t.setVelocity(vector.multiply(1.2).setY(1.2));
				                                          
				                                        t.getWorld().strikeLightning(t.getLocation().add(0, 1, 0));
				                                  }
				                          }
				                         }
				                       }
				                  }
				          }
				  }
				}
				
				@EventHandler
				  public void Ironman(EntityDamageEvent e) {
					  if(e.getEntity() instanceof Player){
						  Player player = (Player) e.getEntity();
							if(p.util.hasKit(player, sKits.Ironman)){
								if(player.getItemInHand().getType() == Material.IRON_BLOCK){
						  e.setCancelled(true);
						  double dmg = 2;
						  player.damage(dmg);
								}
							}
					  }
				  }
				
				@EventHandler
				public void Ironman1 (BlockPlaceEvent e){
					if(p.util.hasKit(e.getPlayer(), sKits.Ironman)){
					if((e.getPlayer().getItemInHand().getType() == Material.IRON_BLOCK)){
						e.setCancelled(true);
					}
					}
				}
				
				@SuppressWarnings("deprecation")
				@EventHandler
				public void Ironman2(PlayerInteractEvent e){
					final Player player = e.getPlayer();
					if(e.getAction() == Action.RIGHT_CLICK_AIR){
						if(p.util.hasKit(player, sKits.Ironman)){
						if(player.getItemInHand().getType() == Material.IRON_BLOCK){
							e.setCancelled(true);
							if(!p.util.isOnCD(player)){
							p.util.addCD(player, 20);
								boolean b = false;
								for(Block loc : player.getLineOfSight(null, 30)){
									if(b == false){
									loc.getWorld().playEffect(loc.getLocation(), Effect.STEP_SOUND, Material.IRON_BLOCK);

									FallingBlock block = loc.getWorld().spawnFallingBlock(loc.getLocation(), Material.AIR, (byte) 0);
									List<Entity> ent = block.getNearbyEntities(1.50, 1.50, 1.50);
									if(b == false){
											for(Entity entity : ent){
												if(entity instanceof Player){
													Player target = (Player) entity;
													if(target != player){
														target.setVelocity(new Vector(0, 1.75, 0));
														player.teleport(target);
														target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 5, 1));
														player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 5, 1));
													b = true;
													}
												}
											}
									}
									
									block.remove();
									
									
										}
									}
								}
						}
								}
							}
					}
				
			    Map<String, Integer> jumps = new HashMap<String, Integer>();
			    
			    @EventHandler
			    public void Kangaroo (PlayerInteractEvent e) {
			        Player p = e.getPlayer();
			        if (this.p.util.hasKit(p, sKits.Kangaroo)) {
			            if ((e.getAction().name().contains("RIGHT")) ||(e.getAction().name().contains("LEFT")))  {
			                if (p.getItemInHand().getType() == Material.FIREWORK) {
				                e.setCancelled(true);
			                    Vector v = p.getEyeLocation().getDirection();
			                    if (!(e.getPlayer().getLocation().subtract(new Vector(0, 0.25, 0)).getBlock().getType() != Material.AIR)) {
			                    jumps.put(p.getName(), jumps.get(p.getName()) != null ? jumps.get(p.getName()) + 1 : 1);
			                    }else{
			                        jumps.put(p.getName(), jumps.get(p.getName()) != null ? jumps.get(p.getName()) + 2 : 1);
			                    }
			                    if (jumps.get(p.getName()) < 2) {
			                        p.setFallDistance(p.isSneaking() ? -5.0F : -7.0F);
			                        p.setVelocity(p.isSneaking() ? v.multiply(1.25F).setY(0.75F) : v.multiply(0.6F).setY(1.0F));
			                    }
			                }
			            }
			        }
			    }
			 
			 
			    @EventHandler
			    public void Kangaroo1 (PlayerMoveEvent e) {
			        if (this.p.util.hasKit(e.getPlayer(), sKits.Kangaroo)) {
			        if (e.getPlayer().getLocation().subtract(new Vector(0, 0.25, 0)).getBlock().getType() != Material.AIR) {
			            jumps.remove(e.getPlayer().getName());
			        }
			        }
			    }
			    
				@SuppressWarnings("deprecation")
				@EventHandler
				  public void Kangaroo2(EntityDamageEvent event) {  

				          if (event.getEntity() instanceof Player) {

				                  Player p = (Player) event.getEntity();

				                  if (this.p.util.hasKit(p, sKits.Kangaroo)) {

				                          if (event.getCause() == DamageCause.FALL) {

				                                  if (event.getDamage() > 7) {
				                                          
				                                          event.setDamage(7);
				                                          
				                                  }              
				                                  }
				                          }
				                          }
				                  }
				
				@EventHandler
				public void Leech(PlayerInteractEntityEvent e){
					final Player player = e.getPlayer();
					if(e.getRightClicked() instanceof Player){
					final Player target = (Player) e.getRightClicked();
						if(player.getItemInHand().getType() == Material.WEB){
							if(p.util.hasKit(player, sKits.Leech)){
								e.setCancelled(true);
							if(!p.util.isOnCD(player)){
							Damageable pl = player;
							Damageable ptl = target;
							double healthPlayer = ((CraftPlayer) pl).getHandle().getHealth();
							double healthTarget = ((CraftPlayer) ptl).getHandle().getHealth();
							
							player.setHealth(healthTarget);
							target.setHealth(healthPlayer);
							
							p.util.addCD(player, 15);
							
							}
							}
						}
						}
					}
				
				@SuppressWarnings("deprecation")
				@EventHandler
				public void Pyro(PlayerInteractEvent e){
					final Player player = e.getPlayer();
					if(e.getAction() == Action.RIGHT_CLICK_AIR){
						if(p.util.hasKit(player, sKits.Pyro)){
						if(player.getItemInHand().getType() == Material.REDSTONE_TORCH_ON){
							if(!p.util.isOnCD(player)){
							p.util.addCD(player, 15);
								e.setCancelled(true);
								for(int i = 1; i < 25; i++){
									final int c = i;
									p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
										public void run(){
									        BlockIterator bItr = new BlockIterator(player, c);
									        Block block;
									        int bx, by, bz;
									        while (bItr.hasNext()) {
								                block = bItr.next();
									                bx = block.getX();
									                by = block.getY();
									                bz = block.getZ();
									                
													Location loc = new Location(player.getWorld(), bx, by, bz);
													if(!bItr.hasNext()){
													loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.FIRE);
													loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
													
													FallingBlock bl = loc.getWorld().spawnFallingBlock(block.getLocation(), Material.AIR, (byte) 0);
													List<Entity> ent = bl.getNearbyEntities(1.50, 1.50, 1.50);
															for(Entity entity : ent){
																if(entity instanceof Player){
																	Player target = (Player) entity;
																	if(target != player){
																	int h = 6;
																	target.damage(h);
																	target.setFireTicks(20 * 5);
																	}
																}
															}
														}
													}
									                }
									}, i);
								}
							}
						}
					}
				}
			}
				
				
				@SuppressWarnings("deprecation")
				@EventHandler
				public void wizard(PlayerInteractEvent e){
					final Player player = e.getPlayer();
					if(e.getAction() == Action.RIGHT_CLICK_AIR){
						if(p.util.hasKit(player, sKits.Wizard)){
						if(player.getItemInHand().getType() == Material.COAL){
							if(!p.util.isOnCD(player)){
								p.util.addCD(player, 12);
								for(int i = 1; i < 25; i++){
									final int c = i;
									p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
										public void run(){
									        BlockIterator bItr = new BlockIterator(player, c);
									        Block block;
									        int bx, by, bz;
									        while (bItr.hasNext()) {
								                block = bItr.next();
									                bx = block.getX();
									                by = block.getY();
									                bz = block.getZ();
									                
													Location loc = new Location(player.getWorld(), bx, by, bz);
													if(!bItr.hasNext()){
													loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.COAL_BLOCK);
													
													FallingBlock bl = loc.getWorld().spawnFallingBlock(block.getLocation(), Material.AIR, (byte) 0);
													List<Entity> ent = bl.getNearbyEntities(1.50, 1.50, 1.50);
															for(Entity entity : ent){
																if(entity instanceof Player){
																	Player target = (Player) entity;
																	if(target != player){
																	int h = 4;
																	target.damage(h);
																	target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 1));
																	target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 5, 0));
																	}
																}
															}
														}
													}
									                }
									}, i);
								}
							}
						}
					}
				}
			}	
				
				@SuppressWarnings("deprecation")
				@EventHandler
				  public void Stomper(EntityDamageEvent event) {  

				          if (event.getEntity() instanceof Player) {

				                  Player player = (Player) event.getEntity();

				                  if (p.util.hasKit(player, sKits.Stomper)) {

				                          if (event.getCause() == DamageCause.FALL) {
				                        	  
				                        	  if(sProtectionListener.user.contains(player)){
				                        		  
				                        		  return;
				                        	  }

				                                  if (event.getDamage() > 4) {

				                                          event.setCancelled(true);
				                                          player.damage(4);
				                                  }       
				                                  if(!p.util.isOnCD(player)){
				                          List<Entity> nearbyEntities = event.getEntity().getNearbyEntities(5, 5, 5);
				                          
				                          boolean b = false;

				                          for (Entity target : nearbyEntities) {

				                                  if (target instanceof Player) {

				                                          Player t = (Player) target;

				                                          if(t.getName() == player.getName())

				                                                  continue;
				                                          b = true;
				                                          
				                                          if(!p.util.isInSpawn(t)){
				                                          if (t.isSneaking()){
				                                                  t.damage(4);
				                                          }else{
				                                                  t.damage(event.getDamage() / 1.5 , event.getEntity());
				                                                  }
				                                  }
				                          }
				                                  }
				                          if(b == true){
				                        	  p.util.addCD(player, 20);
				                          }
				                          }
				                          }
				                          }
				                  }
				  }
				
				  @EventHandler
				  public void Summoner(PlayerDeathEvent e){
					  Player player = e.getEntity();
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
				  }
				  
				  @EventHandler
				  public void Summoner1(PlayerQuitEvent e){
					  Player player = e.getPlayer();
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
				  }
				  	  
				  		@EventHandler
				  		public void Summoner2(PlayerInteractEvent e){
				  			final Player player = e.getPlayer();
				  			if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
				  				if(player.getItemInHand().getType() == Material.BLAZE_POWDER){
				  					if(p.util.hasKit(player, sKits.Summoner)){
				  					if(!p.util.isOnCD(player)){
				  					p.util.addCD(player, 90);
				  					
				  					Location loc = e.getClickedBlock().getLocation().add(0, 1, 0);
				  					
				  					for(int i = 0; i < 3; i++){
				  						final Zombie zom = (Zombie) player.getWorld().spawnEntity(loc, EntityType.ZOMBIE);	
				  						
					  					zom.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3));
					  					zom.getEquipment().setItemInHand(new ItemStack(Material.WOOD_AXE));
					  					zom.setCustomName(ChatColor.GOLD + player.getName() + "'s" + ChatColor.BLACK + " Minion");
					  					zom.setCustomNameVisible(true);
					  					zom.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
				  					}
				  				}
				  				}
				  				}
				  				}
				  		}
				  	

				  	  @EventHandler
				  	  public void Summoner3(EntityTargetEvent event) {
				  	    if (((event.getTarget() instanceof Player)) && ((event.getEntity() instanceof Zombie)))
				  	    {
				  	    	Zombie zom = (Zombie) event.getEntity();
							  if(zom.getCustomName() != null){
				  	    	if(zom.getCustomName().equals(ChatColor.GOLD + ((Player) event.getTarget()).getName() + "'s" + ChatColor.BLACK + " Minion")){
				  	        event.setCancelled(true);
				  	    	}
							 }
				  	    }
				  	  }
				  	  
					  @SuppressWarnings("deprecation")
						@EventHandler
						  public void Switcher(EntityDamageByEntityEvent event) {
						      if ((event.getEntity() instanceof Player) 
						    		  && ((event.getDamager() instanceof Snowball))) {
						      Snowball snowball = (Snowball)event.getDamager();
						    	Player thrower = (Player) snowball.getShooter();
						        if ((snowball.getShooter() instanceof Player)) {
									if(p.util.hasKit(thrower, sKits.Switcher)){
						        Location loc1 = thrower.getPlayer().getLocation().clone();
						        Location loc2 = event.getEntity().getLocation().clone();
						        thrower.getPlayer().teleport(loc2);
						        event.getEntity().teleport(loc1);
						    }
						        }
						      }
						  }
						  
						  @SuppressWarnings("deprecation")
						@EventHandler
						  public void Switcher1(ProjectileLaunchEvent event) {
							    if ((event.getEntityType() == EntityType.SNOWBALL) &&
							  	      (event.getEntity().getShooter() != null) && ((event.getEntity().getShooter() instanceof Player))) {
						      final Player player = (Player)event.getEntity().getShooter();
									if(p.util.hasKit(player, sKits.Switcher)){
									if(!p.util.isOnCD(player)){
								  event.setCancelled(false);

								  p.util.addCD(player, 10);
								  

							  }else{
								  event.setCancelled(true);
								  player.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 1));  
						      }
						    }
						  }
						  }
						  
							@EventHandler
							public void Thor(PlayerInteractEvent e){
								Player player = e.getPlayer();
								if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
									if(p.util.hasKit(player, sKits.Thor)){
									if(player.getItemInHand().getType() == Material.WOOD_AXE){
									if(!p.util.isOnCD(player)){
										int x = e.getClickedBlock().getLocation().getBlockX();
										int z = e.getClickedBlock().getLocation().getBlockZ();
										
										Location loc = player.getWorld().getHighestBlockAt(x, z).getLocation();
										player.getWorld().strikeLightningEffect(loc);
										for(Entity ent : player.getNearbyEntities(2, 2, 2)){
											if(ent instanceof Player && !((Player) ent).getName().equals(player.getName())){
												ent.setVelocity(new Vector(0, 0, 0));
												((Player) ent).damage(4D);
											}
										}
									
									
									
								    p.util.addCD(player, 5);
									}
									}
									}
								}
							}
							
							@EventHandler
							public void Witch(TimeSecondEvent e){
								for(Player player : Bukkit.getServer().getOnlinePlayers()){
									if(p.util.hasKit(player, sKits.Witch)){
										if(p.util.CD.get(player) != null){
											if(p.util.CD.get(player) <= 0){
												if(!p.util.isInSpawn(player)){
												for(Location loc : p.locUtil.getCircle(player.getLocation().add(0, 2, 0), 1)){
													Entity pot = loc.getWorld().spawnEntity(loc, EntityType.SPLASH_POTION);
													pot.setMetadata("WitchPot", new FixedMetadataValue(p, 6745639));
												}
												p.util.addNoMessageCD(player, 5);
												}
											}
										}
									}
							}
							}
							
							 @EventHandler
							 public void Witch1(PotionSplashEvent e) {
								 if(e.getEntity().hasMetadata("WitchPot") ){
								 for(Entity affected : e.getAffectedEntities()){
									 if(affected instanceof Player){
										 Player player = (Player)affected;
										 if(!p.util.hasKit(player, sKits.Witch)){
											 if(!p.util.isInSpawn(player)){
										 player.damage(5D);
											 }
										 }
									 }
								 }
								 }
							 }
									  
										@EventHandler
										  public void Ninja(EntityDamageByEntityEvent e) {
											  if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
												  Player player = (Player) e.getEntity();
												  Player hitter = (Player) e.getDamager();
													if(p.util.hasKit(hitter, sKits.Ninja)){
														p.util.ninja.put(hitter, player);
													}
											  }
										  }
										
										@EventHandler
										  public void Ninja1(PlayerToggleSneakEvent e) {
											Player player = e.getPlayer();
											if(p.util.hasKit(player, sKits.Ninja)){
												if(p.util.ninja.get(player) != null){
													if(!p.util.isInSpawn(p.util.ninja.get(player))){
														if(!p.util.isOnCD(player)){
													Location loc = p.util.ninja.get(player).getLocation();
													if(loc.distance(player.getLocation()) < 50){
													loc.setDirection(player.getLocation().getDirection());
													loc.setYaw(player.getLocation().getYaw());
													loc.setPitch(player.getLocation().getPitch());
													player.teleport(loc);
													p.util.addCD(player, 5);
													}else{
														p.chat.sendMessagePlayer(player, "He Is To Far");
													}
												}
												}
											}
											}
										}
										
										@SuppressWarnings("deprecation")
										@EventHandler
										public void Copycat(PlayerDeathEvent e) {
											if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player){
											Player player = (Player) e.getEntity();
											Player killer = (Player) e.getEntity().getKiller();
											if(p.util.isCopyCat(killer)){
												p.chat.sendMessagePlayer(killer, "You Are Now The Kit " + p.util.getKit(player));
												ItemStack[] inv = player.getInventory().getContents();
												p.util.setKit(killer, p.util.getKit(player));
												killer.getInventory().clear();
												killer.getInventory().setContents(inv);
												for(int i = 0; i < 36; i++){
													killer.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
												}
												killer.updateInventory();
												e.getDrops().clear();
										}		
									}
								}
										
										@EventHandler
										public void Viper (EntityDamageByEntityEvent event){
											if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
												Player damager = (Player) event.getDamager();
												Player victim = (Player) event.getEntity();
												if(p.util.hasKit(damager, sKits.Viper)){
													if(!p.util.isOnCD(damager)){
													Random rand = new Random();
													int i = rand.nextInt(3);
													if (i == 1){
														victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 0));
											        }
											}
												}
											}
										}
										
										@EventHandler
										public void Snail (EntityDamageByEntityEvent event){
											if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
												Player damager = (Player) event.getDamager();
												Player victim = (Player) event.getEntity();
												if(p.util.hasKit(damager, sKits.Snail)){
													if(!p.util.isOnCD(damager)){
													Random rand = new Random();
													int i = rand.nextInt(3);
													if (i == 1){
														victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 0));
											        }
													}
											}
											}
										}
										
										ArrayList<Player> anchor = new ArrayList<Player>();
										
										@EventHandler
										  public void Anchor(EntityDamageByEntityEvent e) {
											  if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
												  Player player = (Player) e.getEntity();
												  Player hitter = (Player) e.getDamager();
													if(p.util.hasKit(hitter, sKits.Anchor)){
														if(!anchor.contains(player)){
														anchor.add(player);
														}
													}
													if(p.util.hasKit(player, sKits.Anchor)){
														if(!anchor.contains(player)){
														anchor.add(player);
														}
													}
											  }
										  }
										
										@EventHandler
										  public void Anchor1(PlayerVelocityEvent e) {
											if(anchor.contains(e.getPlayer())){
												e.setCancelled(true);
												anchor.remove(e.getPlayer());
												e.getPlayer().setVelocity(new Vector(0, 0, 0));
											}
										}
										
										
										ArrayList<Player> timelord = new ArrayList<Player>();
										HashMap<Player, Location> timelordLoc = new HashMap<Player, Location>();
										
										@EventHandler
										public void Timelord(PlayerInteractEvent e){
											final Player player = e.getPlayer();
											if(p.util.hasKit(player, sKits.Timelord)){
											if(player.getItemInHand().getType() == Material.WATCH){
												if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
												if(!p.util.isOnCD(player)){
													p.util.addCD(player, 30);
													
													player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1));
													player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 5, 1));
													
													final Location pLoc = player.getLocation();
													for(int i = 0; i < 10; i++){
														p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
															public void run() {
																for(Location lo : p.locUtil.getCircle(pLoc, 6)){
																	for(int c = 0; c < 2; c++){
																	lo.getWorld().playEffect(lo.add(0, c, 0), Effect.MOBSPAWNER_FLAMES, 5);
																	}
																}
															}
															}, 10 * i);
													}
													
												for(Player ple : Bukkit.getServer().getOnlinePlayers()){
													ple.hidePlayer(player);
												}
												
												final List<Entity> nearbyEntities = player.getNearbyEntities(5, 5, 5);
												for(Entity ent : nearbyEntities){
													if(ent instanceof Player){
													Player pl = (Player) ent;
													if(!timelord.contains(pl)){
													timelord.add(pl);
													timelordLoc.put(pl, pl.getLocation());
													pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 999));
													pl.setAllowFlight(true);
													}
													}
												}
												
												
												p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
														public void run() {
															for(Player ple : Bukkit.getServer().getOnlinePlayers()){
																ple.showPlayer(player);
																ple.setAllowFlight(false);
															}
															for(Entity ent : nearbyEntities){
																if(ent instanceof Player){
																Player pl = (Player) ent;
																if(timelord.contains(pl)){
																timelord.remove(pl);
																timelordLoc.remove(pl);
																}
																}
															}
														}
												}, 20 * 5);
												
											}
											}
										}
										}
										}
										
										@EventHandler
										public void Timelord1(PlayerMoveEvent e){
											final Player player = e.getPlayer();
											if(timelord.contains(player)){
												double l1X = timelordLoc.get(player).getX() + 0.50;
												double l1Z = timelordLoc.get(player).getZ() + 0.50;
												
												double l2X = timelordLoc.get(player).getX() - 0.50;
												double l2Z = timelordLoc.get(player).getZ() - 0.50;
												
												double x = player.getLocation().getX();
												double z = player.getLocation().getZ();
												
												  if ((x >= l1X && x <= l2X) || (x <= l1X && x >= l2X)) {
													  if ((z <= l1Z && z >= l2Z) || (z >= l1Z && z <= l2Z)) {
															e.setCancelled(true);
															Location loc = timelordLoc.get(player);
															loc.setDirection(player.getLocation().getDirection());
															loc.setPitch(player.getLocation().getPitch());
															loc.setYaw(player.getLocation().getYaw());
															player.teleport(loc);
												}
											}
											}
										}
										
										@EventHandler
										  public void Timelord2(PlayerVelocityEvent e) {
											if(timelord.contains(e.getPlayer())){
												e.setCancelled(true);
											}
										}
										
										@SuppressWarnings("deprecation")
										@EventHandler
										public void Monk(PlayerInteractEntityEvent e){
											Player player = e.getPlayer();
											if(e.getRightClicked() instanceof Player){
												Player target = (Player) e.getRightClicked();
												if(p.util.hasKit(player, sKits.Monk)){
												if(player.getItemInHand().getType() == Material.BLAZE_ROD){
												if(!p.util.isOnCD(player)){
													Random rand = new Random();
													int c = rand.nextInt(36);
													
													ItemStack itemTarget = target.getItemInHand();
													ItemStack item = target.getInventory().getItem(c);
													
													target.setItemInHand(item);
													target.getInventory().setItem(c, itemTarget);
													target.updateInventory();
													
											    p.util.addCD(player, 15);
												}
												}
											}
											}
										}
										
										HashSet<Snowball> frosty = new HashSet<Snowball>();
										
									    @EventHandler
									    public void Frosty (PlayerMoveEvent e) {
									    	Player player = e.getPlayer();
									        if (this.p.util.hasKit(player, sKits.Frosty)) {
									        	if(player.getLocation().getBlock().getType() == Material.SNOW){
									        		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 1));
									        	}
									        }
									        }
									    
										  @SuppressWarnings("deprecation")
											@EventHandler
											  public void Frosty1(EntityDamageByEntityEvent event) {
											      if ((event.getEntity() instanceof Player) 
											    		  && ((event.getDamager() instanceof Snowball))) {
											      Snowball snowball = (Snowball)event.getDamager();
											    	Player thrower = (Player) snowball.getShooter();
											        if ((snowball.getShooter() instanceof Player)) {
														if(p.util.hasKit(thrower, sKits.Frosty)){
															Player player = (Player) event.getEntity();
															if(!frosty.contains(snowball)){
															frosty.add(snowball);
															}
															player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 0));
											    }
											        }
											      }
											  }
									    
									    @SuppressWarnings("deprecation")
										@EventHandler
									    public void Frosty2 (ProjectileHitEvent e) {
									    	if(e.getEntity().getType() == EntityType.SNOWBALL && e.getEntity().getShooter() instanceof Player){
									    	Player player = (Player) e.getEntity().getShooter();
									        if (this.p.util.hasKit(player, sKits.Frosty)) {
									        	if(!p.util.isInSpawn(e.getEntity().getLocation())){
									        	if(!frosty.contains(e.getEntity())){
									        	final Block b = e.getEntity().getLocation().getBlock();
									        	for(final Location loc : p.locUtil.getCircle(b.getLocation(), 3)){
										        	if(loc.getBlock().getType() == Material.AIR){
										        		loc.getBlock().setType(Material.SNOW);
											        	p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
											        		public void run(){
											        			loc.getBlock().setType(Material.AIR);
											        		}
											        	}, 20 * 3L);
											        }
									        	}
									        }else{
									        	frosty.remove(e.getEntity());
									        }
									        }
									        }
									        }
									    }
									    
										  @EventHandler
										  public void Tank(PlayerDeathEvent e){
											  Player player = e.getEntity();
											  if(e.getEntity().getKiller() != null){
												  if(e.getEntity().getKiller() instanceof Player){
												  Player killer = e.getEntity().getKiller();
												  if(p.util.hasKit(killer, sKits.Tank)){
												  TNTPrimed tnt = player.getWorld().spawn(player.getLocation(), TNTPrimed.class);
												  tnt.setFuseTicks(1);
												  Random rand = new Random();
												  int c = rand.nextInt(9999999);
												  tnt.setMetadata(killer.getUniqueId().toString(), new FixedMetadataValue(p,c));
												  }
												  }
											  }
										  }
										  
											@EventHandler
											  public void Tank1(EntityDamageByEntityEvent event) {
											      if ((event.getEntity() instanceof Player) 
											    		  && ((event.getDamager() instanceof TNTPrimed))) {
											    	  TNTPrimed tnt = (TNTPrimed)event.getDamager();
											    	  Player player = (Player) event.getEntity();
											    	  if(tnt.hasMetadata(player.getUniqueId().toString())){
											    		  event.setCancelled(true);
											    	  }
											      }
											  }
											
							                @EventHandler
							                public void Jesus(final PlayerDeathEvent event) {
							                        final Player p = event.getEntity();
							                        if(this.p.util.hasKit(p, sKits.Jesus)){
							                                      p.getInventory().clear();
							                                        Bukkit.broadcastMessage(p.getName() + " got resurrected!");
							                            p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 10000000);
							                            p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 10000000);
							                                        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 90, 100));
							                                    p.setHealth(20.0D);
							                                    event.getDrops().clear();
							                                    event.setDroppedExp(0);
							                                        p.getInventory().clear();
							                                        
							                                        
							                                        Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable(){
							                                                public void run() {
							                                                        p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
							                                                        p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));

							                                            p.setFireTicks(0);
							                                            }
							                                        }, 20 * 2);
							                                }
							                }
										
											@EventHandler
											public void Disrupter(PlayerInteractEvent e){
												final Player player = e.getPlayer();
												if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR ||
														e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
													if(player.getItemInHand().getType() == Material.REDSTONE_TORCH_ON 
															|| player.getItemInHand().getType() == Material.REDSTONE_TORCH_OFF){
														if(p.util.hasKit(player, sKits.Disrupter)){
														if(!p.util.isOnCD(player)){
															for(Entity ent : player.getNearbyEntities(10, 10, 10)){
																if(ent instanceof Player){
																	final Player target = (Player) ent;
																	p.util.jammed.add(target);
																	target.playSound(target.getLocation(), Sound.ZOMBIE_UNFECT, 100, 1);
														        	p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
														        		public void run(){
														        			p.util.jammed.remove(target);
														        		}
														        	}, 20 * 10L);
																}
															}
															p.util.addCD(player, 30);
														}
														}
														}
													}
												}
											
											@EventHandler
											public void Glacier(PlayerInteractEvent e){
												final Player player = e.getPlayer();
												if(e.getAction() == Action.RIGHT_CLICK_AIR ||
														e.getAction() == Action.RIGHT_CLICK_BLOCK ){
													if(player.getItemInHand().getType() == Material.ICE ){
														if(p.util.hasKit(player, sKits.Glacier)){
														if(!p.util.isOnCD(player)){
															
															Location loc1 = player.getLocation().getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getRelative(BlockFace.NORTH).getLocation();
															Location loc2 = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST).getRelative(BlockFace.SOUTH).getLocation();
															
															final ArrayList<BlockState> bState = new ArrayList<BlockState>();
															
															for(Location loc : p.locUtil.getCuboid(loc1, loc2)){
																bState.add(loc.getBlock().getState());
																loc.getBlock().setType(Material.ICE);
															}
															
															player.getLocation().getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
															player.getLocation().getBlock().setType(Material.AIR);
															
															player.setNoDamageTicks(20 * 3);
															
												        	p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
												        		public void run(){
												        			for(BlockState b : bState){
												        				b.setType(b.getType());
												        				b.setData(b.getData());
												        				bState.remove(b);
												        			}
												        			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 0));
												        			player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 5, 0));
												        		}
												        	}, 20 * 3);
															
															for(Entity entity : player.getNearbyEntities(5, 5, 5)){
																if(entity instanceof Player){
																	Player ent = (Player)entity;
																	ent.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 0));
																	ent.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 3, 0));
																}
															}
															
															player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 3, 10));
															
															
															
															
															p.util.addCD(player, 45);
														}
														}
														}
													}
												}

}
