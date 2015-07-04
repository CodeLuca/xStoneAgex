package me.sneaky.events.competitive;

import me.sneaky.events.competitive.UtilsCompetitve.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class TimerCompetitive implements Runnable{
	public static int time = 137;
	public static int bombTime = 45;
	
	UtilsCompetitve hg = new UtilsCompetitve();
	
	  @SuppressWarnings("deprecation")
	public static void setScoreboard(Player player) {
		    Scoreboard scoreboard = player.getServer().getScoreboardManager().getNewScoreboard();
		    Objective scoreboardObj = scoreboard.registerNewObjective("test", "dummy");
		    scoreboardObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		    scoreboardObj.setDisplayName(ChatColor.RED + "Competitive");
		    scoreboardObj.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Balance: $")).setScore(UtilsCompetitve.playerMoney.get(player));
		    scoreboardObj.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "T Wins: ")).setScore(UtilsCompetitve.tWins);
		    scoreboardObj.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "CT Wins: ")).setScore(UtilsCompetitve.tWins); 
		    player.setScoreboard(scoreboard);
		}
	
	public void run() {
		if(UtilsCompetitve.started == true){
			for(Player player : UtilsCompetitve.players){
				setScoreboard(player);
			}
		if(UtilsCompetitve.planted == false){
			for(Player player : UtilsCompetitve.players){
				player.setLevel(time);
			}
		time--;
		if(time == 120){
			for(Player player : UtilsCompetitve.players){
				player.closeInventory();
			}
		}
		if(time == 0){
			hg.newRound(teams.COUNTER_TERRORIST);
		}
		}else{
			for(Player player : UtilsCompetitve.players){
				player.setLevel(bombTime);
			}
			bombTime--;
				UtilsCompetitve.tntBomb.getWorld().playSound(UtilsCompetitve.tntBomb.getLocation(), Sound.NOTE_PLING, 25, 1);
			if(bombTime == 0){
				hg.newRound(teams.TERRORIST);
			}
		}
		}else{
			time = 135;
			bombTime = 45;
		}
	}
}
