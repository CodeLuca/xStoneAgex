package me.sneaky.cmds.staff;

import me.sneaky.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteChat implements CommandExecutor, Listener {
	final Main p;
	public static boolean muted = false;

	public MuteChat(Main instance) {
		this.p = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		final Player player = (Player) sender;
		if (args.length == 0) {
			if (player.hasPermission("skits.staff.sc")) {
				if (muted == false) {
					p.chat.sendBroadcast("§4The Chat Has Been Muted By "
							+ player.getName());
					muted = true;
					return true;
				} else if (muted == true) {
					p.chat.sendBroadcast("§aThe Chat Has Been UnMuted By "
							+ player.getName());
					muted = false;
					return true;
				}
			}
		}
		return false;
	}

	@EventHandler
	public void Chat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		if (muted == true) {
			if (!player.hasPermission("skits.staff.sc")) {
				e.setCancelled(true);
				p.chat.sendMessagePlayer(player,
						"You cannot Speak When Chat is Muted.");
				return;
			}
		} else if (muted == false) {
			e.setCancelled(false);
			return;
		}
	}
}
