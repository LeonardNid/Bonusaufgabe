package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.logic.dto.MovieDTO;
import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonManager {

	/**
	 * Liefert eine Liste aller Personen, deren Name den Suchstring enthaelt.
	 * @param text Suchstring
	 * @return Liste mit passenden Personennamen, die in der Datenbank eingetragen sind.
	 * @throws Exception
	 */
	public List<String> getPersonList(String text) throws Exception {
//		if (text.equals(" ")) {
//			text = "";
//		}
		// TODO
		List<String> list = new ArrayList<>();
		String sql = "select name from person where name like ?;";

		try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
			statement.setString(1, "%" + text + "%");

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {

				String name = rs.getString("name");;
				list.add(name);

			}
		}

		System.out.println("IN PERSONEN");
		System.out.println(list);
		return list;
	}

}
