package org.example.de.hsh.dbs2.imdb.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import org.example.de.hsh.dbs2.imdb.logic.dto.*;
import org.example.de.hsh.dbs2.imdb.util.EntityFactory;

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
		EntityManager em = EntityFactory.getEMF().createEntityManager();
		em.getTransaction().begin();
		List<Movie> movies = em.createQuery("SELECT m FROM Movie m WHERE m.title LIKE :title", Movie.class)
				.setParameter("title", "%" + search + "%")
				.getResultList();
		for (Movie movie : movies) {
			list.add(getMovie(movie.getId()));
		}
		em.getTransaction().commit();
		em.close();

		return list;
	}

	/**
	 * Speichert die uebergebene Version des Films neu in der Datenbank oder aktualisiert den
	 * existierenden Film.
	 * Dazu werden die Daten des Films selbst (Titel, Jahr, Typ) beruecksichtigt,
	 * aber auch alle Genres, die dem Film zugeordnet sind und die Liste der Charaktere
	 * auf den neuen Stand gebracht.
	 * @param movieDTO Film-Objekt mit Genres und Charakteren.
	 * @throws Exception
	 */
	public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {
		// TODO
		EntityManager em = EntityFactory.getEMF().createEntityManager();
		em.getTransaction().begin();
		Movie m1 = null;

		if (movieDTO.getId() == null) {
			System.out.println("movieDTO id = null");
			m1 = new Movie(movieDTO.getTitle(), movieDTO.getType(), movieDTO.getYear());


			System.out.println("1 : " + m1.getId());


			for (String genre : movieDTO.getGenres()) {
				m1.addGenre(genre, em);
			}
			for (CharacterDTO characterDTO : movieDTO.getCharacters()) {
				MovieCharacter movieCharacter = new MovieCharacter(
						characterDTO.getCharacter(),
						characterDTO.getAlias()
				);
//				movieCharacter.addMovie(m1.getId(), em);
				movieCharacter.setMovie(m1);
				movieCharacter.addPerson(characterDTO.getPlayer(), em);
				em.persist(movieCharacter);
				m1.addMovieCharacter(movieCharacter.getId(), em);
			}
		}

		System.out.println(m1.getMovieCharacters());

		em.persist(m1);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Loescht einen Film aus der Datenbank. Es werden auch alle abhaengigen Objekte geloescht,
	 * d.h. alle Charaktere und alle Genre-Zuordnungen.
	 * @param movieId
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
		EntityManager em = EntityFactory.getEMF().createEntityManager();
		em.getTransaction().begin();
		MovieDTO md = new MovieDTO();
		Movie m1 = em.find(Movie.class, movieId);
		md.setId(m1.getId());
		md.setTitle(m1.getTitle());
		md.setYear(m1.getYear());
		md.setType(m1.getType());

		for (Genre genre : m1.getGenres()) {
			md.addGenre(genre.getGenre());
		}

		for (MovieCharacter movieCharacter : m1.getMovieCharacters()) {
			CharacterDTO characterDTO = new CharacterDTO();
			characterDTO.setAlias(movieCharacter.getAlias());
			characterDTO.setCharacter(movieCharacter.getCharacter());
			characterDTO.setPlayer(movieCharacter.getPerson().getName());
			md.addCharacter(characterDTO);
		}

		em.getTransaction().commit();
		em.close();
		return md;
	}

}