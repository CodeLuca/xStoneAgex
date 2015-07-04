package me.sneaky.stats;

import org.bukkit.entity.Player;

import me.sneaky.Config;
import me.sneaky.Main;
import me.sneaky.mysql.MySqlManager;

public class StatsUtils {
	
	  final Main p;
	  

	  public StatsUtils(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  public void addPlayer(Player player){
		  if(Config.getStatsConfig().getConfigurationSection("stats." + player.getUniqueId().toString()) != null){
			  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".kills", 0);
			  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".deaths", 0);
			  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".credits", 0);
			  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".hks", 0);
		  }
	  }
	  
	  public int getCredits(Player player) throws Exception{
		  return Config.getStatsConfig().getInt("stats." + player.getUniqueId().toString() + ".credits");
	  }
	  
	  public int getKills(Player player) throws Exception{
		  return Config.getStatsConfig().getInt("stats." + player.getUniqueId().toString() + ".kills");
	  }
	  
	  public int getDeaths(Player player) throws Exception{
		  return Config.getStatsConfig().getInt("stats." + player.getUniqueId().toString() + ".deaths");
	  }
	  
	  public int getHKS(Player player) throws Exception{
		  return Config.getStatsConfig().getInt("stats." + player.getUniqueId().toString() + ".hks");
	  }
	  
	  public int getCurrentKillStreak(Player player) throws Exception{
		  return p.util.KillStreak.get(player) != null ? p.util.KillStreak.get(player) : 0;
	  }
	  
	  public double getKD(Player player) throws Exception{
		  
		  double kills = getKills(player);
		  double deaths = getDeaths(player);
		  double kd = kills/deaths;
		  
		  return kd;
	  }
	  
	  public void addKills(Player player) throws Exception{
		  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".kills", getKills(player) + 1);
	  }
	  
	  public void addDeaths(Player player) throws Exception{
		  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".deaths", getDeaths(player) + 1);
	  }
	  
	  public void addCredits(Player player, int amnt) throws Exception{
		  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".credits", getKills(player) + amnt);
	  }
	  
	  public void removeCredits(Player player, Integer i) throws Exception{
		  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".credits", getKills(player) - i);
	  }
	  	
	  public void setHKS(Player player, Integer i) throws Exception{
		  Config.getStatsConfig().set("stats." + player.getUniqueId().toString() + ".hks", i);
	  }
	  
	  
	  public void addKillToKillStreak(Player player) throws Exception{
		  p.util.KillStreak.put(player, p.util.KillStreak.get(player) != null ? p.util.KillStreak.get(player) + 1 : 1);
	  }
	  
	  
	  
	  

}
