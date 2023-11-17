package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreFactory {
    public static Genre findbyGenre(String genre) throws SQLException {
        String sql = "SELECT * FROM genre " +
                "WHERE genre = ? ;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, genre);

            ResultSet rs = statement.executeQuery();

            Long id = rs.getLong("genreid");

            return new Genre(id,genre);

        }
    }
}
