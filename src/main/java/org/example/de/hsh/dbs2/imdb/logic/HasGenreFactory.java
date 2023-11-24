package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HasGenreFactory {
    public static ArrayList<Long> findByMovieId(long id) throws SQLException {
        String sql = "SELECT * FROM hasgenre " +
                "WHERE movieid = ? ;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);

            ArrayList<Long> list = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long genreid = rs.getLong("genreid");

                    list.add(genreid);
                }
            }
            return list;
        }
    }
}
