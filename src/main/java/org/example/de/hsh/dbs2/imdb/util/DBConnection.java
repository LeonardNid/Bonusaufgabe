package org.example.de.hsh.dbs2.imdb.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	private static Connection conn;
	
	static {
		conn = null;
		try {
			// TODO: Connection to SQLite-DB
			conn = DriverManager.getConnection(
					"jdbc:sqlite:sqlite-test.db");
//			conn.setAutoCommit(false);
			System.out.println("Connect durchgefuehrt ....");
		} catch (Exception e) {
			System.err.println("Error while connecting to database");
			e.printStackTrace();
			System.exit(1);
		} 
	}
		
	public static Connection getConnection() {
		return conn;
	}
}
