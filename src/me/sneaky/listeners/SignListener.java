package me.sneaky.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {
	
    @EventHandler
    public void onSignChange(SignChangeEvent e) {
            if (e.getLine(0).equalsIgnoreCase("StoneAge")) {
                    e.setLine(0, ChatColor.GREEN + "" + ChatColor.BOLD + "StoneAge");
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
	  			if (s.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "StoneAge")) {
	  				player.chat("/" + ChatColor.stripColor(s.getLine(1)));
	  		    }
	  		    }
	  			}

}
