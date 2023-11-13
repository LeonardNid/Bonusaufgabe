package org.example;

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

    public void insertIntoGenre(Connection conn) {
        if (genreid != null) {
            throw new RuntimeException();
        }

        String sql = "INSERT INTO genre (genre) VALUES (?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, genre);
            int cnt = statement.executeUpdate();

            setGenreid(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGenre(Connection conn) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGenre(Connection conn) {
        if (genreid == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM genre "+
                " WHERE genreid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, genreid);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setGenreid(Connection conn) {
        String sql = "SELECT last_insert_rowid() AS id;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            this.genreid = rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
