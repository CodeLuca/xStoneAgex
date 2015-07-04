package me.sneaky.events.custom;
 
import org.bukkit.Bukkit;
 
public class TimeSecondLoop implements Runnable {
 
    public void run() {
        Bukkit.getPluginManager().callEvent(new TimeSecondEvent());
    }
 
}