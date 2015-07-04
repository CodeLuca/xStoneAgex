package me.sneaky.events.tntrun;

import me.sneaky.Main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerTNTRun implements Listener {
	
	  final Main p;
	  

	  public ListenerTNTRun(Main instance)
	  {
	    this.p = instance;
	  }

	UtilsTNTRun hg = new UtilsTNTRun();
	
	@EventHandler
	public void QuitEvent(PlayerQuitEvent e){
		Player player = e.getPlayer();
		if(UtilsTNTRun.players.contains(player)){
			if(UtilsTNTRun.started == true){
				hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Left");
				if(UtilsTNTRun.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsTNTRun.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsTNTRun.players.size() + " Remaining");
				}
			}else{
				hg.removePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void DeathEvent(PlayerDeathEvent e){
		Player player = e.getEntity();
		if(UtilsTNTRun.players.contains(player)){
			if(UtilsTNTRun.started == true){
				this.hg.removePlayer(player);
				hg.broadcastMSG(player.getName() + " Died");
				if(UtilsTNTRun.players.size() <= 1){
					Player winner = null;
					for(Player pl : UtilsTNTRun.players){
						winner = pl;
					}
					if(winner != null){
						hg.broadcastMSG(winner.getName() + " Won!!!");
						winner.teleport(winner.getWorld().getSpawnLocation().add(0.50, 0, 0.50));
					}
					hg.stopGame();
				}else{
					hg.broadcastMSG(UtilsTNTRun.players.size() + " Remaining");
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
			if(UtilsTNTRun.players.contains(player)){
				if(UtilsTNTRun.started == false && UtilsTNTRun.countdown == true){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		if(!player.isOp()){
			if(UtilsTNTRun.players.contains(player)){
				if(!e.getMessage().equalsIgnoreCase("/TNTRun leave")){
			e.setCancelled(true);
			hg.sendMSG(player, "No Commands Allowed");
				}
			}
		}
		}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void moveEvent(PlayerMoveEvent e){
		Player player = e.getPlayer();
		if(UtilsTNTRun.players.contains(player) && UtilsTNTRun.started == true){
			if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getTypeId() == 12 &&
			player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.TNT){
				
				
				final Block block1 = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
				final Block block2 = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
				
				final BlockState blockState1 = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getState();
				final BlockState blockState2 = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState();
				
				if(UtilsTNTRun.removeBlock == true){
				if(!UtilsTNTRun.blocks.contains(blockState1)){
				UtilsTNTRun.blocks.add(blockState1);
				}
				if(!UtilsTNTRun.blocks.contains(blockState2)){
					UtilsTNTRun.blocks.add(blockState2);
				}
				
				Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
					public void run() {
						block1.setType(Material.AIR);
						block2.setType(Material.AIR);
					}		
				}, 5);
			}
			}
		}
	}
	
}
