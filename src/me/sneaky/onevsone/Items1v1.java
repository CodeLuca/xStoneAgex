package me.sneaky.onevsone;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items1v1 {
	
	public static ItemStack HardcoreGUI(){
		ItemStack item = new ItemStack(Material.POTION, 1, (short) 8233);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Hardcore 1v1");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Diamond Sword Sharpness 1");
		lore.add("Full Iron Armor");
		lore.add("Speed And Strength 2");
		lore.add("1 Hotbar Of Soup");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static ItemStack NormalGUI(){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Normal 1v1");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Diamond Sword Sharpness 1");
		lore.add("Full Iron Armor");
		lore.add("1 Hotbar Of Soup");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static ItemStack EliteGUI(){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Elite 1v1");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Diamond Sword");
		lore.add("Full Iron Armor");
		lore.add("1 Hotbar Of Soup");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static ItemStack HGGUI(){
		ItemStack item = new ItemStack(Material.IRON_SWORD);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "HG 1v1");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Iron Sword");
		lore.add("Full Iron Armor");
		lore.add("1 Hotbar Of Soup");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static ItemStack EarlyHGGUI(){
		ItemStack item = new ItemStack(Material.STONE_SWORD);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "EarlyHG 1v1");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Stone Sword");
		lore.add("No Armor");
		lore.add("1 Hotbar Of Soup");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public static ItemStack Stick1v1(){
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "1v1 Stick");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Right Click Someone To Ask Him For A 1v1");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	

}
