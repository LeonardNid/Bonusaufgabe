package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenreFactory {
    public static ArrayList<Genre> findByGenre(String search) throws SQLException {
        String sql = "SELECT * FROM genre " +
                "WHERE genre like ? " +
                "ORDER BY genre;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, "%" + search + "%");

            ArrayList<Genre> list = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("genreid");
                    String genre = rs.getString("genre");

                    list.add(new Genre(id, genre));
                }
            }
            return list;
        }
    }

    public static Genre findOneGenre(String search) throws SQLException {
        String sql = "SELECT * FROM genre " +
                "WHERE genre = ? ;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, search);

            try (ResultSet rs = statement.executeQuery()) {
                Long id = rs.getLong("genreid");
                String genreString = rs.getString("genre");

                return new Genre(id, genreString);
            }
        }
    }

    public static Genre findbyid(Long id) throws SQLException {
        String sql = "SELECT * FROM genre " +
                "WHERE genreid = ? ;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                Long id2 = rs.getLong("genreid");
                String genreString = rs.getString("genre");

                return new Genre(id2, genreString);
            }
        }
    }
}
