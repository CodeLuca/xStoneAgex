package me.sneaky;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.sneaky.kits.Kits.sKits;
import me.sneaky.kits.grappler.FishingLine;
import me.sneaky.kits.grappler.GrapplerState;

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
	
    HashMap<Player, Integer> grappler = new HashMap<Player, Integer>();
    HashMap<Player, Entity> grapplerHook = new HashMap<Player, Entity>();
     
    @SuppressWarnings("deprecation")
	@EventHandler
      public void Grappler(PlayerInteractEvent event)
      {
        Player player = event.getPlayer();
	//	if(p.util.hasKit(player, sKits.Grappler)){
        net.minecraft.server.v1_8_R1.Item item = (net.minecraft.server.v1_8_R1.Item) net.minecraft.server.v1_8_R1.Items.LEAD;
if (((event.getAction() == Action.LEFT_CLICK_AIR) && (event.getMaterial() == Material.getMaterial(420))) || ((event.getAction() == Action.LEFT_CLICK_BLOCK) && (event.getMaterial() == Material.getMaterial(420)))) {
	if(!p.util.isOnCD(player)){
    if(grappler.get(player) != null){
    if(grappler.get(player) == 2){
        grappler.put(player, 1);
        Entity h = grapplerHook.get(player);
        h.remove();
    }
    }
      event.setCancelled(true);
      FishingLine fish = new FishingLine(player, item);
      Location location = player.getLocation();
      fish.spawn(location);
      grappler.put(player, 2);
      Entity hook = fish.getBukkitEntity();
      hook.teleport(location.add(0, 1.5, 0));
      hook.setVelocity(player.getLocation().getDirection().multiply(1.5));
      grapplerHook.put(player, hook);
         
}
}
if (((event.getAction() == Action.RIGHT_CLICK_AIR) && (event.getMaterial() == Material.getMaterial(420))) || ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getMaterial() == Material.getMaterial(420)))) {
	if(grapplerHook.get(player) != null){
	Location to = grapplerHook.get(player).getLocation();
    if(!grapplerHook.get(player).isDead()){
    	grapplerTo(player, to);
	}
	}
}
//}
}
    
    public void grapplerTo(Player e, Location loc){
        if(FishingLine.status.get((Player)e) == GrapplerState.Player){
    	int speed = (int) 2.5;
    	Vector dir = loc.toVector().subtract(e.getLocation().toVector()).normalize();
    	e.setVelocity(dir.multiply(speed));
        }
        
        if(FishingLine.status.get((Player)e) == GrapplerState.Block){
    	int speed = (int) 1.5;
    	Vector dir = loc.toVector().subtract(e.getLocation().toVector()).normalize();
    	e.setVelocity(dir.multiply(speed));
        }
    }
    
    public static void GrapplerpullTo(Entity e, Location loc) {
        Location l = e.getLocation();

        if (l.distanceSquared(loc) < 9) {
           if (loc.getY() > l.getY()) {
                e.setVelocity(new Vector(0, 0.25, 0));
               return;
           }
            Vector v = loc.toVector().subtract(l.toVector());
            e.setVelocity(v);
            return;
       }

        l.setY(l.getY() + 0.5);

        double d = loc.distance(l);
       double g = -0.08;
       double x = (1.0 + 0.07 * d) * (loc.getX() - l.getX()) / d;
        double y = (1.0 + 0.03 * d) * (loc.getY() - l.getY()) / d - 0.5 * g * d;
        double z = (1.0 + 0.07 * d) * (loc.getZ() - l.getZ()) / d;
        if(FishingLine.status.get((Player)e) == GrapplerState.Block){
        Vector v = e.getVelocity();
       v.setX(x / 1.25);
        v.setY(y / 1.25);
        v.setZ(z / 1.25);
        e.setVelocity(v);
        e.setFallDistance(0);
        }
        if(FishingLine.status.get((Player)e) == GrapplerState.Player){
        Vector v = e.getVelocity();
        v.setX(x);
        v.setY(y);
        v.setZ(z);
        e.setVelocity(v);
        e.setFallDistance(0);
        }
    }

}
