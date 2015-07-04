package me.sneaky.cmds;

import java.util.ArrayList;

import me.sneaky.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Spectate implements CommandExecutor, Listener {
	
	static ArrayList<Player> spectate = new ArrayList<Player>();
	
	
	public static void spectateMode(Player player, boolean on, boolean tp){
		if(on == true){
			if(!spectate.contains(player)){
				spectate.add(player);
			}
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1));
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 10));
			player.setAllowFlight(true);
			player.setFlying(true);
			player.getInventory().clear();
			//Main.instance.chat.sendCustomMSG(player, "Spectate", "Spectate Mode Is Now On");
		}
		
		if(on == false){
			if(spectate.contains(player)){
				spectate.remove(player);
			}
			player.setAllowFlight(false);
			player.setFlying(false);
			if(tp == true){
			player.teleport(player.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
			}
			//Main.instance.chat.sendCustomMSG(player, "Spectate", "Spectate Mode Is Now Off");
		}
	}
	
	public static boolean isInSpectate(Player player){
		if(spectate.contains(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    	if(isInSpectate(player)){
		    	Spectate.spectateMode(player, false, true);
		    	Main.instance.chat.sendCustomMSG(player, "Spectate", "Spectate Mode Is Now Off");
		    	}else{
		    		if(Main.instance.util.isInSpawn(player)){
		    		Spectate.spectateMode(player, true, true);
		    		Main.instance.chat.sendCustomMSG(player, "Spectate", "Spectate Mode Is Now On");
		    	}else{
	    			Main.instance.chat.sendCustomMSG(player, "Spectate", "You Can Only Do This In The Spawn");
	    		}
		    	}
		    }
		    return false;
          	}

	
	
	   @EventHandler
	   public void onDropItem(final PlayerDropItemEvent e)
	   {
		   if(spectate.contains(e.getPlayer())){
			   e.setCancelled(true);
		   }
		 }
	   
	   @EventHandler
	   public void onDropItem(final PlayerPickupItemEvent e)
	   {
		   if(spectate.contains(e.getPlayer())){
			   e.setCancelled(true);
		   }
		 }
	   
	   @EventHandler
	   public void onDMG(EntityDamageByEntityEvent e){
		   if(e.getDamager() instanceof Player){
			   Player player = (Player) e.getDamager();
			   if(spectate.contains(player)){
				   e.setCancelled(true);
			   }
		   }
		   if(e.getEntity() instanceof Player){
			   Player player = (Player) e.getEntity();
			   if(spectate.contains(player)){
				   e.setCancelled(true);
			   }
		   }
	   }
	
	

}
