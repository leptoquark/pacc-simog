/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.repositoryImp;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.cp.TedCP;
import it.anticorruzione.ted.db.entity.Lotto;
import it.anticorruzione.ted.db.repository.LottoRepository;


public class LottoRepositoryImpl implements LottoRepository {
	private static final Logger logger = Logger.getLogger(LottoRepository.class);

	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();

	@Override
	public Lotto findByCigAndIdStazioneAppaltante(String cig, String idStazioneAppaltante) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String queryStr = "SELECT lotto FROM Lotto lotto, Gara gara WHERE lotto.idGara = gara.idGara AND lotto.cig = ? AND lotto.cigKkk = ? ";
		if(idStazioneAppaltante!=null)
			queryStr+="AND gara.idStazioneAppaltante = ?";
		try {
			Query query = entityManager.createQuery(queryStr);
			query.setParameter(1, cig.substring(0,7));
			query.setParameter(2, cig.substring(7,10));
			
			if(idStazioneAppaltante!=null)
			query.setParameter(3, idStazioneAppaltante);

			@SuppressWarnings("unchecked")
			List<Lotto> listLotto = query.getResultList();

			if(listLotto.size() == 1) {
				return listLotto.get(0);
			} else {
				logger.info("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - RETURN " + listLotto.size() + " ROWS");
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
	public List<Lotto> getByIdGara(Long idGara) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Lotto> res = new ArrayList<Lotto>();
		try {
			Query query = entityManager.createQuery("SELECT lotto FROM Lotto lotto WHERE lotto.idGara = ?");
			query.setParameter(1, idGara);


			@SuppressWarnings("unchecked")
			List<Lotto> listLotto = query.getResultList();
			for(Lotto l : listLotto) {
				if(l.getDataCancellazioneLotto()==null) {
					res.add(l);
				}
			}
			
				return res;
		
		} catch (Exception exception) {
			logger.error(exception);
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}

		return null;
	}
}