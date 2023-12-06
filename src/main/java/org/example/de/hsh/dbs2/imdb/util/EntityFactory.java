package org.example.de.hsh.dbs2.imdb.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityFactory {
	private static EntityManagerFactory emf;

	static {
		emf = Persistence.createEntityManagerFactory("movie");
		System.out.println("emf created");
	}

	public static EntityManagerFactory getEMF() {
		return emf;
	}
}
