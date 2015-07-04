package me.sneaky.onevsone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.sneaky.Main;

public class Utils1v1 {
	
	  final Main p;
	  

	  public Utils1v1(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  public enum Types1v1{
		  Hardcore,
		  Normal,
		  Elite,
		  HG,
		  EarlyHG,
		  Ranked;
	  }
	  
	  public void openPage1v1(Player player){
		  Inventory inv = Bukkit.createInventory(null, 27, ChatColor.RED + ChatColor.BOLD.toString() + "1v1");
		  for(int i = 0; i < 27; i ++){
			  inv.setItem(i, new ItemStack(Material.IRON_FENCE));
		  }
		  inv.setItem(11, Items1v1.HardcoreGUI());
		  inv.setItem(12, Items1v1.NormalGUI());
		  inv.setItem(13, Items1v1.EliteGUI());
		  inv.setItem(14, Items1v1.HGGUI());
		  inv.setItem(15, Items1v1.EarlyHGGUI());
		  player.openInventory(inv);
	  }
	  
	  public void openPageCustom1v1(Player player){
		  Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + ChatColor.BOLD.toString() + "1v1");
		  for(int i = 0; i < 54; i ++){
			  inv.setItem(i, new ItemStack(Material.IRON_FENCE));
		  }
		  
		  
		  
		  player.openInventory(inv);
	  }
	  
	  public boolean isInArena(Player player){
		  return p.util.arena.contains(player);
	  }
	  
	  public boolean isIn1v1(Player player){
		  return p.util.onevsone.get(player) != null;
	  }
	  
	  public void requestPlayer(Player asker, Player requested, Types1v1 type){
		  p.chat.sendCustomMSG(asker, "1v1", "You Have Requested " + requested.getName() + " For A " + type.toString() + " 1v1");
		  
		  p.chat.sendCustomMSG(requested, "1v1", asker.getName() + " Requested You For A " + type.toString() + " 1v1");
		  p.chat.sendCustomMSG(requested, "1v1", "Punch Him With Your Stick To Accept Him");
		  
		  p.util.request.put(asker, requested);
		  p.util.type1v1.put(asker, type);
	  }
	  
	  public void acceptPlayer(Player player, Player punched){
		  if(p.util.request.get(punched) != null){
			  if(p.util.request.get(punched) == player){
				  p.util.request.put(punched, null);
				  p.util.onevsone.put(punched, player);
				  p.util.onevsone.put(player, punched);
				  tpToArena(punched, player);
				  hidePlayers(punched, player);
				  Start1v1(punched, player, p.util.type1v1.get(punched));
				  
			  }
		  }
	  }
	  
	  public void hidePlayers(Player player1, Player player2){
		  for(Player pl : Bukkit.getServer().getOnlinePlayers()){
			  pl.hidePlayer(player1);
			  pl.hidePlayer(player2);
			  
			  player1.hidePlayer(pl);
			  player2.hidePlayer(pl);
		  }
		  player1.showPlayer(player2);
		  player2.showPlayer(player1);
	  }
	  
	  public void showPlayers(Player player1, Player player2){
		  for(Player pl : Bukkit.getServer().getOnlinePlayers()){
			  pl.showPlayer(player1);
			  pl.showPlayer(player2);
			  
			  player1.showPlayer(pl);
			  player2.showPlayer(pl);
		  }
	  }
	  
	  public void tpTo1v1(Player player){
		  double x = p.getConfig().getDouble("1v1.spawn.x");
		  double y = p.getConfig().getDouble("1v1.spawn.y");
		  double z = p.getConfig().getDouble("1v1.spawn.z");
		  
		  Location loc = new Location(player.getWorld(), x, y, z);
		  player.teleport(loc);
		  
		  p.util.Clear(player);
		  
		  player.setHealth(20D);
		  
		  player.getInventory().addItem(Items1v1.Stick1v1());
		  
		  if(!isInArena(player)){
			  p.util.arena.add(player);
		  }
	  }
	  
