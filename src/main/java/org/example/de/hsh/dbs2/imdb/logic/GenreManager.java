package org.example.de.hsh.dbs2.imdb.logic;

import jakarta.persistence.EntityManager;
import org.example.de.hsh.dbs2.imdb.util.EntityFactory;

import java.util.ArrayList;
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
		List<String> genres = new ArrayList<>();
		EntityManager em = null;
		try {
			em = EntityFactory.getEMF().createEntityManager();

			em.getTransaction().begin();
			List<Genre> results = em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
			for (Genre genre : results) {
				genres.add(genre.getGenre());
			}
			em.getTransaction().commit();
			return genres;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

}
