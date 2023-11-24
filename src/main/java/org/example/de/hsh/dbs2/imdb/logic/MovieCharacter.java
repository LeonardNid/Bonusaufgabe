package org.example.de.hsh.dbs2.imdb.logic;

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

    public MovieCharacter(String character, String alias, Long movieID, Long personID) {
        this.character = character;
        this.alias = alias;
        this.movieID = movieID;
        this.personID = personID;
    }

    private void findMaxPos(Connection conn) throws SQLException {
        String sql = "Select position from moviecharacter order by position desc;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                setPosition(rs.getInt("position") + 1);

            }
        }
    }

    public void insertIntoMovieCharacter(Connection conn) throws SQLException {
        if (movCharID != null) {
            throw new RuntimeException();
        }

        findMaxPos(conn);

        String sql = "INSERT INTO moviecharacter (character, alias, position, movieid, personid) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, character);
            statement.setString(2, alias);
            statement.setInt(3, position);
            statement.setLong(4, movieID);
            statement.setLong(5, personID);
            int cnt = statement.executeUpdate();
            if (cnt == 0) {
                throw new SQLException("ExecuteUpdate: Kein Datensatz wurde aktualisiert");
            }
            setMovCharID(conn);
        }
    }

    public void updateMovieCharacter(Connection conn) throws SQLException {
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
            if (cnt == 0) {
                throw new SQLException("ExecuteUpdate: Kein Datensatz wurde aktualisiert");
            }
        }
    }

    public void deleteMovieCharacter(Connection conn) throws SQLException {
        if (movCharID == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM moviecharacter "+
                " WHERE movcharid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, movCharID);
            int cnt = statement.executeUpdate();
            if (cnt == 0) {
                throw new SQLException("ExecuteUpdate: Kein Datensatz wurde aktualisiert");
            }
        }
    }

    private void setMovCharID(Connection conn) throws SQLException {
        String sql = "SELECT last_insert_rowid() AS id;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                this.movCharID = rs.getLong(1);
            }
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
