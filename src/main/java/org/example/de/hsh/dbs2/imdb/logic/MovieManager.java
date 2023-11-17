package org.example.de.hsh.dbs2.imdb.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.example.de.hsh.dbs2.imdb.logic.dto.*;
import org.example.de.hsh.dbs2.imdb.util.DBConnection;

public class MovieManager {

	/**
	 * Ermittelt alle Filme, deren Filmtitel den Suchstring enthaelt.
	 * Wenn der String leer ist, sollen alle Filme zurueckgegeben werden.
	 * Der Suchstring soll ohne Ruecksicht auf Gross/Kleinschreibung verarbeitet werden.
	 * @param search Suchstring. 
	 * @return Liste aller passenden Filme als MovieDTO
	 * @throws Exception
	 */
	public List<MovieDTO> getMovieList(String search) throws Exception {
		// TODO
		List<MovieDTO> list = new ArrayList<>();
		String sql = "select * from movie where title like ?;";

		try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
			statement.setString(1, "%" + search + "%");

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {

				MovieDTO md = new MovieDTO();
				md.setId(rs.getLong("movieid"));
				md.setTitle(rs.getString("title"));
				md.setYear(rs.getInt("year"));
				md.setType(rs.getString("type"));
				list.add(md);


			}
		}
		return list;
	}

	/**
	 * Speichert die uebergebene Version des Films neu in der Datenbank oder aktualisiert den
	 * existierenden Film.
	 * Dazu werden die Daten des Films selbst (Titel, Jahr, Typ) beruecksichtigt,
	 * aber auch alle Genres, die dem Film zugeordnet sind und die Liste der Charaktere
	 * auf den neuen Stand gebracht.
	 * @param movie Film-Objekt mit Genres und Charakteren.
	 * @throws Exception
	 */
	public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {
		// TODO
		Movie m1 = new Movie(movieDTO.getTitle(), movieDTO.getYear(), movieDTO.getType());
		m1.insertIntomovie(DBConnection.getConnection());

		Genre g1 = GenreFactory.findbyGenre((String) movieDTO.getGenres().toArray()[0]);

		HasGenre hg1 = new HasGenre(g1.getGenreid() ,m1.getMovieid());
		hg1.insertIntoHasGenre(DBConnection.getConnection());

		System.out.println(movieDTO.getGenres().toString());
		System.out.println(movieDTO.getCharacters());
	}

	/**
	 * Loescht einen Film aus der Datenbank. Es werden auch alle abhaengigen Objekte geloescht,
	 * d.h. alle Charaktere und alle Genre-Zuordnungen.
	 * @param movie
	 * @throws Exception
	 */
	public void deleteMovie(long movieId) throws Exception {
		// TODO
	}

	/**
	 * Liefert die Daten eines einzelnen Movies zurück
	 * @param movieId
	 * @return
	 * @throws Exception
	 */
	public MovieDTO getMovie(long movieId) throws Exception {
		// TODO
		MovieDTO md = new MovieDTO();
		Movie m1 = MovieFactory.findById(movieId);
		md.setId(m1.getMovieid());
		md.setTitle(m1.getTitle());
		md.setYear(m1.getYear());
		md.setType(m1.getType());
		return md;
	}

}
