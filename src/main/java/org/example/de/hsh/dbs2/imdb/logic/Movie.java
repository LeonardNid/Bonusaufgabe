package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class Movie {
    private Long movieid = null;
    private String title;
    private Integer year;
    private String type;
    public Movie(String title, Integer year, String type) {
        this.title = title;
        this.year = year;
        this.type =type;
    }
    public Movie(Long movieid, String title, Integer year, String type) {
        this.movieid = movieid;
        this.title = title;
        this.year = year;
        this.type =type;
    }

    public void insertIntomovie(Connection conn) throws Exception {
        if (movieid != null) {
            throw new RuntimeException();
        }

        String sql = "INSERT INTO movie (title, year, type) VALUES (?, ?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setInt(2, year);
            statement.setString(3, type);
            int cnt = statement.executeUpdate();


            setMovieid(conn);
        }
    }

    public void updatemovie(Connection conn) throws SQLException {
        if (movieid == null) {
            throw new RuntimeException();
        }

        String sql = "UPDATE movie "+
                " SET title = ?, year = ?, type = ? "+
                " WHERE movieid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setInt(2, year);
            statement.setString(3, type);
            statement.setLong(4, movieid);

            int cnt = statement.executeUpdate();
        }
    }

    public void deletemovie(Connection conn) throws Exception {
        if (movieid == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM movie "+
                " WHERE movieid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, movieid);
            int cnt = statement.executeUpdate();
        }
    }

    private void setMovieid(Connection conn) throws SQLException {
        String sql = "SELECT last_insert_rowid() AS id;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                this.movieid = rs.getLong(1);
            }
        }
    }

    public Long getMovieid() {
        return movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
