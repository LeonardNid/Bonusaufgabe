package org.example.de.hsh.dbs2.imdb.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {
    private Long personID = null;
    private String name;
    private String sex;
    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }
    public Person(Long personID, String name, String sex) {
        this.personID = personID;
        this.name = name;
        this.sex =sex;
    }

    public void insertIntoPerson(Connection conn) throws SQLException {
        if (personID != null) {
            throw new RuntimeException();
        }

        String sql = "INSERT INTO person (name, sex) VALUES (?, ?);";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, sex);
            int cnt = statement.executeUpdate();

            setPersonID(conn);
        }
    }

    public void updatePerson(Connection conn) throws SQLException {
        if (personID == null) {
            throw new RuntimeException();
        }

        String sql = "UPDATE person "+
                " SET name = ?, sex = ? "+
                " WHERE personid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, sex);
            statement.setLong(3, personID);

            int cnt = statement.executeUpdate();
        }
    }

    public void deletePerson(Connection conn) throws SQLException {
        if (personID == null) {
            throw new RuntimeException();
        }


        String sql = "DELETE FROM person "+
                " WHERE personid = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, personID);
            int cnt = statement.executeUpdate();
        }
    }

    private void setPersonID(Connection conn) throws SQLException {
        String sql = "SELECT last_insert_rowid() AS id;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            this.personID = rs.getLong(1);
        }
    }

    public long getPersonID() {
        return personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
