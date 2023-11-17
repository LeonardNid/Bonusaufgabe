package org.example;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {
    private Long movieid = null;
    private String title;
    private Integer year;
    private String type;
    public Movie(String title, Integer year, String type) {
        this.title = title;
        this.year = year;
        this.type =type;

        System.out.println("MOVIE:");
    }
    public Movie(Long movieid, String title, Integer year, String type) {
        this.movieid = movieid;
        this.title = title;
        this.year = year;
        this.type =type;
    }

    public void insertIntomovie(Connection conn) {

        System.out.println("MOVIe insert");
        System.out.println(DBConnection.getConnection());

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatemovie(Connection conn) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletemovie(Connection conn) {
        if (movieid == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM movie "+
                " WHERE movieid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, movieid);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setMovieid(Connection conn) {
        String sql = "SELECT last_insert_rowid() AS id;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            this.movieid = rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getMovieid() {
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
