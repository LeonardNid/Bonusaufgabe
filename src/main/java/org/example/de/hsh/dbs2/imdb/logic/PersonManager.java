package org.example.de.hsh.dbs2.imdb.logic;

import jakarta.persistence.EntityManager;
import org.example.de.hsh.dbs2.imdb.util.EntityFactory;

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
		// TODO
		List<String> personen = new ArrayList<>();
		EntityManager em = null;
		try {
			em = EntityFactory.getEMF().createEntityManager();
			em.getTransaction().begin();
			List<Person> results = em.createQuery("SELECT p FROM Person p WHERE p.name LIKE :name", Person.class)
					.setParameter("name", "%" + text + "%")
					.getResultList();
			for (Person person : results) {
				personen.add(person.getName());
			}
			em.getTransaction().commit();
			return personen;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

}
