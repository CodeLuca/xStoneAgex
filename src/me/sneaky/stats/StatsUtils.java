package me.sneaky.stats;

import org.bukkit.entity.Player;

import me.sneaky.Main;
import me.sneaky.mysql.MySqlManager;

public class StatsUtils {
	
	  final Main p;
	  

	  public StatsUtils(Main instance)
	  {
	    this.p = instance;
	  }
	  
	  public void addPlayer(Player player){
		  if(!MySqlManager.sqlContainsPlayer(player)){
			  MySqlManager.addPlayerToSQL(player);
		  }
	  }
	  
	  public int getCredits(Player player) throws Exception{
		  return MySqlManager.getIntSQL(player, "credits");
	  }
	  
	  public int getKills(Player player) throws Exception{
		  return MySqlManager.getIntSQL(player, "kills");
	  }
	  
	  public int getDeaths(Player player) throws Exception{
		  return MySqlManager.getIntSQL(player, "deaths");
	  }
	  
	  public int getHKS(Player player) throws Exception{
		  return MySqlManager.getIntSQL(player, "hks");
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
		  MySqlManager.addIntSQL(player, "kills", 1);
	  }
	  
	  public void addDeaths(Player player) throws Exception{
		  MySqlManager.addIntSQL(player, "deaths", 1);
	  }
	  
	  public void addCredits(Player player, int amnt) throws Exception{
		  MySqlManager.addIntSQL(player, "credits", amnt);
	  }
	  
	  public void removeCredits(Player player, Integer i) throws Exception{
		  MySqlManager.addIntSQL(player, "credits", i);
	  }
	  
	  
	  public void addKillToKillStreak(Player player) throws Exception{
		  p.util.KillStreak.put(player, p.util.KillStreak.get(player) != null ? p.util.KillStreak.get(player) + 1 : 1);
		  if(getHKS(player) < p.util.KillStreak.get(player)){
			  p.getConfig().set("stats." + player.getUniqueId() + ".ks",  p.util.KillStreak.get(player));
			  p.saveConfig();
		  }
	  }
	  
	  
	  
	  

}