	  public void tpToArena(Player player1, Player player2){
		  double x1 = p.getConfig().getDouble("1v1.arena.pos.1.x");
		  double y1 = p.getConfig().getDouble("1v1.arena.pos.1.y");
		  double z1 = p.getConfig().getDouble("1v1.arena.pos.1.z");
		  
		  double x2 = p.getConfig().getDouble("1v1.arena.pos.2.x");
		  double y2 = p.getConfig().getDouble("1v1.arena.pos.2.y");
		  double z2 = p.getConfig().getDouble("1v1.arena.pos.2.z");
		  
		  Location loc1 = new Location(player1.getWorld(), x1, y1, z1);
		  Location loc2 = new Location(player1.getWorld(), x2, y2, z2);
		  
		  player1.teleport(loc1);
		  player2.teleport(loc2);
		  
	  }
	  
	  public void Start1v1(Player player1, Player player2, Types1v1 type){
		  
		  ItemStack helmet = new ItemStack(Material.AIR);
		  ItemStack chestplate = new ItemStack(Material.AIR);
		  ItemStack leggings = new ItemStack(Material.AIR);
		  ItemStack boots = new ItemStack(Material.AIR);
		  ItemStack sword = new ItemStack(Material.AIR);
		  
		  PotionEffect pot1 = null;
		  PotionEffect pot2 = null;
		  
		  switch (type){
		case EarlyHG:
			sword = new ItemStack(Material.STONE_SWORD);
			break;
			
		case Elite:
			sword = new ItemStack(Material.DIAMOND_SWORD);
			helmet = new ItemStack(Material.IRON_HELMET);
			chestplate = new ItemStack(Material.IRON_CHESTPLATE);
			leggings = new ItemStack(Material.IRON_LEGGINGS);
			boots = new ItemStack(Material.IRON_BOOTS);
			break;
			
		case HG:
			sword = new ItemStack(Material.IRON_SWORD);
			helmet = new ItemStack(Material.IRON_HELMET);
			chestplate = new ItemStack(Material.IRON_CHESTPLATE);
			leggings = new ItemStack(Material.IRON_LEGGINGS);
			boots = new ItemStack(Material.IRON_BOOTS);
			break;
			
		case Hardcore:
			sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			helmet = new ItemStack(Material.IRON_HELMET);
			chestplate = new ItemStack(Material.IRON_CHESTPLATE);
			leggings = new ItemStack(Material.IRON_LEGGINGS);
			boots = new ItemStack(Material.IRON_BOOTS);
			pot1 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 1);
			pot2 = new PotionEffect(PotionEffectType.SPEED, 9999999, 1);
			break;
			
		case Normal:
			sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			helmet = new ItemStack(Material.IRON_HELMET);
			chestplate = new ItemStack(Material.IRON_CHESTPLATE);
			leggings = new ItemStack(Material.IRON_LEGGINGS);
			boots = new ItemStack(Material.IRON_BOOTS);
			break;
			
		case Ranked:
			sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			helmet = new ItemStack(Material.IRON_HELMET);
			chestplate = new ItemStack(Material.IRON_CHESTPLATE);
			leggings = new ItemStack(Material.IRON_LEGGINGS);
			boots = new ItemStack(Material.IRON_BOOTS);
			pot1 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999999, 1);
			pot2 = new PotionEffect(PotionEffectType.SPEED, 9999999, 1);
			break;
			
		default:
			break;
		  
		  }
		  
		  for(int i = 0; i < 9; i++){
			  player1.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
		  }
		  
		  for(int i = 0; i < 9; i++){
			  player2.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
		  }
		  
		  player1.getInventory().setItem(0, sword);
		  player1.getInventory().setHelmet(helmet);
		  player1.getInventory().setChestplate(chestplate);
		  player1.getInventory().setLeggings(leggings);
		  player1.getInventory().setBoots(boots);
		  
		  player2.getInventory().setItem(0, sword);
		  player2.getInventory().setHelmet(helmet);
		  player2.getInventory().setChestplate(chestplate);
		  player2.getInventory().setLeggings(leggings);
		  player2.getInventory().setBoots(boots);
		  
		  if(pot1 != null){
			  player1.addPotionEffect(pot1);
			  player2.addPotionEffect(pot1);
		  }
		  
		  if(pot2 != null){
			  player1.addPotionEffect(pot2);
			  player2.addPotionEffect(pot2);
		  }
		  
		  p.chat.sendCustomMSG(player1, "1v1", "Your 1v1 Against " + player2.getName() + " Started");
		  p.chat.sendCustomMSG(player2, "1v1", "Your 1v1 Against " + player1.getName() + " Started");
		  
		  
	  }

}
