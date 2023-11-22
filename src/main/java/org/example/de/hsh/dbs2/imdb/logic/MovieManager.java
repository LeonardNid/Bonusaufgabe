package org.example.de.hsh.dbs2.imdb.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.example.de.hsh.dbs2.imdb.logic.dto.*;
import org.example.de.hsh.dbs2.imdb.util.DBConnection;
import org.sqlite.core.DB;

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
		ArrayList<Movie> movies = MovieFactory.findBytitle(search);
		for (int i = 0; i < movies.size(); ++i) {
			MovieDTO md = new MovieDTO();
			md.setId(movies.get(i).getMovieid());
			md.setTitle(movies.get(i).getTitle());
			md.setYear(movies.get(i).getYear());
			md.setType(movies.get(i).getType());
			list.add(md);
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
		Movie m1 = new Movie(movieDTO.getId(), movieDTO.getTitle(), movieDTO.getYear(), movieDTO.getType());

		if (m1.getMovieid() == null) {
			m1.insertIntomovie(DBConnection.getConnection());
			movieDTO.setId(m1.getMovieid());

			insertHasGenre(movieDTO, m1);

			insertMovieCharcter(movieDTO, m1);


		} else {
			m1.updatemovie(DBConnection.getConnection());
			/*
			Löscht alle Einträge aus HasGenre mit der Movie id von moviedto
			*/
			for (Genre genre : GenreFactory.findByGenre("")) {
				HasGenre hasGenre = new HasGenre(genre.getGenreid() , m1.getMovieid());
				hasGenre.deleteHasGenre(DBConnection.getConnection());
			}
			insertHasGenre(movieDTO, m1);

			for (MovieCharacter movieCharacter : MovieCharacterFactory.findByMovieId(m1.getMovieid())) {
				MovieCharacter movieCharacter1 = new MovieCharacter(
						movieCharacter.getMovCharID(),
						movieCharacter.getCharacter(),
						movieCharacter.getAlias(),
						movieCharacter.getPosition(),
						movieCharacter.getMovieID(),
						movieCharacter.getPersonID()
				);
				movieCharacter1.deleteMovieCharacter(DBConnection.getConnection());
			}
			insertMovieCharcter(movieDTO, m1);

		}

	}
	/*
	loop jeden Genre aus moviedto
	Genre wird mit findOneGenre gefunden um auf die id zugreifen zu können
	Hasgenre wird erstellt und insertet
	 */
	private void insertHasGenre(MovieDTO movieDTO, Movie m1) throws Exception {
		for (String genre : movieDTO.getGenres()) {
			Genre genre1 = GenreFactory.findOneGenre(genre);
			HasGenre hasGenre = new HasGenre(genre1.getGenreid() , m1.getMovieid());
			hasGenre.insertIntoHasGenre(DBConnection.getConnection());
		}
	}

	private void insertMovieCharcter(MovieDTO movieDTO, Movie m1) throws SQLException {
		for (CharacterDTO characterDTO : movieDTO.getCharacters()) {
			MovieCharacter movieCharacter = new MovieCharacter(
					characterDTO.getCharacter(),
					characterDTO.getAlias(),
					m1.getMovieid(),
					PersonFactory.findOnename(characterDTO.getPlayer()).getPersonID()
			);
			movieCharacter.insertIntoMovieCharacter(DBConnection.getConnection());
		}
	}

	/**
	 * Loescht einen Film aus der Datenbank. Es werden auch alle abhaengigen Objekte geloescht,
	 * d.h. alle Charaktere und alle Genre-Zuordnungen.
	 * @param movie
	 * @throws Exception
	 */
	public void deleteMovie(long movieId) throws Exception {
		// TODO
		Movie m1 = MovieFactory.findById(movieId);
		m1.deletemovie(DBConnection.getConnection());
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


		for (Long id : HasGenreFactory.findById(movieId)) {
			Genre genre = GenreFactory.findbyid(id);
			md.addGenre(genre.getGenre());
		}

		for (MovieCharacter movieCharacter : MovieCharacterFactory.findByMovieId(movieId)) {
			CharacterDTO characterDTO = new CharacterDTO();
			characterDTO.setAlias(movieCharacter.getAlias());
			characterDTO.setCharacter(movieCharacter.getCharacter());
			characterDTO.setPlayer(PersonFactory.findbyid(movieCharacter.getPersonID()).getName());
			md.addCharacter(characterDTO);
		}



		return md;
	}

}
