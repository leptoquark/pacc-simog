/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.repositoryImp;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.cp.TedCP;
import it.anticorruzione.ted.db.entity.WsSession;
import it.anticorruzione.ted.db.repository.WsSessionRepository;

public class WsSessionRepositoryImpl implements WsSessionRepository {
	private static final Logger logger = Logger.getLogger(WsSessionRepository.class);

	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();

	@Override
	public WsSession findByTicket(String ticket) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createQuery("SELECT wsSession FROM WsSession wsSession WHERE ticket = ?");
			query.setParameter(1, ticket);

			@SuppressWarnings("unchecked")
			List<WsSession> listWsSession = query.getResultList();

			if(listWsSession.size() == 1) {
				return listWsSession.get(0);
			} else {
				logger.info("ticket : " + ticket + " - RETURN " + listWsSession.size() + " ROWS");
			}
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
	public Boolean merge(WsSession wsSession) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(wsSession);
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