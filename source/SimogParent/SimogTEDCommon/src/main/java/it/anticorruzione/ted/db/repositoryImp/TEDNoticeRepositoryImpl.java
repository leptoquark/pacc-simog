package it.anticorruzione.ted.db.repositoryImp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.cp.TedCP;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.repository.TEDNoticeRepository;
import it.anticorruzione.ted.enums.TypeNoticeEnum;

public class TEDNoticeRepositoryImpl implements TEDNoticeRepository {

	private static final Logger logger = Logger.getLogger(TEDNoticeRepository.class);	
	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();
	
	@Override
	public TEDNotice findByNoDocExt(String noDocExt) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Query q = em.createQuery("Select tn FROM TEDNotice tn WHERE tn.noDocExt = ?");
			q.setParameter(1, noDocExt);
			
			@SuppressWarnings("unchecked")
			List<TEDNotice> lista = q.getResultList(); 
			if(lista.size()==1)
				return lista.get(0);
			
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
	public TEDNotice findByTypeAndIdGara(TypeNoticeEnum type, long idgara) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Query q = em.createQuery("Select tn FROM TEDNotice tn, TEDTypeNotice ttn "
					+ "WHERE tn.idGara = ? AND tn.idTedTypeNotice = ttn.idTedTypeNotice AND ttn.codTypeNotice = ?");
			q.setParameter(1, idgara);
			q.setParameter(2, type.getTipo());
			
			@SuppressWarnings("unchecked")
			List<TEDNotice> lista = q.getResultList();
			if(lista.size()>=1)
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
	public TEDNotice findByTypeAndIdLotto(TypeNoticeEnum type, long idlotto) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Query q = em.createQuery("Select tn FROM TEDNotice tn, TEDTypeNotice ttn "
					+ "WHERE tn.idLotto = ? AND tn.idTedTypeNotice = ttn.idTedTypeNotice AND ttn.codTypeNotice = ?");
			q.setParameter(1, idlotto);
			q.setParameter(2, type.getTipo());
			
			@SuppressWarnings("unchecked")
			List<TEDNotice> lista = q.getResultList();
			if(lista.size()>=1)
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
	public boolean insertTEDNotice(TEDNotice notice) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(notice);
			em.flush();
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
	public boolean updateTEDNotice(TEDNotice notice) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(notice);
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
	public TEDNotice find(Long idTEDNotice) {

		
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			return em.find(TEDNotice.class, idTEDNotice);
		}catch (Exception exception) {
			exception.printStackTrace();
			logger.error(exception);
			em.getTransaction().rollback();
		} finally {
			if(em.isOpen()) {
				em.close();
			}
		}
		return null;
	}

	@Override
	public TEDNotice findByNoDocOjs(String noDocOjs,TypeNoticeEnum typeNotice, long idLotto, long idGara) {
		EntityManager em = entityManagerFactory.createEntityManager();
		String query = "Select tn FROM TEDNotice tn WHERE tn.noDocOjs = ?";
		
		if(typeNotice!=null)
			query+=" AND tn.idTedTypeNotice = ?";
		
		if(idLotto!=0)
			query+=" AND tn.idLotto = ?";
		
		if(idGara!=0)
			query+=" AND tn.idGara = ?";
		
		try {
			int pIndex=1;
			Query q = em.createQuery(query);
			q.setParameter(pIndex++, noDocOjs);
			if(typeNotice!=null)
				q.setParameter(pIndex++, typeNotice.getIdTipo());
			
			if(idLotto!=0)
				q.setParameter(pIndex++, idLotto);
			
			if(idGara!=0)
				q.setParameter(pIndex++, idGara);
				
			@SuppressWarnings("unchecked")
			List<TEDNotice> lista = q.getResultList(); 
			if(lista.size()>=1)
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
