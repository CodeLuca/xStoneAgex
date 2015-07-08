package me.sneaky.listeners;

import java.util.ArrayList;
import java.util.List;

import me.sneaky.Main;
import me.sneaky.cmds.staff.build;
import me.sneaky.events.custom.TimeSecondEvent;
import me.sneaky.kits.Kits.sKits;
import me.sneaky.stats.killstreaks.Killstreak;
import me.sneaky.utils.ScoreBoard;
import mkremins.fanciful.FancyMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class PlayerListener implements Listener {
	
	  final Main p;
	  

	  public PlayerListener(Main instance)
	  {
	    this.p = instance;
	  }
	
	  int intBC = 0;
	  boolean booleanBC = false;
	  
	  @EventHandler
	  public void broadcast(TimeSecondEvent e){
		  if(booleanBC == false){
			  intBC++;
			  if(intBC >= 60 * 1){
				  sendKitIdeaMSG();
				  booleanBC = true;
				  return;
			  }
		  }
		  if(booleanBC == true){
			  intBC--;
			  if(intBC <= 0){
				  booleanBC = false;
				  sendKitIdeaMSG();
				  return;
			  }
		  }
		  
	  }
	  
	  public void sendKitIdeaMSG(){
		  for(Player player : Bukkit.getServer().getOnlinePlayers()){
	          new FancyMessage("Post your own kit idea")
	          .color(ChatColor.RED)
	          .then(" Here")
	          .color(ChatColor.GOLD)
	           .link("http://goo.gl/Txgx02")
	          .tooltip("Click Here To Open The Link")
	          .send(player);
		  }
	  }
	  
	  ArrayList<Player> spam = new ArrayList<Player>();
	  
	  String[] notAllowed = {"ez", "dos", "ddossed", "ddos", "dox", "shit", "fuck", "cunt", "scrub", "skid", "dick", 
			  "faggot", "abuse", "gay", "queer", "hgkits", "futurekits", "eu.mc-hg.com", ".org", ".net", ".com", ".us",
			  ".eu", "cancer", "aids", "nigger", "nigga", "nigg", "penis", "wank", "pussy", 
			  "rekt", "www.", "youtube.com", "twitch", "streaming", "randal", "whore", "slut", "crap", "grived", "grief", "(dot)", "fag", "mrgayharm",};
	  
		@EventHandler
		  public void onSpam(AsyncPlayerChatEvent e){
			final Player player = e.getPlayer();
			if(!player.isOp()){
				if(spam.contains(player)){
					player.sendMessage(ChatColor.GRAY + "You must wait 3 seconds before saying a message again");
					e.getRecipients().clear();
					e.setCancelled(true);
				}else{
					boolean b = false;
					for(String str : notAllowed){
						if(e.getMessage().toLowerCase().contains(str)){
							b = true;
							player.sendMessage(ChatColor.GRAY + "You are not allowed to say that " + str);
						}
					}
					
					if(b == false){
						spam.add(player);
				 	     p.getServer().getScheduler().runTaskLater(p, new Runnable() {
				 	         public void run() {
				 	        	spam.remove(player);
				 	         }
				 	 }, 20 * 5L);
					}else{
						e.getRecipients().clear();
						e.setCancelled(true);
					}
				}
			}
		    }
	  
		@EventHandler
		  public void onJoin(PlayerPreLoginEvent e)
		    {
			if(e.getResult() != Result.ALLOWED){
				e.setKickMessage("You Have Been Banned For Cheating");
			}
		      
		    }
	  
	   @EventHandler
	   public void onDropItem(final PlayerDropItemEvent e)
	   {
		   boolean b = false;
		   for(sKits kit : sKits.values()){
			   if(kit.toString().equalsIgnoreCase(ChatColor.stripColor(e.getItemDrop().getItemStack().getItemMeta().getDisplayName()))){
				   e.setCancelled(true);
				   p.chat.sendMessagePlayer(e.getPlayer(), "You are not allowed to drop that!");
				   b = true;
			   }
		   }
		   if(e.getItemDrop().equals(Killstreak.airstrikeItem()) || e.getItemDrop().equals(Killstreak.sentryItem()) || e.getItemDrop().equals(Killstreak.predatorItem())){
			   b = true; 
			   e.setCancelled(true);
			   p.chat.sendMessagePlayer(e.getPlayer(), "You are not allowed to drop that!");
		   }
		   if(b == false){
		 	     p.getServer().getScheduler().runTaskLater(p, new Runnable() {
		 	         public void run() {
		 	                 e.getItemDrop().remove();
		 	         }
		 	 }, 20 * 5L);
		   }
		 }
	
	@EventHandler
	public void BlockBreak (BlockBreakEvent e){
		if(!(build.Build.contains(e.getPlayer().getName()))){
			e.setCancelled(true);
			return;
		}
		if(!(e.getPlayer().hasPermission("skits.block.break") || e.getPlayer().isOp())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void BlockPlace (BlockPlaceEvent e){
		if(!(build.Build.contains(e.getPlayer().getName()))){
			e.setCancelled(true);
			return;
		}
		if(!(e.getPlayer().hasPermission("skits.block.place") || e.getPlayer().isOp())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onExplodeCommand(EntityExplodeEvent e)
	{
			e.setCancelled(true);

	}
	
	
	@EventHandler
	  public void onQuit(PlayerQuitEvent e)
	    {
	      Player player = e.getPlayer();
	      
	      e.setQuitMessage(ChatColor.GRAY + player.getName() + ChatColor.GRAY + " Left");
	    }
	 
	@EventHandler
	  public void onJoin(PlayerJoinEvent e) throws IllegalStateException, IllegalArgumentException, Exception
	    {
	      Player player = e.getPlayer();
	     
	      ScoreBoard.setScoreboard(player);
	      
	      e.setJoinMessage(ChatColor.GRAY + player.getName() + ChatColor.GRAY + " Joined");
	      
	      p.util.Clear(player);
	      
	      player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
	      
          new FancyMessage("Post your own kit idea")
          .color(ChatColor.RED)
          .then(" Here")
          .color(ChatColor.GOLD)
           .link("http://goo.gl/Txgx02")
          .tooltip("Click Here To Open The Link")
          .send(player);
	    }
	  
	  @EventHandler
	  public void onKick(PlayerKickEvent e)
	  {
	      final Player player = e.getPlayer();
	      
	      e.setLeaveMessage(ChatColor.BLUE + "VoidKits> " + ChatColor.GOLD + player.getName() + " Got kicked");
	  }
		
		@EventHandler
		public void onFoodLevelChange(FoodLevelChangeEvent event){
			event.setCancelled(true);
		}
		  
		  
		   @EventHandler
		   public void onRefill(PlayerInteractEvent e){
			   if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK )) return;
		    if(e.getClickedBlock() != null){
		     if(e.getClickedBlock().getType() == Material.TRAPPED_CHEST){
		    e.setCancelled(true);
		    Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + ChatColor.BOLD.toString() + "Refill Chest");
		    for(int i = 0;i<54;i++){
		        inv.addItem(new ItemStack[] { new ItemStack(Material.MUSHROOM_SOUP) });
		     }
		    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_PLING, 3, 3);
		    e.getPlayer().openInventory(inv);
		    }
		    }

		   }
		   
	  
		@EventHandler
		   public void PotionFix(EntityDamageByEntityEvent event) {
		     if ((event.getDamager() instanceof Player))
		     {
		       Player player = (Player)event.getDamager();
		       
		       if(player.getItemInHand().getType() == Material.STONE_SWORD){
		       event.setDamage(event.getDamage() - 3);
		       }
		       
		       if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
		       {
		         for (PotionEffect Effect : player.getActivePotionEffects())
		         {
		           if (Effect.getType().equals(PotionEffectType.INCREASE_DAMAGE))
		           {
		             double DamagePercentage = (Effect.getAmplifier() + 1) * 1.3D + 1.0D;
		             int NewDamage;
		             if (event.getDamage() / DamagePercentage <= 1.0D)
		             {
		               NewDamage = (Effect.getAmplifier() + 1) * 3 + 1;
		             }
		             else
		             {
		               NewDamage = (int)(event.getDamage() / DamagePercentage) + (Effect.getAmplifier() + 1) * 3;
		             }
		             event.setDamage(NewDamage);
		             break;
		           }
		         }
		       }
		       
		     }
		   }
		   
		     @EventHandler
		     public void onEntityExplode(EntityExplodeEvent e) {
		    	 e.setCancelled(true);
		     }
			
			  @EventHandler
			  public void PlayerDeath(final PlayerDeathEvent e){
				  final Player player = e.getEntity();
				  
				  e.setDeathMessage(null);
				  
				  if(player.getKiller() != null){
				  p.chat.sendMessagePlayer(player, "You have been killed by " + player.getKiller().getName() + "(" + p.util.getKit(player.getKiller()) + ")" );
				  }
				  
			      p.util.Clear(player);

			      
			     List<ItemStack> items = e.getDrops();
			       ArrayList<ItemStack> itemlist = new ArrayList<ItemStack>();
			       itemlist.addAll(items);
			       final Location f = e.getEntity().getLocation();
			       for (ItemStack it : itemlist) {
			    	   if(it.getType() == Material.MUSHROOM_SOUP || it.getType() == Material.BOWL  
			    			   || it.getType() == Material.RED_MUSHROOM || it.getType() == Material.BROWN_MUSHROOM 
			    			   || it.getType() == Material.STONE_SWORD){
			       final Item itemonGround= f.getWorld().dropItemNaturally(f, it);
			       e.getDrops().clear();
			       p.getServer().getScheduler()
					  .scheduleSyncDelayedTask(p, new Runnable() {
						  public void run() {
			          itemonGround.remove();
			         
			         }
			        }, 20 * 10L);
			       }
			       }
			       
			       
			  }
			  
			  @EventHandler
			  public void onInteract(PlayerInteractEvent e)
			  {
			    Player pl = e.getPlayer();
			    
			    Action action = e.getAction();

			    if (((action == Action.RIGHT_CLICK_BLOCK) || (action == Action.RIGHT_CLICK_AIR)) && (pl.getItemInHand().getType() == Material.MUSHROOM_SOUP) && (((CraftPlayer) pl).getHandle().getHealth() != 20) && (((CraftPlayer) pl).getHandle().getHealth() + 1 <= 20))
			    {
			      e.setCancelled(true);

			      ((CraftPlayer) pl).getHandle().setHealth(((CraftPlayer) pl).getHandle().getHealth() + 7 > ((CraftPlayer) pl).getHandle().getMaxHealth() ? 20 : ((CraftPlayer) pl).getHandle().getHealth() + 7);

			      pl.getInventory().setItemInHand(new ItemStack(Material.BOWL, 1));
			    }
			  }

}
