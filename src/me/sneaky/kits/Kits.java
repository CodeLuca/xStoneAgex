package me.sneaky.kits;

import java.util.ArrayList;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kits {
	
	  final Main p;
	  

	  public Kits(Main instance)
	  {
	    this.p = instance;
	  }
	  
	
	public enum sKits{
		PvP(new ItemStack(Material.STONE_SWORD), 0),
		Archer(new ItemStack(Material.BOW), 5000),
		Stomper(new ItemStack(Material.IRON_BOOTS), 25000),
		Switcher(new ItemStack(Material.SNOW_BALL), 10000),
		Airbender(new ItemStack(Material.BLAZE_ROD), 5000),
		Berserker(new ItemStack(Material.SNOW_BALL), 7000),
		Blink(new ItemStack(Material.NETHER_STAR), 7000),
		Drunk(new ItemStack(Material.MILK_BUCKET), 20000),
		Dragon(new ItemStack(Material.SLIME_BALL), 17000),
		Endermage(new ItemStack(Material.PORTAL), 20000),
		Fisherman(new ItemStack(Material.FISHING_ROD), 20000),
		Gambler(new ItemStack(Material.WATCH), 8000),
		Grappler(new ItemStack(Material.LEASH), 22000),
		Hawk(new ItemStack(Material.FEATHER), 15000),
		Ironman(new ItemStack(Material.IRON_BLOCK), 10000),
		Kangaroo(new ItemStack(Material.FIREWORK), 25000),
		Leech(new ItemStack(Material.WEB), 5000),
		Pyro(new ItemStack(Material.FIRE), 15000),
		Reaper(new ItemStack(Material.WOOD_HOE), 10000),
		Scout(new ItemStack(Material.POTION), 10000),
		Summoner(new ItemStack(Material.BLAZE_POWDER), 15000),
		Thor(new ItemStack(Material.WOOD_AXE), 8000),
		Transfer(new ItemStack(Material.HOPPER), 10000),
		Witch(new ItemStack(Material.PUMPKIN), 14000),
		Wizard(new ItemStack(Material.COAL), 7000),
		Gladiator(new ItemStack(Material.IRON_FENCE), 22000),
		Ninja(new ItemStack(Material.NETHER_STAR), 6000),
		Satan(new ItemStack(Material.NETHER_FENCE), 20000),
		@SuppressWarnings("deprecation")
		Copycat(new ItemStack(Material.getMaterial(175)), 22000),
		Viper(new ItemStack(Material.SPIDER_EYE), 12000),
		Snail(new ItemStack(Material.INK_SACK), 12000),
		Anchor(new ItemStack(Material.IRON_BLOCK), 22500),
		Timelord(new ItemStack(Material.WATCH), 20000),
		Monk(new ItemStack(Material.BLAZE_ROD), 7000),
		Frosty(new ItemStack(Material.ICE), 8000),
		Grandpa(new ItemStack(Material.STICK), 6000),
		Tank(new ItemStack(Material.TNT), 10000),
		Jesus(new ItemStack(Material.BLAZE_POWDER), 20000);
		//Laser(new ItemStack(Material.REDSTONE_ORE), 20000);
		
		private ItemStack item;
		private int price;
		
		sKits(ItemStack item, int price){
			this.item = item;
			this.price = price;
		}
		
		public ItemStack getItem(){
			return item;
		}
		
		public int getPrice(){
			return price;
		}
	}
	
	public void buyKit(Player player, sKits kit){
		if(player.hasPermission("skits.kit." + kit.toString().toLowerCase())){
			p.chat.sendMessagePlayer(player, "You Already Have That Kit");
			return;
			}
			try {
				if(p.stats.getCredits(player) < kit.getPrice()){
					p.chat.sendMessagePlayer(player, "You Need "+ kit.getPrice()  +" Credits to Buy This Kit!");
				}else{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp " + player.getName() + " skits.kit." + kit.toString().toLowerCase());
				p.stats.removeCredits(player, kit.getPrice());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public ArrayList<String> getKitDesc(sKits kit){
		return kitDesc(kit);
	}
	
	public ArrayList<String> kitDesc(sKits kit){
		ArrayList<String> lore = new ArrayList<String>();
		switch(kit){
		case Airbender:
		    lore.add("Right Click Someone With Your Blaze Rod");
		    lore.add("To Launch Him Away");
			break;
			
		case Archer:
		    lore.add("Gives You A Bow");
			break;
			
		case Berserker:
		    lore.add("Kill People To Get Bloodlust");
			break;
			
		case Blink:
		    lore.add("Use Your Nether Star To Blink Away");
			break;
			
		case Dragon:
		    lore.add("Shoot Green Beams To Confuse Your Enemy");
			break;
			
		case Drunk:
		    lore.add("Drink Your Milk To Gain Strenght");
			break;
			
		case Endermage:
		    lore.add("Mage Players Up/Down To The Location");
		    lore.add("Were You Placed Your Portal");
			break;
			
		case Fisherman:
		    lore.add("Fish Players Towards You");
			break;
			
		case Gambler:
		    lore.add("Click On Your Watch To Earn");
		    lore.add("Random Potion Effects");
			break;
			
		case Grappler:
		    lore.add("Hook Onto Walls And Enemy's");
		    lore.add("And Launch Yourself Towards Them");
			break;
			
		case Hawk:
		    lore.add("Launch Up Using Your Feather");
		    lore.add("And Do Something Special To Other Players");
		    lore.add("When You Get Fall Damage");
			break;
			
		case Ironman:
		    lore.add("Hold Your Iron Block To Get");
		    lore.add("A Max Of 1 Heart Damage");
		    lore.add("Right Click Your Iron Block To");
		    lore.add("Shoot Your Iron Beam");
			break;
			
		case Kangaroo:
		    lore.add("Let's Jump");
			break;
			
		case Leech:
		    lore.add("Right Click Someone With Your Web");
		    lore.add("To Switch Health");
			break;
			
		case PvP:
		    lore.add("The Default PvP Kit");
			break;
			
		case Pyro:
		    lore.add("Shoot Fire Beams To Put");
		    lore.add("Your Enemy On Fire");
			break;
			
		case Reaper:
		    lore.add("Right Click a Wood Hoe");
		    lore.add("To Send a Beam of");
		    lore.add("Witherness to your enemies");
			break;
			
		case Scout:
		    lore.add("Gives You Speed 2");
			break;
			
		case Stomper:
		    lore.add("Transfer Your Fall Damage To");
		    lore.add("Nearby Players");
			break;
			
		case Summoner:
		    lore.add("Spawn Minions By Right Clicking The Ground");
			break;
			
		case Switcher:
		    lore.add("Switch Locations With Other Players");
		    lore.add("By Throwing a Snowball On Them");
			break;
			
		case Thor:
		    lore.add("Click On The Ground To");
		    lore.add("Strike Your Lightning");
			break;
			
		case Transfer:
		    lore.add("When You Get Stomped The Stomp Damage");
		    lore.add("Will Transfer To The Stomper");
		    lore.add("When You Get Endermaged The Endermage");
		    lore.add("Will Get Teleported Himself");
			break;
			
		case Witch:
		    lore.add("Spawn Potions Around You While Fighting");
			break;
			
		case Wizard:
		    lore.add("Shoot Magic Beams And Give Your Enemy");
		    lore.add("Negative Potion Effects");
			break;

		case Gladiator:
		    lore.add("1v1 players");
			break;
			
		case Ninja:
		    lore.add("Shift To Teleport To The Guy");
		    lore.add("You Hitted Last");
			break;
			
		case Satan:
		    lore.add("Right Click On Players To");
		    lore.add("Teleport Them In A Cage Of Lava");
		    lore.add("For 10 Seconds");
		    lore.add("But Watch Out Cause When He Gets");
		    lore.add("Out He Recieves Strenght 1 for 5 Seconds");
		    lore.add("And Speed 2 For 10 Seconds");
			break;
			
		case Copycat:
		    lore.add("Kill A Player To Recieve His");
		    lore.add("Kit And Inventory + A Full Inventory");
		    lore.add("Of Soup");
			break;
			
		case Viper:
		    lore.add("33% Chance Of Giving Someone Poison");
		    lore.add("For 3 Seconds");
			break;
			
		case Snail:
		    lore.add("33% Chance Of Giving Someone Slowness");
		    lore.add("For 3 Seconds");
			break;
			
		case Anchor:
		    lore.add("Give No Knockback And");
		    lore.add("Recieve No Knockback");
			break;
			
		case Timelord:
		    lore.add("Turn Invisible By Right Clicking Your Watch");
		    lore.add("And Freeze Players In A 5x5 Range Of You");
			break;
			
		case Monk:
		    lore.add("Right Click Someone To Switch The Item In His Hand");
		    lore.add("For A Random Item In His Inventory");
			break;
			
		case Frosty:
		    lore.add("Recieve Speed 2 For 5 Seconds If You Walk On Snow");
		    lore.add("Hit Someone With A Snowball To Give Him Blindness");
		    lore.add("Hit The Ground With A Snowball To Spawn Snow For 3 Seconds");
			break;
			
		case Grandpa:
		    lore.add("Start With A Knockback 2 Stick");
			break;
			
		case Tank:
		    lore.add("When You Kill Someone It Creates An");
		    lore.add("Explosion At The Victim's Location");
		    lore.add("Where You Are Immune To");
			break;
			
		case Jesus:
		    lore.add("When you die you get resurrected with 2 hotbars of soup");
			break;
			
	//	case Laser:
		//    lore.add("Lock onto someone with your laser and laser him");
		//	break;
			
		//case Pikachu:
		//    lore.add("Control Lightning To Kill People");
		//	break;
			
		default:
			break;
		}
		return lore;
	}
	
	public ItemStack[] kitItem(sKits kit){
		ItemStack item1 = new ItemStack(Material.AIR);
		ItemStack item2 = new ItemStack(Material.AIR);
		ItemStack item3 = new ItemStack(Material.AIR);
		
		switch (kit){
		case Airbender:
			item1 = new ItemStack(Material.BLAZE_ROD);
			break;
			
		case Archer:
			item1 = new ItemStack(Material.BOW);
			item2 = new ItemStack(Material.ARROW);
			item1.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			break;
			
		case Berserker:
			break;
			
		case Blink:
			item1 = new ItemStack(Material.NETHER_STAR);
			break;
			
		case Dragon:
			item1 = new ItemStack(Material.SLIME_BALL);
			break;
			
		case Drunk:
			item1 = new ItemStack(Material.MILK_BUCKET);
			break;
			
		case Endermage:
			item1 = new ItemStack(Material.PORTAL);
			break;
			
		case Fisherman:
			item1 = new ItemStack(Material.FISHING_ROD);
			break;
			
		case Gambler:
			item1 = new ItemStack(Material.WATCH);
			break;
			
		case Grappler:
			item1 = new ItemStack(Material.LEASH);
			break;
			
		case Hawk:
			item1 = new ItemStack(Material.FEATHER);
			break;
			
		case Ironman:
			item1 = new ItemStack(Material.IRON_BLOCK);
			break;
			
		case Kangaroo:
			item1 = new ItemStack(Material.FIREWORK);
			break;
			
		case Leech:
			item1 = new ItemStack(Material.WEB);
			break;
			
		case PvP:
			break;
			
		case Pyro:
			item1 = new ItemStack(Material.REDSTONE_TORCH_ON);
			break;
			
		case Reaper:
			item1 = new ItemStack(Material.WOOD_HOE);
			break;
			
		case Scout:
	        ItemStack Pot = new ItemStack(Material.POTION);
	        PotionMeta meta = (PotionMeta) Pot.getItemMeta();
	        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1), true);
	        Pot.setItemMeta(meta);
	        
			item1 = Pot;
			break;
			
		case Stomper:
			break;
			
		case Summoner:
			item1 = new ItemStack(Material.BLAZE_POWDER);
			break;
			
		case Switcher:
			item1 = new ItemStack(Material.SNOW_BALL, 10);
			break;
			
		case Thor:
			item1 = new ItemStack(Material.WOOD_AXE);
			break;
			
		case Transfer:
			break;
			
		case Witch:
			break;
			
		case Wizard:
			item1 = new ItemStack(Material.COAL);
			break;
			
		case Gladiator:
			item1 = new ItemStack(Material.IRON_FENCE);
			break;
			
		case Ninja:
			break;
			
		case Satan:
			item1 = new ItemStack(Material.NETHER_FENCE);
			break;
			
		case Copycat:
			break;
			
		case Viper:
			break;
			
		case Snail:
			break;
			
		case Anchor:
			break;
			
		case Timelord:
			item1 = new ItemStack(Material.WATCH);
			break;
			
		case Monk:
			item1 = new ItemStack(Material.BLAZE_ROD);
			break;
			
		case Frosty:
			item1 = new ItemStack(Material.SNOW_BALL, 16);
			item2 = new ItemStack(Material.SNOW_BALL, 16);
			break;
			
		case Grandpa:
			item1 = new ItemStack(Material.STICK);
			item1.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			break;
			
		case Tank:
			break;
			
		case Jesus:
			break;
			
		//case Laser:
		//	item1 = new ItemStack(Material.REDSTONE_ORE);
		//	break;
			
		//case Pikachu:
		//	item1 = new ItemStack(Material.NETHER_STAR);
		//	break;
			
		default:
			break;
		
		}
		ItemStack[] items = {item1, item2, item3};
		
		return items;
	}
	
	public void giveKit(Player player, sKits kit){
		
		p.util.Clear(player);
		
		PotionEffect pot1 = new PotionEffect(PotionEffectType.HEAL, 1, 0);
		PotionEffect pot2 = new PotionEffect(PotionEffectType.HEAL, 1, 0);
		
		ItemStack[] items;
		items = kitItem(kit);
		
		for(int i = 0; i < 3; i++){
			if(items[i] != null){
				if(items[i].getType() != Material.AIR){
		ItemMeta iMeta = items[i].getItemMeta();
		iMeta.setDisplayName(ChatColor.GOLD + kit.toString());
		iMeta.setLore(getKitDesc(kit));
		items[i].setItemMeta(iMeta);
			}
			}
		}
		
		player.getInventory().setItem(8, items[0]);
		player.getInventory().setItem(7, items[1]);
		player.getInventory().setItem(6, items[2]);
		
		player.addPotionEffect(pot1);
		player.addPotionEffect(pot2);
		
		p.util.giveStuffHG(player);
		
		if(kit == sKits.Copycat){
			p.util.CopycatKit.put(player, sKits.Copycat);
		}
		p.util.setKit(player, kit);
		
		p.chat.sendMessageKitPlayer(player, kit.toString());
		
	}

}
