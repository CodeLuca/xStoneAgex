package me.sneaky.events.sg;

import org.bukkit.Material;

public enum ItemsSG {
	Leather_Helmet(20, Material.LEATHER_HELMET),
	Leather_Chest(20, Material.LEATHER_CHESTPLATE),
	Leather_Pants(20, Material.LEATHER_LEGGINGS),
	Leather_Boots(20, Material.LEATHER_BOOTS),
	
	Chain_Helmet(10, Material.CHAINMAIL_HELMET),
	Chain_Chest(10, Material.CHAINMAIL_CHESTPLATE),
	Chain_Pants(10, Material.CHAINMAIL_LEGGINGS),
	Chain_Boots(10, Material.CHAINMAIL_BOOTS),
	
	Gold_Helmet(13, Material.GOLD_HELMET),
	Gold_Chest(13, Material.GOLD_CHESTPLATE),
	Gold_Pants(13, Material.GOLD_LEGGINGS),
	Gold_Boots(13, Material.GOLD_BOOTS),
	
	Stone_Sword(10, Material.STONE_SWORD),
	Wood_Sword(12, Material.WOOD_SWORD),
	Gold_Sword(15, Material.GOLD_SWORD),
	
	Stone_axe(15, Material.STONE_AXE),
	Wood_axe(17, Material.WOOD_AXE),
	Gold_axe(20, Material.GOLD_AXE),
	
	Soup(5, Material.MUSHROOM_SOUP),
	Melon(15, Material.MELON),
	Steak(5, Material.COOKED_BEEF),
	Golden_Apple(2, Material.GOLDEN_APPLE),
	
	Ender_Pearl(4, Material.ENDER_PEARL),
	Arrow(7, Material.ARROW),
	Bow(5, Material.BOW);
	

	private int chance;
	private Material mat;
	
	ItemsSG(int chance, Material mat){
		this.chance = chance;
		this.mat = mat;
	}
	
	
	public int getChance(){	
		return this.chance;
	}
	
	public Material getMat(){	
		return this.mat;
	}

}
