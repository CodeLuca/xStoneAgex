package me.sneaky.kits.grappler;

import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_7_R4.EntityFishingHook;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class FishingLine extends EntityFishingHook {
    private Item item;
    
    public static HashMap<EntityHuman, Entity> snowBall = new HashMap<EntityHuman, Entity>();
    public static HashMap<EntityHuman, String> hooked = new HashMap<EntityHuman, String>();
    public static HashMap<Player, GrapplerState> status = new HashMap<Player, GrapplerState>();

    public FishingLine(Player player, Item item2){
        super(((CraftWorld) player.getWorld()).getHandle(), ( (CraftPlayer) player).getHandle());
        item2 =
        this.item = item2;
    }
    @Override
    public void h(){
        if(item != null){
            ItemStack hand = this.owner.be();
            boolean shouldRemove = false;

            if(this.owner.dead || !(this.owner.isAlive())){
                shouldRemove = true;
            }

            if(hand == null){
                shouldRemove = true;
            } else {
                if(hand.getItem() != item){
                    shouldRemove = true;
                }
            }

            if(this.e(this.owner) > 9999999.0D){
                shouldRemove = true;
            }

            if(shouldRemove){
                super.die();
                super.owner.hookedFish = null;
            }
            	this.getBukkitEntity().teleport(snowBall.get(this.owner).getLocation());
            	
            	List<Entity> ent = this.getBukkitEntity().getNearbyEntities(2.5, 2.5, 2.5);
            	for(Entity target : ent){
            		if(target instanceof Player){
            			if(((Player) target).getName() != this.owner.getBukkitEntity().getName()){
            				if(hooked.get(this.owner) != "true"){
            			snowBall.get(this.owner).remove();
            			snowBall.put(this.owner, target);
            			hooked.put(this.owner, "true");
            			status.put((Player)this.owner.getBukkitEntity(), GrapplerState.Player);
            			Player player = (Player) this.owner.getBukkitEntity();
        				player.sendMessage(ChatColor.GREEN + "Gotcha");
            				}
            			}
            		}
            	}
            	int loc1X = this.getBukkitEntity().getLocation().add(-1, 0, +1).getBlockX();
            	int loc1Y = this.getBukkitEntity().getLocation().add(0, 0, 0).getBlockY();
            	int loc1Z = this.getBukkitEntity().getLocation().add(-1, 0, +1).getBlockZ();
            	
            	int loc2X = this.getBukkitEntity().getLocation().add(+1, 0, -1).getBlockX();
            	int loc2Y = this.getBukkitEntity().getLocation().add(0, 0, 0).getBlockY();
            	int loc2Z = this.getBukkitEntity().getLocation().add(+1, 0, -1).getBlockZ();
            	
            	
    	        for(int xLoc = (int)Math.min(loc1X, loc2X); xLoc <= Math.max(loc1X, loc2X); xLoc++)
    	        {
    	           for(int yLoc = (int)Math.min(loc1Y, loc2Y); yLoc <= Math.max(loc1Y, loc2Y); yLoc++)
    	           {
    	               for(int zLoc = (int)Math.min(loc1Z, loc2Z); zLoc <= Math.max(loc1Z, loc2Z); zLoc++)
    	               {
    	            	   if(this.getBukkitEntity().getWorld().getBlockAt(xLoc, yLoc, zLoc).getType() != Material.AIR){
    	            		   if(hooked.get(this.owner) != "true"){
    	            			snowBall.get(this.owner).teleport(this.getBukkitEntity().getWorld().getBlockAt(xLoc, yLoc, zLoc).getLocation());
    	            		   hooked.put(this.owner, "true");
    	            		   status.put((Player)this.owner.getBukkitEntity(), GrapplerState.Block);
    	            		   ((Player) this.owner.getBukkitEntity()).sendMessage(ChatColor.GREEN + "Your Hook Is Now Attached");
    	            	   }
    	            	  }
    	               }
    	           }
    	        }
            	
        }
    }

	@SuppressWarnings("deprecation")
	public void spawn(Location loc){
        net.minecraft.server.v1_7_R4.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        
        Snowball snow = this.owner.getBukkitEntity().launchProjectile(Snowball.class);
        
        snow.setVelocity(this.owner.getBukkitEntity().getLocation().getDirection().multiply(2));
        
        if(snowBall.get(this.owner) != null){
        if(!snowBall.get(this.owner).isDead()){
        	if(!(snowBall.get(this.owner) instanceof Player)){
        	snowBall.get(this.owner).remove();
        	}
        }
        }
        
        snowBall.put(this.owner, snow);
        
        hooked.put(this.owner, "false");
        
        status.put((Player)this.owner.getBukkitEntity(), GrapplerState.None);
        
        nmsWorld.addEntity(this);
        
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(snow.getEntityId());
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
        
    }
	
}