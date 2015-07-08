package me.sneaky.spawnprotection;

import java.util.ArrayList;

import me.sneaky.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

public class sProtectionListener implements Listener {
	
	  final Main p;
	  

	  public sProtectionListener(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  public static ArrayList<Player> user = new ArrayList<Player>();
	  
	  
	  @EventHandler
	  public void dmg(PlayerMoveEvent e){
		  final Player player = e.getPlayer();
		  if(p.util.isInSpawn(player)){ 
			  
			  if(!sProtectionListener.user.contains(player)){
				  sProtectionListener.user.add(player);
			  }
		  }
			  
		  if(!p.util.isInSpawn(player) && sProtectionListener.user.contains(player)){ 
			  p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
				public void run() {
					if(sProtectionListener.user.contains(player)){
						sProtectionListener.user.remove(player);
					}
				} 
			  }, 20 * 5);
		  }
		  }

	  @EventHandler
	  public void dmg(EntityDamageEvent e){
		  if(e.getEntity() instanceof Player){
			  final Player player = (Player) e.getEntity();
			  if(p.util.isInSpawn(player)){
				  e.setCancelled(true);
				  
			  }
			  
			  if(e.getCause() == DamageCause.FALL && sProtectionListener.user.contains(player)){
				  sProtectionListener.user.remove(player);
				  e.setCancelled(true);
			  }
		  }
	  }
}
