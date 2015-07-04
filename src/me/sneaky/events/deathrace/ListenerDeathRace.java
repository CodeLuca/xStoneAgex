package me.sneaky.events.deathrace;

import me.sneaky.Main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerDeathRace implements Listener {
	
	  final Main p;
	  

	  public ListenerDeathRace(Main instance)
	  {
	    this.p = instance;
	  }

	UtilsDeathRace hg = new UtilsDeathRace();
	
	@EventHandler
	public void QuitEvent(PlayerQuitEvent e){
		Player player = e.getPlayer();
		if(UtilsDeathRace.players.contains(player)){
			if(UtilsDeathRace.started == true){
				hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Left");
				if(UtilsDeathRace.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsDeathRace.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsDeathRace.players.size() + " Remaining");
				}
			}else{
				hg.removePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void DeathEvent(PlayerDeathEvent e){
		Player player = e.getEntity();
		if(UtilsDeathRace.players.contains(player)){
			if(UtilsDeathRace.started == true){
				this.hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Died");
				if(UtilsDeathRace.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsDeathRace.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsDeathRace.players.size() + " Remaining");
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
			if(UtilsDeathRace.players.contains(player)){
				if(UtilsDeathRace.started == false && UtilsDeathRace.countdown == true){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		if(!player.isOp()){
			if(UtilsDeathRace.players.contains(player)){
				if(!e.getMessage().equalsIgnoreCase("/drace leave")){
			e.setCancelled(true);
			hg.sendMSG(player, "No Commands Allowed");
				}
			}
		}
		}
	
	@EventHandler
	public void moveEvent(PlayerMoveEvent e){
		Player player = e.getPlayer();
		if(UtilsDeathRace.players.contains(player) && UtilsDeathRace.started == true){
			final Block block1 = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
			final Block block2 = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
			if(block1.getType() == Material.BEACON && block2.getType() == Material.DIAMOND_BLOCK){	
				hg.broadcastMSG(player.getName() + " Won!!!");
				player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
				hg.removePlayer(player);
				for(Player pl : UtilsDeathRace.players){
					pl.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
				}
				hg.stopGame();
			}
		}
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
			Player player = (Player) e.getEntity();
			if(UtilsDeathRace.players.contains(player)){
					e.setCancelled(true);
			}
		}
	}
	
}
