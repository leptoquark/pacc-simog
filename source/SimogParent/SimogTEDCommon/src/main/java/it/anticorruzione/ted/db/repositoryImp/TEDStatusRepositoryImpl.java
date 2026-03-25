package it.anticorruzione.ted.db.repositoryImp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.cp.TedCP;
import it.anticorruzione.ted.db.entity.TEDStatus;
import it.anticorruzione.ted.db.repository.TEDStatusRepository;

public class TEDStatusRepositoryImpl implements TEDStatusRepository {

	private static final Logger logger = Logger.getLogger(TEDStatusRepository.class);	
	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();
	
	/**
	 * Recupera l'ultimo stato registrato
	 */
	@Override
	public TEDStatus getLastNoticeStatus(Long idTedNotice) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Query q = em.createQuery("Select ts FROM TEDStatus ts WHERE ts.idTedNotice = ? order by ts.updateData");
			q.setParameter(1, idTedNotice);
			
			@SuppressWarnings("unchecked")
			List<TEDStatus> lista = q.getResultList(); 
			if(lista.size()>0)
				return lista.get(lista.size()-1);
			
		} catch (Exception exception) {
			logger.error(exception);
		} finally {
			if(em.isOpen()) {
				em.close();
			}
		}
		
		return null;
	}

	@Override
	public boolean saveTEDStatus(TEDStatus status) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(status);
			em.getTransaction().commit();
			
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error(exception);
			em.getTransaction().rollback();
		} finally {
			if(em.isOpen()) {
				em.close();
			}
		}
		
		return false;
	}

}
