package it.anticorruzione.ted.db.repositoryImp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.cp.TedCP;
import it.anticorruzione.ted.db.entity.TEDNoDocExt;
import it.anticorruzione.ted.db.repository.TEDNoDocExtRepository;

public class TEDNoDocExtRepositoryImpl implements TEDNoDocExtRepository {
	private static final Logger logger = Logger.getLogger(TEDNoDocExtRepository.class);	
	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();
	
	@Override
	public String createNoDocExt(String year) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		String res = "";
		try {
			Query q = em.createQuery("Select n FROM TEDNoDocExt n WHERE n.year = ? ");
			q.setParameter(1, year);
			
			@SuppressWarnings("unchecked")
			List<TEDNoDocExt> listNoDocExt = q.getResultList();
			TEDNoDocExt tedNoDocExt=null;
			
			if(listNoDocExt.size() == 1) {
				tedNoDocExt = listNoDocExt.get(0);
			} else {
				tedNoDocExt = new TEDNoDocExt();
				tedNoDocExt.setYear(year);
				tedNoDocExt.setCountNo(new Long(0));
			}
			
			tedNoDocExt.incrementCount();
			
			em.merge(tedNoDocExt);
			em.getTransaction().commit();
			res = tedNoDocExt.getYear()+"-"+tedNoDocExt.getStringCount();
			
		}catch (Exception exception) {
			logger.error(exception);
			em.getTransaction().rollback();
		} finally {
			if(em.isOpen()) {
				em.close();
			}
		}
		
		return res;
	}

}
