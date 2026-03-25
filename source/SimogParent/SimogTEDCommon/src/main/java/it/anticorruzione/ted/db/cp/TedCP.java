package it.anticorruzione.ted.db.cp;
/**
 * Web Service TED
 */


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TedCP {
	private static EntityManagerFactory entityManagerFactory;

	public static EntityManagerFactory getInstance() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("ted");
		}

		return entityManagerFactory;
	}
}