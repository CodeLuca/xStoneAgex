package me.sneaky;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Codes {
	
	
	  final Main p;
	  

	  public Codes(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  public void dd(){
		  TimerTask timer = new TimerTask(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}	  
		  };
		  Timer time = new Timer();
		  time.schedule(timer, 1000);
	  }
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void Pikachu(PlayerInteractEvent e){
		final Player player = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR){
			//if(p.util.hasKit(player, sKits.Pikachu)){
			if(player.getItemInHand().getType() == Material.NETHER_STAR){
				if(p.util.isOnCD(player)){
					p.util.addCD(player, 30);
					e.setCancelled(true);
					for(int i = 1; i < 25; i++){
						final int c = i;
						p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
							public void run(){
						        BlockIterator bItr = new BlockIterator(player, c);
						        Block block;
						        int bx, by, bz;
						        while (bItr.hasNext()) {
					                block = bItr.next();
						                bx = block.getX();
						                by = block.getY();
						                bz = block.getZ();
						                
										Location loc = new Location(player.getWorld(), bx, by, bz);
										if(!bItr.hasNext()){
										loc.getWorld().strikeLightningEffect(loc);
										
										FallingBlock bl = loc.getWorld().spawnFallingBlock(block.getLocation(), Material.AIR, (byte) 0);
										List<Entity> ent = bl.getNearbyEntities(1.50, 1.50, 1.50);
												for(Entity entity : ent){
													if(entity instanceof Player){
														Player target = (Player) entity;
														if(target != player){
														int h = 4;
														target.damage(h);
														target.setFireTicks(20 * 5);
														target.setVelocity(new Vector(0, 1.25, 0));
														}
													}
												}
											}
										}
						                }
						}, i);
					}
					}
			}
				//	}
			}
		}	

}
