package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenreFactory {
    public static ArrayList<Genre> findByGenre(String search) throws SQLException {
        String sql = "SELECT * FROM genre " +
                "WHERE genre like ? ;";
        ArrayList<Genre> list = new ArrayList<>();
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, "%" + search + "%");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("genreid");
                String genre = rs.getString("genre");

                list.add(new Genre(id, genre));
            }
            return list;
        }
    }
}
