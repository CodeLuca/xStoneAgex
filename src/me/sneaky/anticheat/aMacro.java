package me.sneaky.anticheat;

import me.sneaky.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class aMacro implements Listener {
	
	  final Main p;
	  
	  public aMacro(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @EventHandler
	  public void check(PlayerInteractEvent e){
		  Player player = e.getPlayer();
		  if(e.getAction() == Action.LEFT_CLICK_AIR){
			  p.anticheat.acClicks.put(player, p.anticheat.acClicks.get(player) != null ? p.anticheat.acClicks.get(player) + 1 : 1);
		  }
	  }
	  
	  
}
