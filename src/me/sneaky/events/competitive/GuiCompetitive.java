package me.sneaky.events.competitive;

import java.util.ArrayList;
import java.util.Arrays;

import me.sneaky.events.competitive.WeaponsCompetitve.gunType;
import me.sneaky.events.competitive.WeaponsCompetitve.guns;
import me.sneaky.events.competitive.WeaponsCompetitve.hitType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiCompetitive {
	//UtilsCompetitve hg = new UtilsCompetitve();
	
	WeaponsCompetitve weapons = new WeaponsCompetitve();
	
	  @SuppressWarnings("deprecation")
	public ItemStack Snipers(){
		  ItemStack item = new ItemStack(Material.getMaterial(38), 1, (short) 4);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName(ChatColor.GOLD + "Snipers");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  
		@SuppressWarnings("deprecation")
		public ItemStack Rifles(){
			  ItemStack item = new ItemStack(Material.getMaterial(38), 1, (short) 5);
			  ItemMeta iMeta = item.getItemMeta();
			  iMeta.setDisplayName(ChatColor.GOLD + "Rifles");
			  item.setItemMeta(iMeta);
			  return item;
		  }
		
		@SuppressWarnings("deprecation")
		public ItemStack Pistols(){
			  ItemStack item = new ItemStack(Material.getMaterial(38), 1, (short) 6);
			  ItemMeta iMeta = item.getItemMeta();
			  iMeta.setDisplayName(ChatColor.GOLD + "Pistols");
			  item.setItemMeta(iMeta);
			  return item;
		  }
		
		@SuppressWarnings("deprecation")
		public ItemStack SubMachineGuns(){
			  ItemStack item = new ItemStack(Material.getMaterial(38), 1, (short) 7);
			  ItemMeta iMeta = item.getItemMeta();
			  iMeta.setDisplayName(ChatColor.GOLD + "Sub Machine Guns");
			  item.setItemMeta(iMeta);
			  return item;
		  }
		
		public ItemStack Gear(){
			  ItemStack item = new ItemStack(Material.PAPER);
			  ItemMeta iMeta = item.getItemMeta();
			  iMeta.setDisplayName(ChatColor.GOLD + "Gear");
			  item.setItemMeta(iMeta);
			  return item;
		  }
	
		Integer[] SnipersSlot = {1, 2, 3, 4, 5, 6};		
		Integer[] RiflesSlot = {10, 11, 12, 13, 14};
		Integer[] SMGsSlot = {19, 20, 21, 22, 23};		
		Integer[] PistolSlot = {28, 29, 30, 31, 32};
		Integer[] GearSlot = {17, 26, 35, 44, 51};
		
		
	@SuppressWarnings("deprecation")
	public void openInv(Player player){
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + ChatColor.BOLD.toString() + "Competitive Items");
		for(int i = 0; i < 54; i++){
			inv.setItem(i, new ItemStack(Material.getMaterial(160)));	
		}
		inv.setItem(0, Snipers());
		
		inv.setItem(9, Rifles());
		
		inv.setItem(18, SubMachineGuns());
		
		inv.setItem(27, Pistols());
		
		//inv.setItem(36, Gear());
		
		int sniper = 0;
		int rifles = 0;
		int smg = 0;
		int pistol = 0;
		//int gear = 0;
		
		guns[] noCTGuns = {guns.AK47, guns.GLOCK, guns.G3SG1, guns.GALIL};
		guns[] noTGuns = {guns.M4A4, guns.USP, guns.SCAR20, guns.FAMAS};
		
		for(guns gun : guns.values()){
			if(UtilsCompetitve.CT.contains(player)){
				if(Arrays.asList(noCTGuns).contains(gun) || UtilsCompetitve.playerGun1.get(player) == gun || UtilsCompetitve.playerGun2.get(player) == gun){
					gun = null;
				}
			}
			if(UtilsCompetitve.T.contains(player)){
				if(Arrays.asList(noTGuns).contains(gun)){
					gun = null;
				}
			}
			  
			  if(gun != null){
			Material mat = null;
			switch (gun.getType()){
			case PISTOL:
				mat = Material.WOOD_HOE;
				break;
				
			case RIFLE:
				mat = Material.IRON_HOE;
				break;
				
			case SNIPER:
				mat = Material.DIAMOND_HOE;
				break;
				
			case SUB:
				mat = Material.STONE_HOE;
				break;
				
			default:
				break;
			
			}
			
			  ItemStack item = new ItemStack(mat);
			  ItemMeta iMeta = item.getItemMeta();
			  iMeta.setDisplayName(ChatColor.GOLD + gun.toString());
			  ArrayList<String> lore = new ArrayList<String>();
			  lore.add("Price: $" + gun.getPrice());
			  lore.add("Kill Reward: $" + gun.getKillPrice());
			  lore.add("Damage: " + weapons.getDamage(gun, hitType.HEAD, 0, false) / 10);
			  lore.add("Firerate: " + gun.getFirerate() + " Shots/Second");
			  lore.add("Reload Time: " + gun.getReloadTime() + " Shots/Second");
			  lore.add("Clip size: " + gun.getAmmo() + " || Ammo: " + gun.getMaxAmmo());
			  iMeta.setLore(lore);
			  item.setItemMeta(iMeta);
			if(gun.getType() == gunType.SNIPER){
				inv.setItem(SnipersSlot[sniper], item);
				sniper++;
			}
			if(gun.getType() == gunType.RIFLE){
				inv.setItem(RiflesSlot[rifles], item);
				rifles++;
			}
			if(gun.getType() == gunType.SUB){
				inv.setItem(SMGsSlot[smg], item);
				smg++;
			}
			if(gun.getType() == gunType.PISTOL){
				inv.setItem(PistolSlot[pistol], item);
				pistol++;
			}
		  }
		}
		player.openInventory(inv);
	}
	
}
