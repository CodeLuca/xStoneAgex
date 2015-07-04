package me.sneaky.anticheat;

import me.sneaky.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class aAutosoup implements Listener {
	
	
	  final Main p;
	  
	  public aAutosoup(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @EventHandler
	  public void invClick(InventoryClickEvent e){
		  final Player player = (Player) e.getWhoClicked();
		  if(p.anticheat.asInv.get(player.getName()) != null){
			  if(e.getCurrentItem() != null){
				  if(e.isLeftClick() && e.isShiftClick()){
			  if(e.getCurrentItem().getType() == Material.BOWL){
				  p.anticheat.asInv.put(player.getName(), 1);
					p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable() {
						public void run() {
							p.anticheat.asInv.put(player.getName(), 0);
						}
						}, 1);
			  }
			  if(e.getCurrentItem().getType() == Material.MUSHROOM_SOUP){
				  if(p.anticheat.asInv.get(player.getName()) == 1){
					  p.anticheat.asWarns.put(player, 1);
				  }
				  }
			  }
			  }
			  
			  
		  }else{
			  p.anticheat.asInv.put(player.getName(), 0);
		  }
		  
	  }
	  
	  

}
