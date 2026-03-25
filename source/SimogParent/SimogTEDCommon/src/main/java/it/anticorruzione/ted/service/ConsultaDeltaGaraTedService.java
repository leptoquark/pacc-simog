/**
 * Web Service TED
 */
package it.anticorruzione.ted.service;

import it.anticorruzione.ted.xml.response.Response;

public interface ConsultaDeltaGaraTedService {
	public Response execute(Long idGara, String idStazioneAppaltante);
}