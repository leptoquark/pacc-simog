/**
 * Web Service TED
 */
package it.anticorruzione.ted.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.entity.Gara;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.db.repository.GaraRepository;
import it.anticorruzione.ted.db.repository.TedDeltaRepository;
import it.anticorruzione.ted.db.repositoryImp.GaraRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TedDeltaRepositoryImpl;
import it.anticorruzione.ted.service.ConsultaDeltaGaraTedService;
import it.anticorruzione.ted.service.DeltaGaraTedService;
import it.anticorruzione.ted.xml.DeltaGara;
import it.anticorruzione.ted.xml.DeltaLotto;
import it.anticorruzione.ted.xml.response.Response;

public class ConsultaDeltaGaraTedServiceImpl implements ConsultaDeltaGaraTedService {
	private static final Logger logger = Logger.getLogger(DeltaGaraTedService.class);

	private GaraRepository garaRepository = new GaraRepositoryImpl();

	private TedDeltaRepository tedDeltaRepository = new TedDeltaRepositoryImpl();

	@Override
	public Response execute(Long idGara, String idStazioneAppaltante) {
		Response response = new Response();

		Gara gara = garaRepository.findByIdGaraAndIdStazioneAppaltante(idGara, idStazioneAppaltante);

		if(gara != null) {
			if(gara.getDataCancellazioneGara() == null) {
				List<TedDelta> listTedDelta = tedDeltaRepository.getListaDetaLottoByIdGara(idGara);

				if(listTedDelta != null) {
					List<DeltaLotto> listDeltaLotto = new ArrayList<DeltaLotto>();

					for(int cont = 0 ; cont < listTedDelta.size(); cont++) {
						TedDelta tedDelta = listTedDelta.get(cont);

						if(tedDelta.getIdLotto() == null) {
							DeltaGara deltaGara = new DeltaGara();
							deltaGara.setIdGara(tedDelta.getIdGara());
							deltaGara.setDataInserimento(tedDelta.getDataInserimento());
							deltaGara.setDataInizioValidita(tedDelta.getDataInizioValidita());
							deltaGara.setDeltaGara(tedDelta.getXmlDelta());

							response.setDeltaGara(deltaGara);
						} else {
							DeltaLotto deltaLotto = new DeltaLotto();
							deltaLotto.setCig(tedDelta.getCig());
							deltaLotto.setDataInserimento(tedDelta.getDataInserimento());
							deltaLotto.setDataInizioValidita(tedDelta.getDataInizioValidita());
							deltaLotto.setDeltaLotto(tedDelta.getXmlDelta());

							listDeltaLotto.add(deltaLotto);
						}
					}

					response.setDeltaLotto(listDeltaLotto);
					response.setSuccess(true);
				} else {
					logger.info("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - DELTA NOT FOUND");

					response.setSuccess(false);
					response.setError("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - DELTA NOT FOUND");
				}

				return response;
			} else {
				logger.info("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - dataCancellazioneGara : " + gara.getDataCancellazioneGara() + " - DATA CANCELLAZIONE GARA IS NOT NULL");

				response.setSuccess(false);
				response.setError("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");
			}
		} else {
			logger.info("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");

			response.setSuccess(false);
			response.setError("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");
		}

		return response;
	}
}