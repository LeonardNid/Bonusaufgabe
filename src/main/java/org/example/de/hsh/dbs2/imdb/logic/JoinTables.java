package org.example.de.hsh.dbs2.imdb.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinTables {
    // Allgemeine Methode für INNER JOIN
    public static ResultSet innerJoin(Connection connection, String table1, String table2, String onCondition) throws SQLException {
        String sql = "SELECT * FROM " + table1 + " INNER JOIN " + table2 + " ON " + onCondition;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery();
        }
    }
}