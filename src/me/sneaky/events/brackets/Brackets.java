package me.sneaky.events.brackets;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Brackets implements CommandExecutor {
	
	  final Main p;
	  

	  public Brackets(Main instance)
	  {
	    this.p = instance;
	  }
	  public void tpToArena(Player target1, Player target2){
		  double x1 = p.getConfig().getDouble("events.brackets.pos.1.x");
		  double y1 = p.getConfig().getDouble("events.brackets.pos.1.y");
		  double z1 = p.getConfig().getDouble("events.brackets.pos.1.z");
		  
		  double x2 = p.getConfig().getDouble("events.brackets.pos.2.x");
		  double y2 = p.getConfig().getDouble("events.brackets.pos.2.y");
		  double z2 = p.getConfig().getDouble("events.brackets.pos.2.z");
		  
		  Location loc1 = new Location(target1.getWorld(), x1, y1, z1);
		  Location loc2 = new Location(target2.getWorld(), x2, y2, z2);
		  
		  target1.teleport(loc1);
		  target2.teleport(loc2);
		  p.chat.sendBroadcast(ChatColor.GRAY + "Brackets: " + ChatColor.GREEN + target1.getName() + ChatColor.GRAY +  " VS " + ChatColor.GREEN + target2.getName());
	  }
	  
	  public void tpToBracketsSpawn(Player target){
		  double x = p.getConfig().getDouble("events.brackets.spawn.x");
		  double y = p.getConfig().getDouble("events.brackets.spawn.y");
		  double z = p.getConfig().getDouble("events.brackets.spawn.z");
		  
		  Location loc = new Location(target.getWorld(), x, y, z);
		  
		  target.teleport(loc);
		  
	      p.util.Clear(target);
		  
		  p.chat.sendBroadcast(ChatColor.GRAY + "Brackets: " + ChatColor.GREEN + target.getName() + ChatColor.GRAY + " Won!");
	  }
	  
	  public void giveEarlyHG(Player player){
		  final ItemStack sword = new ItemStack(Material.STONE_SWORD);
		  
		  player.getInventory().setHelmet(null);
		  player.getInventory().setChestplate(null);
		  player.getInventory().setLeggings(null);
		  player.getInventory().setBoots(null);
		  player.getInventory().setItem(0, sword);
	  }
	  
	  public void giveDefault(Player player){
		  final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		  final ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		  final ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
		  final ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
		  final ItemStack boots = new ItemStack(Material.IRON_BOOTS);
		  
		  sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		  
		  player.getInventory().setHelmet(helmet);
		  player.getInventory().setChestplate(chest);
		  player.getInventory().setLeggings(legs);
		  player.getInventory().setBoots(boots);
		  player.getInventory().setItem(0, sword);
	  }
	  
	  public void giveHG(Player player){
		  final ItemStack sword = new ItemStack(Material.IRON_SWORD);
		  final ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		  final ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
		  final ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
		  final ItemStack boots = new ItemStack(Material.IRON_BOOTS);
		  
		  player.getInventory().setHelmet(helmet);
		  player.getInventory().setChestplate(chest);
		  player.getInventory().setLeggings(legs);
		  player.getInventory().setBoots(boots);
		  player.getInventory().setItem(0, sword);
	  }
	  
	  public void giveHardcore(Player player){
		  final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		  final ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		  final ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
		  final ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
		  final ItemStack boots = new ItemStack(Material.IRON_BOOTS);
		  
		  sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		  
		  player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
		  player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 1));
		  
		  player.getInventory().setHelmet(helmet);
		  player.getInventory().setChestplate(chest);
		  player.getInventory().setLeggings(legs);
		  player.getInventory().setBoots(boots);
		  player.getInventory().setItem(0, sword);
	  }
	  
	  
	  @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
	    		p.chat.sendMessagePlayer(player, "Usage /Brackets [type] [player] [player] [refill: true/false]");
	    		p.chat.sendMessagePlayer(player, "Types: EarlyHG(1), Default(2), Hardcore(3), HG(4)");
		    }
		    if(args.length == 2){
		    	if(p.util.hasPermission(player, "brackets.host")) return true;
		    	if(args[0].equalsIgnoreCase("winner")){
		    		Player target = Bukkit.getServer().getPlayerExact(args[1]);
			    	if(target == null){
			    		return true;
			    	}
			    	this.tpToBracketsSpawn(target);
		    	}
		    	if(args[0].equalsIgnoreCase("pos")){
		    		Location loc = player.getLocation();
		    		if(args[1].equalsIgnoreCase("1")){
		    			p.getConfig().set("events.brackets.pos.1.x", loc.getX());
		    			p.getConfig().set("events.brackets.pos.1.y", loc.getY());
		    			p.getConfig().set("events.brackets.pos.1.z", loc.getZ());
		    			p.saveConfig();
		    			p.chat.sendMessagePlayer(player, "Brackets Pos 1 Set");
		    		}
		    		if(args[1].equalsIgnoreCase("2")){
		    			p.getConfig().set("events.brackets.pos.2.x", loc.getX());
		    			p.getConfig().set("events.brackets.pos.2.y", loc.getY());
		    			p.getConfig().set("events.brackets.pos.2.z", loc.getZ());
		    			p.saveConfig();
		    			p.chat.sendMessagePlayer(player, "Brackets Pos 2 Set");
		    		}
		    		if(args[1].equalsIgnoreCase("spawn")){
		    			p.getConfig().set("events.brackets.spawn.x", loc.getX());
		    			p.getConfig().set("events.brackets.spawn.y", loc.getY());
		    			p.getConfig().set("events.brackets.spawn.z", loc.getZ());
		    			p.saveConfig();
		    			p.chat.sendMessagePlayer(player, "Brackets Spawn Set");
		    		}
		    	}
		    }
		    if(args.length == 4){
		    	if(p.util.hasPermission(player, "brackets.host")) return true;
		    	Player target1 = Bukkit.getServer().getPlayerExact(args[1]);
		    	Player target2 = Bukkit.getServer().getPlayerExact(args[2]);
		    	if(target1 == null || target2 == null){
		    		p.chat.sendMessagePlayer(player, "Usage /Brackets [Type Number] [player] [player] [refill: true/false]");
		    		p.chat.sendMessagePlayer(player, "Types: EarlyHG(1), Default(2), Hardcore(3), HG(4)");
		    		return true;
		    	}
		    	
		    	p.util.Clear(target1);
		    	p.util.Clear(target2);
		    	
		    	if(Integer.valueOf(args[0]) == 1){
		    		this.giveEarlyHG(target1);
		    		this.giveEarlyHG(target2);
		    	}else
			    	if(Integer.valueOf(args[0]) == 2){
			    		this.giveDefault(target1);
			    		this.giveDefault(target2);
			    	}else
				    	if(Integer.valueOf(args[0]) == 3){
				    		this.giveHardcore(target1);
				    		this.giveHardcore(target2);
				    	}else
					    	if(Integer.valueOf(args[0]) == 4){
					    		this.giveHG(target1);
					    		this.giveHG(target2);
					    	}else{
					    		this.giveDefault(target1);
					    		this.giveDefault(target2);
					    	}
		    	
		    	if(Boolean.valueOf(args[3]) == true){
		    		p.util.giveSoup(target1);
		    		p.util.giveSoup(target2);
		    	}else{
		    		p.util.giveSoup1Bar(target1);
		    		p.util.giveSoup1Bar(target2);
		    	}
		    	
		    	this.tpToArena(target1, target2);
		    	
		    	
		    }
		    return false;
            	}

	  }

