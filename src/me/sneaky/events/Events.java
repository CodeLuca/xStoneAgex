package me.sneaky.events;

import java.util.ArrayList;

import me.sneaky.events.anvil.UtilsAnvil;
import me.sneaky.events.chickenwars.UtilsChickenWars;
import me.sneaky.events.deathrace.UtilsDeathRace;
import me.sneaky.events.hg.UtilsHG;
import me.sneaky.events.oitc.UtilsOITC;
import me.sneaky.events.tntrun.UtilsTNTRun;

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

public class Events implements Listener {
	
	public static Minigames eventHosted = null;
	
	public static ArrayList<Player> playersInEvent = new ArrayList<Player>();
	
	public static int eventTask = 0;
	
	public static void addToEvent(Player player){
		if(!playersInEvent.contains(player)){
			playersInEvent.add(player);
		}
	}
	
	public static void removeFromEvent(Player player){
		if(playersInEvent.contains(player)){
			playersInEvent.remove(player);
		}
	}
	
	
	static UtilsHG hg = new UtilsHG();
	static UtilsTNTRun tntrun = new UtilsTNTRun();
	static UtilsAnvil anvil = new UtilsAnvil();
	static UtilsDeathRace drace = new UtilsDeathRace();
	static UtilsChickenWars cwars = new UtilsChickenWars();
	static UtilsOITC oitc = new UtilsOITC();
	
	public static void newEvent(){
		/**int rand = new Random().nextInt(5);
		if(rand == 0){
			hg.countDown();
		}
		if(rand == 1){
			tntrun.countDown();
		}
		if(rand == 2){
			anvil.countDown();
		}
		if(rand == 3){
			drace.countDown();
		}
		if(rand == 4){
			cwars.countDown();
		}
		if(rand == 5){
			oitc.countDown();
		}
		**/
	}
	
	public enum Minigames{
		HG,
		ChickenWars,
		TNTRun,
		Anvil,
		OITC;
	}
	
	
	public ItemStack HG(){
		ItemStack item = new ItemStack(Material.STONE_SWORD);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "HG");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click Here To Host");
		lore.add(ChatColor.GREEN + "HG");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public ItemStack ChickenWars(){
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Chicken Wars");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click Here To Host");
		lore.add(ChatColor.GREEN + "Chicken Wars");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public ItemStack TNTRun(){
		ItemStack item = new ItemStack(Material.TNT);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "TNT Run");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click Here To Host");
		lore.add(ChatColor.GREEN + "TNT Run");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public ItemStack Anvil(){
		ItemStack item = new ItemStack(Material.ANVIL);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Anvil");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click Here To Host");
		lore.add(ChatColor.GREEN + "Anvil");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public ItemStack OITC(){
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "One In The Chamber");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click Here To Host");
		lore.add(ChatColor.GREEN + "One In The Chamber");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	public ItemStack DeathRace(){
		ItemStack item = new ItemStack(Material.LAVA);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + "Deat hRace");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click Here To Host");
		lore.add(ChatColor.GREEN + "Death Race");
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		return item;
	}
	
	public void openEventGUI(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.RED + ChatColor.BOLD.toString() + "Events");
		for(int i = 0; i < 26; i++){
		inv.setItem(i, new ItemStack(Material.IRON_FENCE));
		}
		
		inv.setItem(10, HG());
		inv.setItem(11, ChickenWars());
		inv.setItem(12, TNTRun());
		inv.setItem(13, Anvil());
		inv.setItem(14, OITC());
		inv.setItem(15, DeathRace());
		player.openInventory(inv);
	}
	
	

	@EventHandler
	public void Inventory(final InventoryClickEvent event){
		final Player player = (Player) event.getWhoClicked();
			if(event.getInventory().getTitle().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Events")){
				if(event.getCurrentItem() != null){
				if(event.getCurrentItem().getType() != null){
					if(event.getCurrentItem().getType() != Material.AIR){
						if(event.getCurrentItem().equals(HG())){
							player.chat("/hg host");
						}
						if(event.getCurrentItem().equals(ChickenWars())){
							player.chat("/cwars host");
						}
						if(event.getCurrentItem().equals(TNTRun())){
							player.chat("/tntrun host");
						}
						if(event.getCurrentItem().equals(Anvil())){
							player.chat("/anvil host");
						}
						if(event.getCurrentItem().equals(OITC())){
							player.chat("/oitc host");
						}
						if(event.getCurrentItem().equals(DeathRace())){
							player.chat("/drace host");
						}
						
						event.setCancelled(true);
					}
				}
			}
			}
		}
	
      @EventHandler
      public void onSignChange(SignChangeEvent e) {
              if (e.getLine(0).equalsIgnoreCase("event")) {
                      e.setLine(0, ChatColor.GREEN + "" + ChatColor.BOLD + "Event");
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
	  			if (s.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Event")) {
	  				if (s.getLine(1).equalsIgnoreCase(ChatColor.BLUE + "" + ChatColor.BOLD + "GUI")) {
	  					openEventGUI(player);
	  				}
	  		    }
	  		    }
	  			}
	
	
}
