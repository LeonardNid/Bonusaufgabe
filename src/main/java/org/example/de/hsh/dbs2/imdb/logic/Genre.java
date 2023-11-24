package org.example.de.hsh.dbs2.imdb.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Genre {
    private Long genreid = null;
    private String genre;
    public Genre(String genre) {
        this.genre = genre;
    }
    public Genre(Long genreid, String genre) {
        this.genreid = genreid;
        this.genre = genre;
    }

    public void insertIntoGenre(Connection conn) throws SQLException {
        if (genreid != null) {
            throw new RuntimeException();
        }

        String sql = "INSERT INTO genre (genre) VALUES (?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, genre);
            int cnt = statement.executeUpdate();
            setGenreid(conn);
            if (cnt == 0) {
                throw new SQLException("ExecuteUpdate: Kein Datensatz wurde aktualisiert");
            }
        }
    }

    public void updateGenre(Connection conn) throws SQLException {
        if (genreid == null) {
            throw new RuntimeException();
        }

        String sql = "UPDATE genre "+
                " SET genre = ?"+
                " WHERE genreid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, genre);
            statement.setLong(2, genreid);

            int cnt = statement.executeUpdate();
            if (cnt == 0) {
                throw new SQLException("ExecuteUpdate: Kein Datensatz wurde aktualisiert");
            }
        }
    }

    public void deleteGenre(Connection conn) throws SQLException {
        if (genreid == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM genre "+
                " WHERE genreid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, genreid);
            int cnt = statement.executeUpdate();
            if (cnt == 0) {
                throw new SQLException("ExecuteUpdate: Kein Datensatz wurde aktualisiert");
            }
        }
    }

    private void setGenreid(Connection conn) throws SQLException {
        String sql = "SELECT last_insert_rowid() AS id;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                this.genreid = rs.getLong(1);
            }
        }
    }

    public long getGenreid() {
        return genreid;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
