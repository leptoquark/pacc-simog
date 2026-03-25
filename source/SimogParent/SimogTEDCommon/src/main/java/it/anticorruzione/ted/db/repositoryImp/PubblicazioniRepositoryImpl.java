package it.anticorruzione.ted.db.repositoryImp;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.cp.TedCP;
import it.anticorruzione.ted.db.entity.Pubblicazioni;
import it.anticorruzione.ted.db.repository.PubblicazioniRepository;

public class PubblicazioniRepositoryImpl implements PubblicazioniRepository {
	private static final Logger logger = Logger.getLogger(PubblicazioniRepository.class);

	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();
	
	@Override
	public Pubblicazioni find(Long idPub, Date dataInizioPub) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
		Query query = entityManager.createQuery("SELECT p FROM Pubblicazioni p WHERE idPubblicazione = ? AND dataInizioPubb = ?");
		query.setParameter(1, idPub);
		query.setParameter(2, dataInizioPub);
		
		@SuppressWarnings("unchecked")
		List<Pubblicazioni> lista = query.getResultList();
		
		if(!lista.isEmpty())
			return lista.get(0);
		
		} catch (Exception exception) {
			logger.error(exception);
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}	
			
		return null;
	}

	@Override
	public Boolean merge(Pubblicazioni pub) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(pub);
			entityManager.getTransaction().commit();

			return true;
		} catch (Exception exception) {
			entityManager.getTransaction().rollback();

			logger.error(exception);
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}

		return false;
	}

}
