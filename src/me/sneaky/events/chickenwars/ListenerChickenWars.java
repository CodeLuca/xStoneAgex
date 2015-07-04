package me.sneaky.events.chickenwars;

import me.sneaky.Main;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ListenerChickenWars implements Listener {
	
	  final Main p;
	  

	  public ListenerChickenWars(Main instance)
	  {
	    this.p = instance;
	  }

	UtilsChickenWars hg = new UtilsChickenWars();
	
	@EventHandler
	public void QuitEvent(PlayerQuitEvent e){
		Player player = e.getPlayer();
		if(UtilsChickenWars.players.contains(player)){
			if(UtilsChickenWars.started == true){
				hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Left");
				if(player.getVehicle() != null){
				player.getVehicle().remove();
				}
				if(UtilsChickenWars.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsChickenWars.players){
						winner = pl;
					}
					if(winner != null){
						winner.getVehicle().remove();
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsChickenWars.players.size() + " Remaining");
				}
			}else{
				hg.removePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void DeathEvent(PlayerDeathEvent e){
		Player player = e.getEntity();
		if(UtilsChickenWars.players.contains(player)){
			if(UtilsChickenWars.started == true){
				this.hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Died");
				if(player.getVehicle() != null){
				player.getVehicle().remove();
				}
				if(UtilsChickenWars.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsChickenWars.players){
						winner = pl;
					}
					if(winner != null){
						winner.getVehicle().remove();
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsChickenWars.players.size() + " Remaining");
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
			if(UtilsChickenWars.players.contains(player)){
				if(UtilsChickenWars.started == false && UtilsChickenWars.countdown == true){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void playerMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
		if(UtilsChickenWars.players.contains(player)){
			if(UtilsChickenWars.started == true){
			if(player.getVehicle() != null){
				player.getVehicle().setVelocity(player.getLocation().getDirection().multiply(0.30));
			}else{
				hg.sendMSG(player, "You Cannot Go Off Your Chicken");
				if(!UtilsChickenWars.playerVehicle.get(player).isDead()){
					UtilsChickenWars.playerVehicle.get(player).setPassenger(player);
				}else{
					Chicken chick = (Chicken) player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.CHICKEN);
					chick.setPassenger(player);
					chick.setNoDamageTicks(Integer.MAX_VALUE);
					chick.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 99));
					UtilsChickenWars.playerVehicle.put(player, chick);
				}
			}
		}
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		if(!player.isOp()){
			if(UtilsChickenWars.players.contains(player)){
				if(!e.getMessage().equalsIgnoreCase("/CWars leave")){
			e.setCancelled(true);
			hg.sendMSG(player, "No Commands Allowed");
				}
			}
		}
		}
	
}
