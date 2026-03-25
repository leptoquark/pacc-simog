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
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.db.repository.TedDeltaRepository;

public class TedDeltaRepositoryImpl implements TedDeltaRepository {
	private static final Logger logger = Logger.getLogger(TedDeltaRepository.class);

	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();

	@Override
	public TedDelta getDeltaGaraValido(Long idGara) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createQuery("SELECT tedDelta FROM TedDelta tedDelta WHERE idGara = ? AND idLotto = NULL AND dataFineValidita = NULL");
			query.setParameter(1, idGara);

			@SuppressWarnings("unchecked")
			List<TedDelta> listTedDelta = query.getResultList();

			if(listTedDelta.size() == 1) {
				return listTedDelta.get(0);
			} else {
				logger.info("idGara : " + idGara + " - RETURN " + listTedDelta.size() + " ROWS");
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
	public TedDelta getDeltaLottoValidoByIdLotto(Long idGara, Long idLotto) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createQuery("SELECT tedDelta FROM TedDelta tedDelta WHERE idGara = ? AND idLotto = ? AND dataFineValidita = NULL");
			query.setParameter(1, idGara);
			query.setParameter(2, idLotto);

			@SuppressWarnings("unchecked")
			List<TedDelta> listTedDelta = query.getResultList();

			if(listTedDelta.size() == 1) {
				return listTedDelta.get(0);
			} else {
				logger.info("idGara : " + idGara + " - idLotto : " + idLotto + " - RETURN " + listTedDelta.size() + " ROWS");
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
	public TedDelta getDeltaLottoValidoByCIG(String cig) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createQuery("SELECT tedDelta FROM TedDelta tedDelta WHERE cig = ? AND dataFineValidita = NULL");
			query.setParameter(1, cig);

			@SuppressWarnings("unchecked")
			List<TedDelta> listTedDelta = query.getResultList();

			if(listTedDelta.size() == 1) {
				return listTedDelta.get(0);
			} else {
				logger.info("cig : " + cig + " - RETURN " + listTedDelta.size() + " ROWS");
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
	public TedDelta getDeltaGaraNonCancellato(Long idGara) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createQuery("SELECT tedDelta FROM TedDelta tedDelta WHERE idGara = ? AND idLotto = NULL AND dataCancellazione = NULL AND dataFineValidita = NULL");
			query.setParameter(1, idGara);

			@SuppressWarnings("unchecked")
			List<TedDelta> listTedDelta = query.getResultList();

			if(listTedDelta.size() == 1) {
				return listTedDelta.get(0);
			} else {
				logger.info("idGara : " + idGara + " - RETURN " + listTedDelta.size() + " ROWS");
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
	public TedDelta getDeltaLottoNonCancellato(Long idGara, Long idLotto) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createQuery("SELECT tedDelta FROM TedDelta tedDelta WHERE idGara = ? AND idLotto = ? AND dataCancellazione = NULL AND dataFineValidita = NULL");
			query.setParameter(1, idGara);
			query.setParameter(2, idLotto);

			@SuppressWarnings("unchecked")
			List<TedDelta> listTedDelta = query.getResultList();

			if(listTedDelta.size() == 1) {
				return listTedDelta.get(0);
			} else {
				logger.info("idGara : " + idGara + " - idLotto : " + idLotto + " - RETURN " + listTedDelta.size() + " ROWS");
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
	public List<TedDelta> getListaDetaLottoByIdGara(Long idGara) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createQuery("SELECT tedDelta FROM TedDelta tedDelta WHERE idGara = ? AND idLotto IS NOT NULL AND dataFineValidita = NULL");
			query.setParameter(1, idGara);

			@SuppressWarnings("unchecked")
			List<TedDelta> listTedDelta = query.getResultList();

			return listTedDelta;
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
	public Boolean merge(TedDelta tedDelta) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(tedDelta);
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

	@Override
	public Boolean persist(TedDelta tedDelta) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(tedDelta);
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