package me.sneaky.listeners;

import me.sneaky.Main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class diaBlockListener implements Listener{
	
	  final Main p;
	  

	  public diaBlockListener(Main instance)
	  {
	    this.p = instance;
	  }
	
	public void kitMain(Player p){
		this.p.util.Clear(p);
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
		chest.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
	    legs.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemStack boots = new ItemStack(Material.IRON_BOOTS);
		boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		p.getInventory().setArmorContents(new ItemStack[] {
				 boots,
				 legs,
        		 chest,
        		 helmet,	         		 
         });
		p.getInventory().addItem(sword);
		this.p.util.giveSoup(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999999, 1));
		
	}
	
	   @EventHandler
	   public void clearKit(PlayerMoveEvent e){
		   Player player = e.getPlayer();
		   Block b = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		   if(b.getType() == Material.DIAMOND_BLOCK || b.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIAMOND_BLOCK){
			   Block s = b.getLocation().add(0, -1, 0).getBlock();
			    if ((s.getState() instanceof Sign)) {
				   Sign sign = (Sign) s.getState();

				   if(sign.getLine(0).equalsIgnoreCase("sKits")){
					   if(sign.getLine(1).equalsIgnoreCase("clear")){
							this.p.util.Clear(player);
					   	  }else
								   
							if(sign.getLine(1).equalsIgnoreCase("main")){
							   this.p.util.Clear(player);
									      
							  kitMain(player);
					 }
				   }
		       }
			}
	   }
}

