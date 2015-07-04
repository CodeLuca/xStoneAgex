package me.sneaky.listeners;

import java.util.ArrayList;
import java.util.Random;

import me.sneaky.Main;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class CannonListener implements Listener {
	
	ArrayList<Location> cannonCD = new ArrayList<Location>();
	
	public void launchCannon(Player player, Location locOfSpawn, Location locShootTo){
		TNTPrimed tnt = player.getWorld().spawn(locOfSpawn.add(0.50, 0.50, 0.50), TNTPrimed.class);
		//Snowball snow = player.getWorld().spawn(locOfSpawn, Snowball.class);
		player.getWorld().playSound(locOfSpawn, Sound.EXPLODE, 100, 1);
		player.getWorld().playEffect(locOfSpawn, Effect.SMOKE, 1);
		player.getWorld().playEffect(locOfSpawn, Effect.MOBSPAWNER_FLAMES, 1);
		tnt.setMetadata("cannon", new FixedMetadataValue(Main.instance, new Random().nextInt(999999)));
		tnt.setVelocity(locShootTo.add(0.50, 0.50, 0.50).toVector().subtract(locOfSpawn.add(0.50, 0.50, 0.50).toVector()).normalize().multiply(1.35)); 
		tnt.setFuseTicks((int) (20 * 3.5));
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e){
	    Player player = e.getPlayer();
	    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	        final Block clicked = e.getClickedBlock();
	        if(clicked.getType() == Material.STONE_BUTTON){
	        	Location locSpawn = null;
	        	Location locShootTo = null;
	        	if(clicked.getRelative(BlockFace.SOUTH).getType() == Material.getMaterial(159)){
	        		locSpawn = clicked.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).getLocation();
	        		locShootTo = locSpawn.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH).getLocation();
	        	}
	        	if(clicked.getRelative(BlockFace.EAST).getType() == Material.getMaterial(159)){
	        		locSpawn = clicked.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN).getLocation();
	        		locShootTo = locSpawn.getBlock().getRelative(BlockFace.EAST).getRelative(BlockFace.EAST).getLocation();
	        	}
	        	if(clicked.getRelative(BlockFace.NORTH).getType() == Material.getMaterial(159)){
	        		locSpawn = clicked.getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN).getLocation();
	        		locShootTo = locSpawn.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH).getLocation();
	        	}
	        	if(clicked.getRelative(BlockFace.WEST).getType() == Material.getMaterial(159)){
	        		locSpawn = clicked.getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN).getLocation();
	        		locShootTo = locSpawn.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.WEST).getLocation();
	        	}
	        	
	        	if(locSpawn != null && locShootTo != null){
	        		if(!cannonCD.contains(clicked.getLocation())){
	        		launchCannon(player, locSpawn, locShootTo);
	        		cannonCD.add(clicked.getLocation());
	        		Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
	        			public void run(){
	        				cannonCD.remove(clicked.getLocation());
	        			}
	        		}, 20 * 10);
	        		}else{
	        			player.sendMessage(ChatColor.GRAY + "Cannon is still reloading!");
	        		}
	        	}
	        }
	}
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent e){
		if(e.getEntity().hasMetadata("cannon")){
			TNTPrimed tnt = e.getEntity().getWorld().spawn(e.getEntity().getLocation(), TNTPrimed.class);
			tnt.setFuseTicks(1);
		}
	}

}
