package org.example.de.hsh.dbs2.imdb.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HasGenre {
    private Long genreid = null;
    private Long movieid = null;

    public HasGenre(long genreid, long movieid) {
        this.genreid = genreid;
        this.movieid = movieid;
    }

    public void insertIntoHasGenre(Connection conn) throws SQLException {
        if (genreid == null && movieid == null) {
            throw new RuntimeException();
        }

        String sql = "INSERT INTO HasGenre VALUES (?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, genreid);
            statement.setLong(2, movieid);
            int cnt = statement.executeUpdate();
            if (cnt == 0) {
                throw new SQLException("ExecuteUpdate: Kein Datensatz wurde aktualisiert");
            }
        }
    }

    public void deleteHasGenre(Connection conn) throws SQLException {
        if (genreid == null && movieid == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM HasGenre "+
                " WHERE genreid = ? AND movieid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, genreid);
            statement.setLong(2, movieid);
            int cnt = statement.executeUpdate();
            if (cnt == 0) {
                throw new SQLException("ExecuteUpdate: Kein Datensatz wurde aktualisiert");
            }
        }
    }

    public long getGenreid() {
        return genreid;
    }

    public Long getMovieid() {
        return movieid;
    }
}
