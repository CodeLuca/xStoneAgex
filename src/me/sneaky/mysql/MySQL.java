package me.sneaky.mysql;

import java.sql.*;

public class MySQL extends Database {
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;

	private Connection connection;
	private String table;

	public MySQL(String hostname, String port, String database, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = null;
	}

	public MySQL(String hostname, String port, String database, String username, String password, String table) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = null;
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	@Override
	public Connection openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		}
		return connection;
	}

	@Override
	public boolean checkConnection() {
		return connection != null;
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ResultSet querySQL(String query) {
		Connection c = null;

		if (checkConnection()) {
			c = getConnection();
		} else {
			c = openConnection();
		}

		Statement s = null;

		try {
			s = c.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		ResultSet ret = null;

		try {
			ret = s.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();

		return ret;
	}

	public void updateSQL(String update) {

		Connection c = null;

		if (checkConnection()) {
			c = getConnection();
		} else {
			c = openConnection();
		}

		Statement s = null;

		try {
			s = c.createStatement();
			s.executeUpdate(update);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		closeConnection();

	}

}