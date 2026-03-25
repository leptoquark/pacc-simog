package it.anticorruzione.ted.db.repositoryImp;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.cp.TedCP;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.entity.TEDStatus;
import it.anticorruzione.ted.db.entity.TEDSubmit;
import it.anticorruzione.ted.db.repository.TEDSubmitRepository;

public class TEDSubmitRepositoryImpl implements TEDSubmitRepository {

	private static final Logger logger = Logger.getLogger(TEDSubmitRepository.class);	
	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();
	
	@Override
	public boolean insertSubmit(TEDSubmit submit) {

		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(submit);
			em.getTransaction().commit();
			return true;
		}catch (Exception exception) {
			logger.error(exception);
			em.getTransaction().rollback();
		} finally {
			if(em.isOpen()) {
				em.close();
			}
		}
		
		return false;
	}

	@Override
	public TEDSubmit getLastSubmit(Long idTedNotice) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Query q = em.createQuery("Select tn FROM TEDSubmit tn WHERE tn.idTedNotice = ? order by tn.idTedSubmit");
			q.setParameter(1, idTedNotice);
			
			@SuppressWarnings("unchecked")
			List<TEDSubmit> lista = q.getResultList(); 
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

}
