package me.sneaky.events.sg;

import me.sneaky.Main;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerSG implements Listener {
	
	  final Main p;
	  

	  public ListenerSG(Main instance)
	  {
	    this.p = instance;
	  }

	UtilsSG hg = new UtilsSG();
	
	@EventHandler
	public void QuitEvent(PlayerQuitEvent e){
		Player player = e.getPlayer();
		if(UtilsSG.players.contains(player)){
			if(UtilsSG.started == true){
				hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Left");
				if(UtilsSG.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsSG.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsSG.players.size() + " Remaining");
				}
			}else{
				hg.removePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void DeathEvent(PlayerDeathEvent e){
		Player player = e.getEntity();
		if(UtilsSG.players.contains(player)){
			if(UtilsSG.started == true){
				this.hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Died");
				if(UtilsSG.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsSG.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsSG.players.size() + " Remaining");
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
			if(UtilsSG.players.contains(player)){
				if(UtilsSG.started == false && UtilsSG.countdown == true){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
			Player player = (Player) e.getPlayer();
			if(UtilsSG.players.contains(player)){
				if(UtilsSG.noMove == true){
					if(UtilsSG.warpLoc.get(player) != null){
						Location loc = UtilsSG.warpLoc.get(player);
						double x = loc.getX();
						double z = loc.getZ();
						if(x != player.getLocation().getX() || z != player.getLocation().getZ()){
							player.teleport(loc);
						}
					}
				}
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		if(!player.isOp()){
			if(UtilsSG.players.contains(player)){
				if(!e.getMessage().equalsIgnoreCase("/sg leave")){
			e.setCancelled(true);
			hg.sendMSG(player, "No Commands Allowed");
				}
			}
		}
		}
	
}
