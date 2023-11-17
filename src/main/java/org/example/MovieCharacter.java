package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieCharacter {
    private Long movCharID = null;

    private String character;

    private String alias;
    private Integer position;
    private Long movieID;
    private Long personID;
    public MovieCharacter(String character, String alias, Integer position, Long movieID, Long personID) {
        this.character = character;
        this.alias = alias;
        this.position = position;
        this.movieID = movieID;
        this.personID = personID;
    }

    public MovieCharacter(Long movCharID, String character, String alias, Integer position, Long movieID, Long personID) {
        this.movCharID = movCharID;
        this.character = character;
        this.alias = alias;
        this.position = position;
        this.movieID = movieID;
        this.personID = personID;
    }
    public void insertIntoMovieCharacter(Connection conn) {
        if (movCharID != null) {
            throw new RuntimeException();
        }

        String sql = "INSERT INTO moviecharacter (character, alias, position, movieid, personid) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, character);
            statement.setString(2, alias);
            statement.setInt(3, position);
            statement.setLong(4, movieID);
            statement.setLong(5, personID);
            int cnt = statement.executeUpdate();

            setMovCharID(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMovieCharacter(Connection conn) {
        if (movCharID == null) {
            throw new RuntimeException();
        }

        String sql = "UPDATE moviecharacter "+
                " SET character = ?, alias = ?, position = ?, movieid = ?, personid = ? "+
                " WHERE movcharid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, character);
            statement.setString(2, alias);
            statement.setInt(3, position);
            statement.setLong(4, movieID);
            statement.setLong(5, personID);
            statement.setLong(6, movCharID);

            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMovieCharacter(Connection conn) {
        if (movCharID == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM moviecharacter "+
                " WHERE movcharid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, movCharID);
            int cnt = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setMovCharID(Connection conn) {
        String sql = "SELECT last_insert_rowid() AS id;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            this.movCharID = rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Long getMovCharID() {
        return movCharID;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }
}