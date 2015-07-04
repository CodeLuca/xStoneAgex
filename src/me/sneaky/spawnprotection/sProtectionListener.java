package me.sneaky.spawnprotection;

import me.sneaky.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class sProtectionListener implements Listener {
	
	  final Main p;
	  

	  public sProtectionListener(Main instance)
	  {
	    this.p = instance;
	  }
	  

	  @EventHandler
	  public void dmg(EntityDamageEvent e){
		  if(e.getEntity() instanceof Player){
			  Player player = (Player) e.getEntity();
			  if(p.util.isInSpawn(player)){
				  e.setCancelled(true);
			  }
		  }
	  }
}
