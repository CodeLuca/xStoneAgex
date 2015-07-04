package me.sneaky.feast;

import java.util.Random;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class feastListener {
	  
	  public static void repeatFeast(final Main p){
		  p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable(){
			public void run() {
				fillFeast(p);	
				
				  final double x = p.getConfig().getDouble("feast.mid.x");
				  final double y = p.getConfig().getDouble("feast.mid.y");
				  final double z = p.getConfig().getDouble("feast.mid.z");
				
				  p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
						public void run() {
							Bukkit.broadcastMessage(ChatColor.RED + "Feast will begin at (" + x + ", " + y + ", " + z + ") in 5 minutes");
						}
						}, 20 * 60 * 5);
				  
				  p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
						public void run() {
							Bukkit.broadcastMessage(ChatColor.RED + "Feast will begin at (" + x + ", " + y + ", " + z + ") in 3 minute");
						}
						}, 20 * 60 * 7);
				  
				  p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
						public void run() {
							Bukkit.broadcastMessage(ChatColor.RED + "Feast will begin at (" + x + ", " + y + ", " + z + ") in 1 minute");
						}
						}, 20 * 60 * 9);
				  
				  p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
						public void run() {
							Bukkit.broadcastMessage(ChatColor.RED + "Feast will begin at (" + x + ", " + y + ", " + z + ") in 30 seconds");
						}
						}, 20 * 60 * 9 + 20 * 30);
				  
				  p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
						public void run() {
							Bukkit.broadcastMessage(ChatColor.RED + "Feast will begin at (" + x + ", " + y + ", " + z + ") in 15 seconds");
						}
						}, 20 * 60 * 9 + 20 * 45);
				  
				  p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
						public void run() {
							Bukkit.broadcastMessage(ChatColor.RED + "Feast will begin at (" + x + ", " + y + ", " + z + ") in 10 seconds");
						}
						}, 20 * 60 * 9 + 20 * 50);
				  
				  p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable(){
						public void run() {
							Bukkit.broadcastMessage(ChatColor.RED + "Feast will begin at (" + x + ", " + y + ", " + z + ") in 5 seconds");
						}
						}, 20 * 60 * 9 + 20 * 55);
			}  
		  }, 0, 20 * 60 * 10);
	  }
	
	public static void fillFeast(Main p){
		Bukkit.broadcastMessage(ChatColor.RED + "The Feast has begun!");
		
		  World w = Bukkit.getServer().getWorld(p.getConfig().getString("feast.world"));
		
		  double x1 = p.getConfig().getDouble("feast.pos.1.x");
		  double y1 = p.getConfig().getDouble("feast.pos.1.y");
		  double z1 = p.getConfig().getDouble("feast.pos.1.z");
		  
		  double x2 = p.getConfig().getDouble("feast.pos.2.x");
		  double y2 = p.getConfig().getDouble("feast.pos.2.y");
		  double z2 = p.getConfig().getDouble("feast.pos.2.z");
		
        for(int xChest1 = (int)Math.min(x1, x2); xChest1 <= Math.max(x1, x2); xChest1++)
        {
           for(int yChest1 = (int)Math.min(y1, y2); yChest1 <= Math.max(y1, y2); yChest1++)
           {
               for(int zChest1 = (int)Math.min(z1, z2); zChest1 <= Math.max(z1, z2); zChest1++)
               {
                   if(w.getBlockAt(xChest1,yChest1,zChest1).getType() == Material.CHEST)
                   {
                   Chest chest = (Chest) w.getBlockAt(xChest1,yChest1,zChest1).getState();
                   
                   chest.getInventory().clear();
                   
                   Random rand = new Random();
                   
                   int pot1 = rand.nextInt(2);
                   int pot2 = rand.nextInt(2);
                   int pot3 = rand.nextInt(2);
                   int pot4 = rand.nextInt(2);
                   
                   ItemStack item1 = new ItemStack(Material.POTION, pot1 + 1, (short) 16418);  
                   ItemStack item2 = new ItemStack(Material.POTION, pot2 + 1, (short) 16420);
                   ItemStack item3 = new ItemStack(Material.POTION, pot3 + 1, (short) 16417);
                   ItemStack item4 = new ItemStack(Material.POTION, pot4 + 1, (short) 16460);
                   
                   ItemStack item5 = new ItemStack(Material.LEATHER_BOOTS);
                   ItemStack item6 = new ItemStack(Material.LEATHER_LEGGINGS); 
                   ItemStack item7 = new ItemStack(Material.LEATHER_CHESTPLATE);
                   ItemStack item8 = new ItemStack(Material.LEATHER_HELMET);
                   
                   int pearl = rand.nextInt(2);
                   ItemStack item9 = new ItemStack(Material.ENDER_PEARL, pearl + 1);
                   
                   int soup = rand.nextInt(12);
                   ItemStack item10 = new ItemStack(Material.MUSHROOM_SOUP, soup + 1);
                   
                   ItemStack[] item = new ItemStack[]{item1, item2, item3, item4, item5, item6, item7, item8, item9, item10};
                   
                   Random r = new Random();
                   int next = r.nextInt(4);
                   if(next == 0){
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
                   }
                   if(next == 1){
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
                   }
                   if(next == 2){
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
                   }
                   if(next == 3){
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
                   }
                   if(next == 4){
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
	                chest.getInventory().addItem(item[rand.nextInt(item.length)]);
                   }
                   
                   chest.update();
                   
                   
                 }
               }
            }
        }
	}
	

}
