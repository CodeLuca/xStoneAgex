package me.sneaky.custom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Casino {
	
	public void casino(Player player){
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + ChatColor.BOLD.toString() + "Casino");
		inv.setItem(0, new ItemStack(Material.APPLE));
		inv.setItem(9, new ItemStack(Material.GOLD_NUGGET, 10));
		
		inv.setItem(2, new ItemStack(Material.CARROT));
		inv.setItem(11, new ItemStack(Material.GOLD_NUGGET, 15));
		
		inv.setItem(4, new ItemStack(Material.POTATO));
		inv.setItem(13, new ItemStack(Material.GOLD_NUGGET, 20));
		
		inv.setItem(6, new ItemStack(Material.COAL));
		inv.setItem(15, new ItemStack(Material.GOLD_NUGGET, 30));
		
		inv.setItem(8, new ItemStack(Material.NETHER_STAR));
		inv.setItem(17, new ItemStack(Material.GOLD_NUGGET, 100));
	}

}
