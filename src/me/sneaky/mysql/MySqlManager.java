package me.sneaky.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class MySqlManager {

	public static MySQL MySQL = new MySQL("192.99.38.228", "3306", "mc702", "mc702", "4lUolQIvHfsgbvad");
	public static Connection c = null;

	public static void setupSQL() {
		c = MySQL.openConnection();
	}

	public static void addPlayerToSQL(Player player) {
		PreparedStatement ps;
		try {
			ps = c.prepareStatement("INSERT INTO kitpvp_player_data(player_uuid, player_kills, player_deaths, player_credits, player_hks) VALUES (?, ?, ?, ?, ?);");
			ps.setString(1, player.getUniqueId().toString());
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.setInt(5, 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean sqlContainsPlayer(Player player) {
		try {
			PreparedStatement statement = c.prepareStatement("SELECT * FROM kitpvp_player_data WHERE player_uuid = ?;");
			statement.setString(1, player.getUniqueId().toString());
			return statement.executeQuery().next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void addIntSQL(Player player, String where, int amount) throws Exception{
		int i = getIntSQL(player, where);
		
		PreparedStatement ps = c.prepareStatement("UPDATE kitpvp_player_data SET column = player_ " + where + " WHERE key = player_uuid;");
		ps.setInt(1, i + amount);
		ps.setString(2, player.getUniqueId().toString());
		ps.executeUpdate();	
	}
	
	public static int getIntSQL(Player player, String where) throws Exception{
		int i = 0;
		if(sqlContainsPlayer(player)){
		PreparedStatement statement = c.prepareStatement("SELECT player_" + where + " FROM kitpvp_player_data WHERE player_uuid = ?;");
		statement.setString(1, player.getUniqueId().toString());
		
		ResultSet result = statement.executeQuery();
		result.next();
		
		i = result.getInt("player_" + where);
		}
		return i;
	}
	
	public static String getStringSQL(Player player, String where) throws Exception{
		String str = "";
		if(sqlContainsPlayer(player)){
		PreparedStatement statement = c.prepareStatement("SELECT player_" + where + " FROM kitpvp_player_data WHERE player_uuid = ?;");
		statement.setString(1, player.getUniqueId().toString());
		
		ResultSet result = statement.executeQuery();
		result.next();
		
		str = result.getString("player_" + where);
		}
		return str;
	}

}
