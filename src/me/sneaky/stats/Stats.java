package me.sneaky.stats;

import me.sneaky.Main;
import me.sneaky.stats.killstreaks.Killstreak;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Stats implements CommandExecutor, Listener {
	
	  final Main p;
	  

	  public Stats(Main instance)
	  {
	    this.p = instance;
	  }
	
	public static ItemStack Kills(Player player, Main p) throws Exception
	{
	    ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
	    
	    itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
	    
	    ItemMeta iMeta = itemStack.getItemMeta();
	    
	    iMeta.setDisplayName(ChatColor.GOLD + player.getName() + "'s Kills: " + p.stats.getKills(player));
	    
	    itemStack.setItemMeta(iMeta);

	    return itemStack;
	    
	  }
	
	public static ItemStack Deaths(Player player, Main p) throws Exception
	{
	    ItemStack itemStack = new ItemStack(Material.SKULL_ITEM);
	    
	    itemStack.setDurability((short) 1);
	    
	    itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
	    
	    ItemMeta iMeta = itemStack.getItemMeta();
	    
	    iMeta.setDisplayName(ChatColor.GOLD + player.getName() + "'s Deaths: " + p.stats.getDeaths(player));
	    
	    itemStack.setItemMeta(iMeta);

	    return itemStack;
	    
	  }
	
	public static ItemStack KD(Player player, Main p) throws Exception
	{
	    ItemStack itemStack = new ItemStack(Material.COAL);
	    
	    itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
	    
	    ItemMeta iMeta = itemStack.getItemMeta();
	    
	    iMeta.setDisplayName(ChatColor.GOLD + player.getName() + "'s KD: " + p.stats.getKD(player));
	    
	    itemStack.setItemMeta(iMeta);

	    return itemStack;
	    
	  }
	
	public static ItemStack Credits(Player player, Main p) throws Exception
	{
	    ItemStack itemStack = new ItemStack(Material.GOLD_NUGGET);
	    
	    itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
	    
	    ItemMeta iMeta = itemStack.getItemMeta();
	    
	    iMeta.setDisplayName(ChatColor.GOLD + player.getName() + "'s Credits: " + p.stats.getCredits(player));
	    
	    itemStack.setItemMeta(iMeta);

	    return itemStack;
	    
	  }
	
	public static ItemStack Coins(Player player, Main p) throws Exception
	{
	    ItemStack itemStack = new ItemStack(Material.GOLD_NUGGET);
	    
	    itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
	    
	    ItemMeta iMeta = itemStack.getItemMeta();
	    
	    iMeta.setDisplayName(ChatColor.GOLD + player.getName() + "'s Coins: " + p.stats.getCredits(player));
	    
	    itemStack.setItemMeta(iMeta);

	    return itemStack;
	    
	  }
	
	
	public void openStats(Player player) throws Exception{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + ChatColor.BOLD.toString() + "Stats");
		inv.setItem(0, Kills(player, p));
		inv.setItem(1, Deaths(player, p));
		inv.setItem(4, KD(player, p));
		inv.setItem(8, Credits(player, p));
		
		player.openInventory(inv);
	}
	
	public void openStatsOther(Player target, Player player) throws Exception{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + ChatColor.BOLD.toString() + "Stats");
		inv.setItem(0, Kills(player, p));
		inv.setItem(1, Deaths(player, p));
		inv.setItem(4, KD(player, p));
		inv.setItem(8, Credits(player, p));
		
		target.openInventory(inv);
	}
	
	
	@EventHandler
	public void Inventory(final InventoryClickEvent event){
			if(event.getInventory().getTitle().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Stats")){
						event.setCancelled(true);
			}
		}
	
	
	@EventHandler
	public void join(PlayerJoinEvent e){
		Player player = e.getPlayer();
		
		p.stats.addPlayer(player);
	}
	
	
	@EventHandler
	public void deathEvent(PlayerDeathEvent e) throws Exception{
		Player player = e.getEntity();
		Entity killerEntity = player.getKiller();
		
		p.util.KillStreak.put(player, 0);
		p.stats.addDeaths(player);
		
		if(killerEntity instanceof Player){
			Player killer = (Player) killerEntity;
		if(killer != null){
			p.stats.addKillToKillStreak(killer);
			p.stats.addKills(killer);
			p.stats.addCredits(killer, 50);
			p.chat.sendMessagePlayer(killer, "You Recieved 50 Credits for Killing " + player.getName());
		}
		}
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player)sender;
		    if(args.length == 0){
		    	try {
					openStats(player);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }else
		    if(args.length == 1){
		    	Player target = Bukkit.getServer().getPlayer(args[0]);
		    	
		    	if(target == null){
		    		p.chat.sendMessagePlayer(player, "That Player Does Not Exist");
		    		return true;
		    	}
		    	
		    	try {
					openStatsOther(player, target);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    return false;
          	}
	
	

}
