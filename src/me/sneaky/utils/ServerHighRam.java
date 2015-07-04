package me.sneaky.utils;

import me.sneaky.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ServerHighRam implements Runnable{

	public void run() {
	      Runtime runtime = Runtime.getRuntime();
	      System.gc();
		long max = runtime.totalMemory() / 1048576L;
		long now = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
				if(max - 100 < now){
					Bukkit.broadcastMessage(ChatColor.RED + "Server Is Restarting In 10 Seconds");
					
					Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							Bukkit.broadcastMessage(ChatColor.RED + "Server Is Restarting In 5 Seconds");
						}
					}, 20 * 5);
					Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							Bukkit.broadcastMessage(ChatColor.RED + "Server Is Restarting In 4 Seconds");
						}
					}, 20 * 6);
					Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							Bukkit.broadcastMessage(ChatColor.RED + "Server Is Restarting In 3 Seconds");
						}
					}, 20 * 7);
					Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							Bukkit.broadcastMessage(ChatColor.RED + "Server Is Restarting In 2 Seconds");
						}
					}, 20 * 8);
					Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							Bukkit.broadcastMessage(ChatColor.RED + "Server Is Restarting In 1 Second");
						}
					}, 20 * 9);
					Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
						public void run(){
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");	
						}
					}, 20 * 10);
				}
	}

}
