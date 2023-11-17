package org.example.de.hsh.dbs2.imdb.logic;

import org.example.de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreManager {

	/**
	 * Ermittelt eine vollstaendige Liste aller in der Datenbank abgelegten Genres
	 * Die Genres werden alphabetisch sortiert zurueckgeliefert.
	 * @return Alle Genre-Namen als String-Liste
	 * @throws Exception
	 */
	public List<String> getGenres() throws Exception {
		// TODO
		List<String> list = new ArrayList<>();
		String sql = "select genre from genre;";

		try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String genre = rs.getString("genre");
				list.add(genre);
			}
		}
		return list;
	}

}
