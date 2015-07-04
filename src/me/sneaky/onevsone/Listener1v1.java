package me.sneaky.onevsone;

import me.sneaky.Main;
import me.sneaky.onevsone.Utils1v1.Types1v1;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener1v1 implements Listener {
	
	  final Main p;

	  public Listener1v1(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  Utils1v1 u1v1 = new Utils1v1(Main.instance);
	  
	  @EventHandler
	  public void Inv1v1(InventoryClickEvent e){
		  final Player player = (Player) e.getWhoClicked();
		  if(p.util.arena.contains(player)){
			  if(e.getInventory().getTitle().equals(ChatColor.RED + ChatColor.BOLD.toString() + "1v1")){
			  if(e.getCurrentItem() != null){
				  if(e.getCurrentItem().getType() != Material.AIR){
					  if(e.getCurrentItem().equals(Items1v1.HardcoreGUI())){
						  u1v1.requestPlayer(player, p.util.preRequest.get(player), Types1v1.Hardcore);
						  player.closeInventory();
					  }
					  if(e.getCurrentItem().equals(Items1v1.NormalGUI())){
						  u1v1.requestPlayer(player, p.util.preRequest.get(player), Types1v1.Normal);
						  player.closeInventory();
					  }
					  if(e.getCurrentItem().equals(Items1v1.EliteGUI())){
						  u1v1.requestPlayer(player, p.util.preRequest.get(player), Types1v1.Elite);
						  player.closeInventory();
					  }
					  if(e.getCurrentItem().equals(Items1v1.HGGUI())){
						  u1v1.requestPlayer(player, p.util.preRequest.get(player), Types1v1.HG);
						  player.closeInventory();
					  }
					  if(e.getCurrentItem().equals(Items1v1.EarlyHGGUI())){
						  u1v1.requestPlayer(player, p.util.preRequest.get(player), Types1v1.EarlyHG);
						  player.closeInventory();
					  }
					  e.setCancelled(true);
				  }  
				  }  
				  }  
				  }  
				  }
	  
		@EventHandler
		public void reqeust1v1(PlayerInteractEntityEvent e){
			Player player = e.getPlayer();
			if(e.getRightClicked() instanceof Player){
			Player clicked = (Player) e.getRightClicked();
			if(player.getItemInHand().equals(Items1v1.Stick1v1())){
			if(p.util.arena.contains(clicked) && p.util.arena.contains(player)){
				if(!u1v1.isIn1v1(player)){
		    p.util.preRequest.put(player, clicked);
			u1v1.openPage1v1(player);
				}
			}
			}
			}
		}
		
		@EventHandler
		public void accept1v1(EntityDamageByEntityEvent event){
			if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
				Player damager = (Player) event.getDamager();
				Player victim = (Player) event.getEntity();
				if(u1v1.isInArena(damager)){
					if(!u1v1.isIn1v1(damager)){
						event.setCancelled(true);
					}
				if(damager.getItemInHand().equals(Items1v1.Stick1v1())){
				if(!u1v1.isIn1v1(damager) || !u1v1.isIn1v1(victim)){
				u1v1.acceptPlayer(damager, victim);
				}
				}
				}
			}
		}
		
		
		@EventHandler
		public void Death1v1(PlayerDeathEvent e){
			final Player player = e.getEntity();
			if(e.getEntity().getKiller() != null){
			if(e.getEntity().getKiller() instanceof Player){
			if(u1v1.isIn1v1(player)){
			Player killer = e.getEntity().getKiller();
			if(p.util.onevsone.get(player) == killer && p.util.onevsone.get(killer) == player){
				p.util.onevsone.remove(player);
				p.util.onevsone.remove(killer);
				
				p.util.request.remove(player);
				p.util.request.remove(killer);
				
				p.util.type1v1.remove(player);
				p.util.type1v1.remove(killer);

				p.chat.sendCustomMSG(killer, "1v1", "You Have Won The 1v1 Against " + player.getName());
				p.chat.sendCustomMSG(player, "1v1", "You Have Lost The 1v1 Against " + killer.getName());
				
				u1v1.tpTo1v1(killer);
				
				u1v1.showPlayers(player, killer);
				
				p.util.Clear(player);
			}
			}
			}
			}
		}
		
		@EventHandler
		public void Quit1v1(PlayerQuitEvent e){
			final Player player = e.getPlayer();
			if(u1v1.isIn1v1(player)){
			Player killer = p.util.onevsone.get(player);
			if(p.util.onevsone.get(player) == killer && p.util.onevsone.get(killer) == player){
				p.util.onevsone.remove(player);
				p.util.onevsone.remove(killer);
				
				p.util.request.remove(player);
				p.util.request.remove(killer);
				
				p.util.type1v1.remove(player);
				p.util.type1v1.remove(killer);

				p.chat.sendCustomMSG(killer, "1v1", "You Have Won The 1v1 Against " + player.getName());
				p.chat.sendCustomMSG(player, "1v1", "You Have Lost The 1v1 Against " + killer.getName());
				
				u1v1.tpTo1v1(killer);
				
				u1v1.showPlayers(player, killer);
				
				p.util.Clear(player);
				player.setHealth(0D);
			}
			}
		}
		
		@EventHandler
		public void onPlayerCommand(PlayerCommandPreprocessEvent e){
			Player player = e.getPlayer();
			if(!player.isOp()){
				if(u1v1.isIn1v1(player)){
					e.setCancelled(true);
					p.chat.sendCustomMSG(player, "1v1", "No Commands Allowed");
				}else
				if(u1v1.isInArena(player)){
				if(!(e.getMessage().equals("/stats") 
						|| e.getMessage().equals("/1v1") 
						|| e.getMessage().equals("/tell") 
						|| e.getMessage().equals("/msg")
						|| e.getMessage().equals("/r")
						|| e.getMessage().equals("/spawn"))){
				e.setCancelled(true);
				p.chat.sendCustomMSG(player, "1v1", "No Commands Allowed");
				}
				}
			}
			}
		
		   @EventHandler
		   public void onDropItem(final PlayerDropItemEvent e)
		   {
			   Player player = e.getPlayer();
			   if(u1v1.isInArena(player)){
				   e.setCancelled(true);
			   }
		 	 }
		
		

}
