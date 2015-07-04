package me.sneaky.events.icetag;

import me.sneaky.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerIceTag implements Listener {
	
	  final Main p;
	  

	  public ListenerIceTag(Main instance)
	  {
	    this.p = instance;
	  }

	UtilsIceTag hg = new UtilsIceTag();
	
	@EventHandler
	public void QuitEvent(PlayerQuitEvent e){
		Player player = e.getPlayer();
		if(UtilsIceTag.players.contains(player)){
			if(UtilsIceTag.started == true){
				hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Left");
				if(UtilsIceTag.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsIceTag.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsIceTag.players.size() + " Remaining");
				}
			}else{
				hg.removePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void DeathEvent(PlayerDeathEvent e){
		Player player = e.getEntity();
		if(UtilsIceTag.players.contains(player)){
			if(UtilsIceTag.started == true){
				this.hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Died");
				if(UtilsIceTag.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsIceTag.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsIceTag.players.size() + " Remaining");
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
			if(UtilsIceTag.players.contains(player)){
				if(UtilsIceTag.started == false && UtilsIceTag.countdown == true){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		if(!player.isOp()){
			if(UtilsIceTag.players.contains(player)){
				if(!e.getMessage().equalsIgnoreCase("/icetag leave")){
			e.setCancelled(true);
			hg.sendMSG(player, "No Commands Allowed");
				}
			}
		}
		}
	
}
