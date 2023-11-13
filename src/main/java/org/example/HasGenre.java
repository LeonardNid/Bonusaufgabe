package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HasGenre {
    private Long genreid = null;
    private Long tempGenreid = null;
    private Long movieid = null;
    private Long tempMovieid = null;

    public HasGenre(long genreid, long movieid) {
        this.genreid = genreid;
        this.movieid = movieid;
    }

    public void insertIntoHasGenre(Connection conn) {
        if (genreid == null && movieid == null) {
            throw new RuntimeException();
        }

        String sql = "INSERT INTO HasGenre VALUES (?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, genreid);
            statement.setLong(2, movieid);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateHasGenre(Connection conn) {
        if (genreid == null && movieid == null) {
            throw new RuntimeException();
        }

        String sql = "UPDATE HasGenre "+
                " SET genreid = ?, movieid = ?"+
                " WHERE genreid = ? AND movieid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, tempGenreid);
            statement.setLong(2, tempMovieid);
            statement.setLong(3, genreid);
            statement.setLong(4, movieid);

            genreid = tempGenreid;
            movieid = tempMovieid;

            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteHasGenre(Connection conn) {
        if (genreid == null && movieid == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM HasGenre "+
                " WHERE genreid = ? AND movieid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, genreid);
            statement.setLong(2, movieid);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGenreid(long genreid) {
        this.tempGenreid = genreid;
    }

    public long getGenreid() {
        return genreid;
    }
    public void setMovieid(long movieid) {
        this.tempMovieid = movieid;
    }

    public Long getMovieid() {
        return movieid;
    }
}
