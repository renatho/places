package com.app.places;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHelper {
	Connection conn = null;
	String url = "jdbc:mysql://174.123.233.90:3306/";
	String dbName = "toctoc_places";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "toctoc_places";
	String password = "places123";
	Statement statement;

	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void executeUpdate(String sql) {
		try {
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
	    try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			statement = conn.createStatement();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}