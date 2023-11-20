package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieFactory {
    public static Movie findById(long id) throws SQLException {
        String sql = "SELECT * FROM movie " +
                "WHERE movieid = ? ;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            String title = rs.getString("title");
            int year = rs.getInt("year");
            String type = rs.getString("type");

            return new Movie(id,title,year,type);

        }
    }

    public static ArrayList<Movie> findBytitle(String search) throws SQLException { // in eigene factoryclass und exception werfen nicht catchen
        String sql = "SELECT * FROM movie " +
                "WHERE title LIKE ? ;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1,"%" + search + "%");

            ResultSet rs = statement.executeQuery();

            ArrayList<Movie> list = new ArrayList<>();

            while (rs.next()) {
                long id = rs.getLong("movieid");
                String title = rs.getString("title");
                int year = rs.getInt("year");
                String type = rs.getString("type");

                list.add(new Movie(id, title, year, type));
            }
            return list;
        }
    }
}
