package me.sneaky.events.oitc;

import me.sneaky.Main;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerOITC implements Listener {
	
	  final Main p;
	  

	  public ListenerOITC(Main instance)
	  {
	    this.p = instance;
	  }

	UtilsOITC hg = new UtilsOITC();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void DMGEvent(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Arrow){
			Player player = (Player) e.getEntity();
			if(UtilsOITC.players.contains(player)){
			player.damage(40, (((Arrow) e.getDamager()).getShooter()));
			}
		}
	}
	
	@EventHandler
	public void QuitEvent(PlayerQuitEvent e){
		Player player = e.getPlayer();
		if(UtilsOITC.players.contains(player)){
			if(UtilsOITC.started == true){
				hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Left");
				if(UtilsOITC.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsOITC.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsOITC.players.size() + " Remaining");
				}
			}else{
				hg.removePlayer(player);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void DeathEvent(PlayerDeathEvent e){
		final Player player = e.getEntity();
		if(UtilsOITC.players.contains(player)){
			if(UtilsOITC.started == true){
				e.getDrops().clear();
				if(player.getKiller() instanceof Arrow){
					Arrow arrow = (Arrow) player.getKiller();
					if(arrow.getShooter() instanceof Player){
						Player killer = (Player) arrow.getShooter();
						killer.getInventory().addItem(new ItemStack(Material.ARROW, 1));
					}
				}
				
				if(player.getKiller() instanceof Player){
					Player killer = player.getKiller();
					killer.getInventory().addItem(new ItemStack(Material.ARROW, 1));
				}
				
				if(hg.getLifes(player) == 0){
				this.hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Died");
				if(UtilsOITC.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsOITC.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsOITC.players.size() + " Remaining");
				}
			}else{
				player.setHealth(20D);
				player.getInventory().clear();
				hg.tpToArena(player);
				hg.removeLife(player, 1);
				hg.sendMSG(player, "You Have " + hg.getLifes(player) + " Lifes Left!");
				Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
					public void run() {
				hg.giveStuff(player);
					}
				}, 20L);
			}
			}else{
				this.hg.removePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
			if(UtilsOITC.players.contains(player)){
				if(UtilsOITC.started == false && UtilsOITC.countdown == true){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		if(!player.isOp()){
			if(UtilsOITC.players.contains(player)){
				if(!e.getMessage().equalsIgnoreCase("/oitc leave")){
			e.setCancelled(true);
			hg.sendMSG(player, "No Commands Allowed");
				}
			}
		}
		}
	
	@EventHandler
	public void onShoot(ProjectileHitEvent e){
		if(e.getEntity() instanceof Arrow){
			e.getEntity().remove();
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(UtilsOITC.players.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	
}
