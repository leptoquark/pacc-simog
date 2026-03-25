/**
 * Web Service TED
 */
package it.anticorruzione.ted.service;

import it.anticorruzione.ted.xml.response.Response;

public interface ConsultaDeltaLottoTedService {
	public Response execute(String cig, String idStazioneAppaltante);
}