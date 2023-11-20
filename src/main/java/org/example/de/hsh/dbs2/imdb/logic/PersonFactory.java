package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonFactory {
    public static ArrayList<Person> findByName(String search) throws SQLException { // in eigene factoryclass und exception werfen nicht catchen
        String sql = "SELECT * FROM person " +
                "WHERE name LIKE ? ;";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1,"%" + search + "%");

            ResultSet rs = statement.executeQuery();

            ArrayList<Person> list = new ArrayList<>();

            while (rs.next()) {
                long id = rs.getLong("personid");
                String name = rs.getString("name");
                String sex = rs.getString("sex");

                list.add(new Person(id, name, sex));
            }

            return list;
        }
    }
}
