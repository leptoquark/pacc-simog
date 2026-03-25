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
import it.anticorruzione.ted.db.entity.Gara;
import it.anticorruzione.ted.db.repository.GaraRepository;

public class GaraRepositoryImpl implements GaraRepository {
	private static final Logger logger = Logger.getLogger(GaraRepository.class);

	private EntityManagerFactory entityManagerFactory = TedCP.getInstance();
	
	@Override
	public Gara findByIdGaraAndIdStazioneAppaltante(Long idGara, String idStazioneAppaltante) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			String stringQry = "SELECT gara FROM Gara gara WHERE idGara = ? ";
			
			if(idStazioneAppaltante!=null)
				stringQry += "AND idStazioneAppaltante = ?";
			
			Query query = entityManager.createQuery(stringQry);
			query.setParameter(1, idGara);
			
			if(idStazioneAppaltante!=null)
			   query.setParameter(2, idStazioneAppaltante);

			@SuppressWarnings("unchecked")
			List<Gara> listGara = query.getResultList();

			if(listGara.size() == 1) {
				return listGara.get(0);
			} else {
				logger.info("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - RETURN " + listGara.size() + " ROWS");
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
}