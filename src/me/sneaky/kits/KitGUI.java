package me.sneaky.kits;

import java.util.ArrayList;

import me.sneaky.Main;
import me.sneaky.kits.Kits.sKits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitGUI implements Listener{
	
	  final Main p;
	  

	  public KitGUI(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  
	  public ItemStack choose(){
		  ItemStack item = new ItemStack(Material.FIRE);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Choose This Kit");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  public ItemStack donate(){
		  ItemStack item = new ItemStack(Material.GOLD_INGOT);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Press Here To Buy The Kit From Enjin");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  public ItemStack buykit(sKits kit){
		  ItemStack item = new ItemStack(Material.ANVIL);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Press Here To Buy The Kit With In Game Money");
		  ArrayList<String> lore = new ArrayList<String>();
		  lore.add(ChatColor.GOLD + "This Kit Costs: $" + kit.getPrice());
		  iMeta.setLore(lore);
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  public ItemStack info(sKits kit){
		  ItemStack item = new ItemStack(Material.SIGN);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Kit Info");
		  iMeta.setLore(p.KitsClass.getKitDesc(kit));
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  public ItemStack kitItems(){
		  ItemStack item = new ItemStack(Material.COMPASS);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Kit Items");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  @SuppressWarnings("deprecation")
	public ItemStack nothing(){
		  ItemStack item = new ItemStack(Material.getMaterial(102));
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("#");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  public ItemStack back(){
		  ItemStack item = new ItemStack(Material.PAPER);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Back To Kit Page");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  public ItemStack backBuyKit(){
		  ItemStack item = new ItemStack(Material.PAPER);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Back To Buyable Kit Page");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  public ItemStack nextPage(){
		  ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Next Page");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  public ItemStack backPage(){
		  ItemStack item = new ItemStack(Material.BLAZE_POWDER);
		  ItemMeta iMeta = item.getItemMeta();
		  iMeta.setDisplayName("Back Page");
		  item.setItemMeta(iMeta);
		  return item;
	  }
	  
	  
	  public void openKitYourPage(Player player){
			Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + ChatColor.BOLD.toString() + "Your Kits");
			for(int i = 0; i < 9; i++){
				if(inv.getItem(i) == null){
				inv.setItem(i, nothing());
				}
			}
			inv.setItem(8, nextPage());
			for(sKits kit : sKits.values()){
				if(player.hasPermission("skits.kit." + kit.toString().toLowerCase())){
				ItemStack item = kit.getItem();
				
			    ItemMeta iMeta = item.getItemMeta();
			    
			    iMeta.setDisplayName(ChatColor.GOLD + kit.toString());
			    
			    iMeta.setLore(p.KitsClass.getKitDesc(kit));
			    
			    item.setItemMeta(iMeta);
				
				inv.addItem(item);
				}
			}
			player.closeInventory();
			player.openInventory(inv);
	  }
	  
	  public void openKitOtherPage(Player player){
			Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + ChatColor.BOLD.toString() + "Other Kits");
			for(int i = 0; i < 9; i++){
				if(inv.getItem(i) == null){
				inv.setItem(i, nothing());
				}
			}
			inv.setItem(0, backPage());
			for(sKits kit : sKits.values()){
				if(!player.hasPermission("skits.kit." + kit.toString().toLowerCase())){
				ItemStack item = kit.getItem();
				
			    ItemMeta iMeta = item.getItemMeta();
			    
			    iMeta.setDisplayName(ChatColor.GOLD + kit.toString());
			    
			    iMeta.setLore(p.KitsClass.getKitDesc(kit));
			    
			    item.setItemMeta(iMeta);
				
				inv.addItem(item);
				}
			}
			player.closeInventory();
			player.openInventory(inv);
	  }
	  
	  public void openBuyKitPage(Player player){
			Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + ChatColor.BOLD.toString() + "Buyable Kits");
			for(sKits kit : sKits.values()){
				if(!player.hasPermission("skits.kit." + kit.toString().toLowerCase())){
				ItemStack item = kit.getItem();
				
			    ItemMeta iMeta = item.getItemMeta();
			    
			    iMeta.setDisplayName(ChatColor.GOLD + kit.toString());
			    
			    iMeta.setLore(p.KitsClass.getKitDesc(kit));
			    
			    item.setItemMeta(iMeta);
				
				inv.addItem(item);
				}
			}
			player.closeInventory();
			player.openInventory(inv);
	  }
	  
	  public void openPageForKit(Player player, sKits kit, boolean b){
			Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + ChatColor.BOLD.toString() + kit.toString());
			if(b == true){
			inv.setItem(4, back());
			}else{
			inv.setItem(4, backBuyKit());	
			}
			if(player.hasPermission("skits.kit." + kit.toString().toLowerCase()) || player.isOp()){
			inv.setItem(13, choose());
			}else{
			inv.setItem(13, donate());
			inv.setItem(13, buykit(kit));
			}
			inv.setItem(31, info(kit));
			inv.setItem(37, kitItems());
			int c = 0;
			for(int i = 46; i < 48; i++){
				inv.setItem(i, p.KitsClass.kitItem(kit)[c]);
				c++;
			}
			for(int i = 0; i < 54; i++){
				if(inv.getItem(i) == null){
				inv.setItem(i, nothing());
				}
			}
			player.closeInventory();
			player.openInventory(inv);
	  }

		@EventHandler
		public void Inventory(final InventoryClickEvent event){
			final Player player = (Player) event.getWhoClicked();
				if(event.getInventory().getTitle().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Your Kits") || 
						event.getInventory().getTitle().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Other Kits")|| 
						event.getInventory().getTitle().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Buyable Kits")){
					if(event.getCurrentItem() != null){
					if(event.getCurrentItem().getType() != null){
						if(event.getCurrentItem().getType() != Material.AIR){
							event.setCancelled(true);
							if(event.getCurrentItem().equals(nextPage())){
								openKitOtherPage(player);
							}
							if(event.getCurrentItem().equals(backPage())){
								openKitYourPage(player);
							}
							for(Kits.sKits kit : Kits.sKits.values()){
								String kName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
								if(kName.equals(kit.toString())){
									if(event.getInventory().getTitle().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Buyable Kits")){
									openPageForKit(player, kit, false);
									}else{
										openPageForKit(player, kit, true);
									}
								}
							}
							event.setCancelled(true);
						}
					}
				}
				}else
					if(event.getCurrentItem() != null){
					if(event.getCurrentItem().getType() != null){
						if(event.getCurrentItem().getType() != Material.AIR){
					for(Kits.sKits kit : Kits.sKits.values()){
						String kName = ChatColor.stripColor(event.getInventory().getTitle());
						if(kName.equals(kit.toString())){
							event.setCancelled(true);
							if(event.getCurrentItem().equals(choose())){
								p.KitsClass.giveKit(player, kit);
								player.closeInventory();
							}
							if(event.getCurrentItem().equals(donate())){
								
							}
							if(event.getCurrentItem().equals(buykit(kit))){
								p.KitsClass.buyKit(player, kit);
							}
							if(event.getCurrentItem().equals(back())){
								openKitYourPage(player);
							}
							if(event.getCurrentItem().equals(backBuyKit())){
								openBuyKitPage(player);
							}
							event.setCancelled(true);
						}
					}
					}
					}
				}
			}
		
	      @EventHandler
	      public void onSignChange(SignChangeEvent e) {
	              if (e.getLine(0).equalsIgnoreCase("kit")) {
	                      e.setLine(0, ChatColor.GREEN + "" + ChatColor.BOLD + "Kit");
	                      String line1 = e.getLine(1);
	                      e.setLine(1, ChatColor.BLUE + "" + ChatColor.BOLD + line1);
	              }
	      }
		  
		  	@EventHandler
		  	public void KitSelect(PlayerInteractEvent e) {
		  		Player player = e.getPlayer();
		  		if (!((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.LEFT_CLICK_BLOCK))) return;
		  		if (e.getClickedBlock().getState() instanceof Sign) {
		  			Sign s = (Sign) e.getClickedBlock().getState();
		  			if (s.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Kit")) {
		  				if (s.getLine(1).equalsIgnoreCase(ChatColor.BLUE + "" + ChatColor.BOLD + "GUI")) {
		  					openKitYourPage(player);
		  				}
		  				if (s.getLine(1).equalsIgnoreCase(ChatColor.BLUE + "" + ChatColor.BOLD + "BuyKit")) {
		  					openBuyKitPage(player);
		  				}
						for(Kits.sKits kit : Kits.sKits.values()){
							if(ChatColor.stripColor(s.getLine(1)).equalsIgnoreCase(kit.toString())){
								p.KitsClass.giveKit(player, kit);
							}
						}
		  		    }
		  		    }
		  			}
		
	  

}
