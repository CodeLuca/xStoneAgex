package me.sneaky.events.competitive;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponsCompetitve {
	
	public enum gunType{
		SNIPER,
		RIFLE,
		SUB,
		PISTOL;
	}
	
	public ItemStack bomb(){
		ItemStack item = new ItemStack(Material.APPLE);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName("Bomb");
		item.setItemMeta(iMeta);
		return item;
	}
	
	public enum guns{
		//Assualt
		AK47(2700, gunType.RIFLE, 30, 90, 10, 15, 300),
		M4A4(3100, gunType.RIFLE, 30, 90, 10, 15, 300),
		GALIL(2000, gunType.RIFLE, 35, 90, 8, 20, 300),
		FAMAS(2250, gunType.RIFLE, 25, 90, 8, 15, 300),
		
		//Sub
		P90(2350, gunType.SUB, 50, 100, 20, 20, 300),
		
		//Sniper
		SSG08(1700, gunType.SNIPER, 10, 90, 1, 30, 300),
		AWP(4750, gunType.SNIPER, 10, 30, 1, 40, 100),
		G3SG1(5000, gunType.SNIPER, 20, 90, 3, 35, 300),
		SCAR20(5000, gunType.SNIPER, 20, 90, 3, 35, 300),
		
		//Hand Guns
		USP(200, gunType.PISTOL, 12, 24, 4, 15, 300),
		GLOCK(200, gunType.PISTOL, 20, 120, 4, 15, 300),
		P250(300, gunType.PISTOL, 13, 52, 4, 20, 300),
		DESERTEAGLE(800, gunType.PISTOL, 7, 35, 2, 25, 300);
		
		
		private int price;
		private gunType type;
		private int ammo;
		private int maxAmmo;
		private int reloadTime;
		private int firerate;
		private int weaponKillPrice;
		
		guns(int price, gunType type, int ammo, int maxAmmo, int firerate , int reloadTime, int weaponKillPrice){
			this.price = price;
			this.type = type;
			this.ammo = ammo;
			this.maxAmmo = maxAmmo;
			//seconds / 10 * reloadTime
			this.reloadTime = reloadTime;
			//shots per second
			this.firerate = firerate;
			this.weaponKillPrice = weaponKillPrice;
		}
		
		public int getPrice(){
			return price;
		}
		
		public gunType getType(){
			return type;
		}
		
		public int getAmmo(){
			return ammo;
		}
		
		public int getMaxAmmo(){
			return maxAmmo;
		}
		
		public int getReloadTime(){
			return reloadTime;
		}
		
		public int getFirerate(){
			return firerate;
		}
		
		public int getKillPrice(){
			return weaponKillPrice;
		}
	}
	
	public enum hitType{
		HEAD,
		CHEST_ARM,
		STOMACH,
		LEG;
	}
	
	public ItemStack getWeapon(guns gun){
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
		iMeta.setDisplayName(gun.toString() + " || " + gun.getAmmo() + "/" + gun.getMaxAmmo());
		item.setItemMeta(iMeta);
		
		
		return item;
	}
	
	public guns getGun(String name){
		guns g = null;
		for(guns gun : guns.values()){
			if(gun.toString().toLowerCase().equalsIgnoreCase(name.toLowerCase())){
				g = gun;
			}
		}
		return g;
	}
	
	public int getDamage(guns gun, hitType type, int distance, boolean armor){
		int damage = 10;
		switch (gun){
		case AK47:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 140 - distance;
				}else{
					damage = 110 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 40 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 40 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			break;
			
		case AWP:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 460 - distance;
				}else{
					damage = 450 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 120 - distance;
				}else{
					damage = 110 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 140 - distance;
				}else{
					damage = 140 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 90 - distance;
				}else{
					damage = 90 - distance;
				}
			}
			break;
			
		case DESERTEAGLE:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 250 - distance;
				}else{
					damage = 230 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 60 - distance;
				}else{
					damage = 60 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 80 - distance;
				}else{
					damage = 70 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 50 - distance;
				}else{
					damage = 50 - distance;
				}
			}
			break;
			
		case FAMAS:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 120 - distance;
				}else{
					damage = 80 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 40 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 20 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			break;
			
		case G3SG1:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 320 - distance;
				}else{
					damage = 260 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 80 - distance;
				}else{
					damage = 70 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 100 - distance;
				}else{
					damage = 80 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 60 - distance;
				}else{
					damage = 60 - distance;
				}
			}
			break;
			
		case GALIL:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 120 - distance;
				}else{
					damage = 90 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 40 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 20 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			break;
			
		case GLOCK:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 110 - distance;
				}else{
					damage = 50 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 10 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 20 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			break;
			
		case M4A4:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 130 - distance;
				}else{
					damage = 90 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 40 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 20 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			break;
			
		case P250:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 140 - distance;
				}else{
					damage = 110 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 40 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			break;
			
		case P90:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 100 - distance;
				}else{
					damage = 80 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 20 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			break;
			
		case SCAR20:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 320 - distance;
				}else{
					damage = 260 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 80 - distance;
				}else{
					damage = 70 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 100 - distance;
				}else{
					damage = 80 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 60 - distance;
				}else{
					damage = 60 - distance;
				}
			}
			break;
			
		case SSG08:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 350 - distance;
				}else{
					damage = 300 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 90 - distance;
				}else{
					damage = 70 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 110 - distance;
				}else{
					damage = 90 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 70 - distance;
				}else{
					damage = 70 - distance;
				}
			}
			break;
			
		case USP:
			if(type == hitType.HEAD){
				if(armor == false){
				damage = 140 - distance;
				}else{
					damage = 70 - distance;
				}
			}
			if(type == hitType.CHEST_ARM){
				if(armor == false){
				damage = 40 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			if(type == hitType.STOMACH){
				if(armor == false){
				damage = 40 - distance;
				}else{
					damage = 20 - distance;
				}
			}
			if(type == hitType.LEG){
				if(armor == false){
				damage = 30 - distance;
				}else{
					damage = 30 - distance;
				}
			}
			break;
			
		default:
			break;
		
		}
		return damage;
	}
	
	public guns getGunInHand(Player player){
		guns g = null;
		if(player.getItemInHand() != null){
			if(player.getItemInHand().hasItemMeta()){
		for(guns gun : guns.values()){
			String gunName = gun.toString().toLowerCase();
			String gunInHand = player.getItemInHand().getItemMeta().getDisplayName().toLowerCase();
			if(gunInHand.contains(gunName)){
				g = gun;
			}
		}
	   }
	}
		return g;
	}
	
	

}
