package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.logic.dto.CharacterDTO;
import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieCharacterFactory {

    public static ArrayList<MovieCharacter> findByMovieId(Long movieid) throws SQLException {
        String sql = "SELECT * FROM movieCharacter " +
                "WHERE movieid = ? ;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setLong(1, movieid);

            ArrayList<MovieCharacter> list = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("movcharid");
                    String character = rs.getString("character");
                    String alias = rs.getString("alias");
                    Integer position = rs.getInt("position");
                    Long movieid2 = rs.getLong("movieid");
                    Long personid = rs.getLong("personid");

                    list.add(new MovieCharacter(id,character,alias,position,movieid2,personid));
                }
            }
            return list;
        }
    }

}
