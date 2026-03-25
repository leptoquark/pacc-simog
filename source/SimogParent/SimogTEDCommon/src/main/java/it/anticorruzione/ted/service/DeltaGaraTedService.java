/**
 * Web Service TED
 */
package it.anticorruzione.ted.service;

import it.anticorruzione.ted.beans.ResponseMessageTED;

public interface DeltaGaraTedService {
	public ResponseMessageTED execute(Long idGara, String idStazioneAppaltante, String deltaGaraTed);
}