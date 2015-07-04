package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Block implements CommandExecutor{

	
	  final Main p;
	  

	  public Block(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  @SuppressWarnings("deprecation")
	public ItemStack getBlockByID(int id, int amount, int data){
		  return new ItemStack(Material.getMaterial(id), amount, (short) data);
	  }
	  
	public ItemStack getBlockByString(String name, int amount, int data){
		ItemStack item = null;
		  for(Material mat : p.util.allMaterials){
			  String matName = mat.toString().toLowerCase();
			  if(matName.equalsIgnoreCase(name) || name.equalsIgnoreCase(matName)){
				  item = new ItemStack(mat, amount, (short) data);
			  }
		  }
		return item;
	  }
	  
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		    final Player player = (Player) sender;
		    
				if (!player.hasPermission("skits.staff.block")) {
					p.chat.noPermMessage(player);
					return true;
				}
				if (args.length == 3){
					if(Integer.valueOf(args[1]) != null || Integer.valueOf(args[2]) != null){
					if(Integer.valueOf(args[0]) != null){
						if(getBlockByID(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2])) != null){
						player.getInventory().addItem(getBlockByID(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2])));
						}else{
							p.chat.sendMessagePlayer(player, "That Block Does Not Exist");
						}
					}else{
						if(getBlockByString(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2])) != null){
						player.getInventory().addItem(getBlockByString(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2])));
						}else{
							p.chat.sendMessagePlayer(player, "That Block Does Not Exist");
						}
					}
				}
				}else{
					p.chat.sendMessagePlayer(player, "/Block [Id/Name] [Amount] [Data]");
				}
		    return true;	    
	}
}